package com.example.commands;
import com.example.Wcc;
import com.example.datas.WccData;
import com.example.datas.WccDataList;
import com.example.datas.project_data;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.argument.*;
import static net.minecraft.server.command.CommandManager.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WccCommand {
    public static void project_list(){
        Path configDir = FabricLoader.getInstance().getConfigDir().resolve("wcc");
        Path dataFile = configDir.resolve("project_data.json");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
                    dispatcher.register(literal("wcc")
                            .executes(context -> {
                                return 1;
                            })
                            .then(literal("list")
                                    .executes(context -> {
                                        WccData loadedData = project_data.loadedData(dataFile, WccData.class);
                                        if (loadedData != null) {
                                            ArrayList<Map<String, Object>> list = loadedData.getList();

                                            String player = context.getSource().getName();

                                            context.getSource().sendMessage(Text.of("======以下是已储存的项目列表======"));
                                            for (int i = 0; i < loadedData.getList().size(); i++) {
                                                Map<String, Object> data = list.get(i);
                                                String project_name = (String) data.get("project_name");
                                                String player_name = (String) data.get("player_name");

                                                double x = (double) data.get("x");
                                                int fx = (int) x;
                                                double y = (double) data.get("y");
                                                int fy = (int) y;
                                                double z = (double) data.get("z");
                                                int fz = (int) z;
                                                double yaw = (double) data.get("yaw");
                                                double pitch = (double) data.get("pitch");

                                                String dimension = (String) data.get("dimension");
                                                int cx = i+1;
                                                String color = "";
                                                String dimension_name = switch (dimension) {
                                                    case "minecraft:overworld" -> {
                                                        color = "#00AA00";
                                                        yield "主世界";
                                                    }
                                                    case "minecraft:the_nether" -> {
                                                        color = "#AA0000";
                                                        yield "地狱";
                                                    }
                                                    case "minecraft:the_end" -> {
                                                        color = "#FFFF55";
                                                        yield "末地";
                                                    }
                                                    default -> {
                                                        color = "";
                                                        yield dimension;
                                                    }
                                                };
                                                String commend = "tellraw "+player+" [{\"text\":\"[\"},{\"text\":\"#"+cx+"\",\"color\":\""+color+"\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\""+dimension_name+"\"}},{\"text\":\"]\"},{\"text\":\"[△]\",\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"假人上线\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/player "+player_name+" spawn at "+x+" "+y+" "+z+" facing "+yaw+" "+pitch+" in "+dimension+"\"}},{\"text\":\"[▽]\",\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"假人下线\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/player "+player_name+" kill\"}},{\"text\":\""+project_name+"\",\"color\":\"#ff55ff\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\""+fx+"."+fy+"."+fz+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"点击获取坐标!\"}}]";
                                                context.getSource().getServer().getCommandManager().executeWithPrefix(context.getSource().getServer().getCommandSource(), commend);
                                            }
                                        }
                                        return 1;
                                    }))
                            .then(literal("add")
                                    .then(argument("project_name",TextArgumentType.text(registryAccess))
                                            .then(argument("player", EntityArgumentType.player())
                                                            .executes(context -> {
                                                                Text Rproject_name = TextArgumentType.getTextArgument(context,"project_name");
                                                                StringBuilder project_name =WccDataDou.project_name_dou(Rproject_name);

                                                                ServerPlayerEntity Rplayer = EntityArgumentType.getPlayer(context,"player");
                                                                StringBuilder player_name =WccDataDou.player_name_dou(Rplayer);

                                                                ServerPlayerEntity loData = context.getSource().getServer().getPlayerManager().getPlayer(String.valueOf(player_name));

                                                                double x = 0;
                                                                double y = 0;
                                                                double z = 0;
                                                                double yaw = 0;
                                                                double pitch =0;
                                                                String dimension = null;

                                                                if (loData != null) {
                                                                    x =loData.getX();
                                                                    y =loData.getY();
                                                                    z =loData.getZ();
                                                                    yaw = loData.getYaw();
                                                                    pitch = loData.getPitch();
                                                                    RegistryKey<World> Rdimension = loData.getWorld().getRegistryKey();
                                                                    dimension = String.valueOf(Rdimension.getValue());
                                                                }

                                                                Map<String, Object> ProjectData = new HashMap<>();
                                                                ProjectData.put("project_name",project_name);
                                                                ProjectData.put("player_name",player_name);
                                                                ProjectData.put("x",x);
                                                                ProjectData.put("y",y);
                                                                ProjectData.put("z",z);
                                                                ProjectData.put("yaw",yaw);
                                                                ProjectData.put("pitch",pitch);
                                                                ProjectData.put("dimension",dimension);

                                                                WccData loadedData = project_data.loadedData(dataFile, WccData.class);
                                                                ArrayList<Map<String, Object>> list = null;
                                                                if (loadedData != null) {
                                                                    list = loadedData.getList();
                                                                }else {
                                                                    WccDataList.add(ProjectData,dataFile);
                                                                }
                                                                if (list != null) {
                                                                    for (int i = 0;i<list.size();i++) {
                                                                        Map<String, Object> com_data = list.get(i);
                                                                        String com = (String) com_data.get("project_name");
                                                                        if (Objects.equals(com, String.valueOf(project_name))) {
                                                                            context.getSource().sendMessage(Text.of("已存在项目<" + project_name + ">"));
                                                                            break;
                                                                        }
                                                                        if(i==list.size()-1) {
                                                                            WccDataList.add(ProjectData,dataFile);
                                                                            context.getSource().sendMessage(Text.of("项目<" + project_name + ">成功添加!"));
                                                                        }
                                                                    }
                                                                }
                                                                return 1;
                                                            })
                                            )
                                    )
                            )
                            .then(literal("remove")
                                    .executes(context -> {
                                        WccData loadedData = project_data.loadedData(dataFile, WccData.class);
                                        if (loadedData != null) {
                                            ArrayList<Map<String, Object>> pach_data = loadedData.getList();
                                            String player = context.getSource().getName();
                                            context.getSource().sendMessage(Text.of("=======选择项目删除======="));
                                            for (int i = 0; i < pach_data.size(); i++) {

                                                Map<String, Object> data = pach_data.get(i);
                                                String project_name = (String) data.get("project_name");
                                                String dimension = (String) data.get("dimension");
                                                int cx = i+1;
                                                String color = "";
                                                String dimension_name = switch (dimension) {
                                                    case "minecraft:overworld" -> {
                                                        color = "#00AA00";
                                                        yield "主世界";
                                                    }
                                                    case "minecraft:the_nether" -> {
                                                        color = "#AA0000";
                                                        yield "地狱";
                                                    }
                                                    case "minecraft:the_end" -> {
                                                        color = "#FFFF55";
                                                        yield "末地";
                                                    }
                                                    default -> {
                                                        color = "#FFFFFF";
                                                        yield dimension;
                                                    }
                                                };
                                                String commend = "tellraw "+player+" [{\"text\":\"[\"},{\"text\":\"#"+cx+"\",\"color\":\""+color+"\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\""+dimension_name+"\"}},{\"text\":\"]\"},{\"text\":\"[X]\",\"color\":\"red\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"点击来输入删除命令\"},\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/wcc remove "+i+" "+project_name+"\"}},{\"text\":\""+project_name+"\",\"color\":\"#ff55ff\"}]";
                                                context.getSource().getServer().getCommandManager().executeWithPrefix(context.getSource().getServer().getCommandSource(), commend);
                                            }
                                        }
                                        return 1;
                                    })
                                    .then(argument("i", IntegerArgumentType.integer())
                                            .then(argument("project_name", MessageArgumentType.message())
                                                    .executes(context -> {
                                                        WccData loadedData = project_data.loadedData(dataFile, WccData.class);
                                                        int i = IntegerArgumentType.getInteger(context,"i");
                                                        Text Rproject_name = MessageArgumentType.getMessage(context,"project_name");
                                                        StringBuilder project_name =WccDataDou.project_name_dou(Rproject_name);

                                                        String player = context.getSource().getName();

                                                        ArrayList<Map<String, Object>> list = null;
                                                        if (loadedData != null) {
                                                            list = loadedData.getList();
                                                        }
                                                        if (list != null && i < list.size()) {
                                                            Map<String, Object> com_data = list.get(i);
                                                            String com = (String) com_data.get("project_name");
                                                            if (Objects.equals(com, String.valueOf(project_name))) {
                                                                WccDataList.remove(i, dataFile);
                                                                context.getSource().sendMessage(Text.of("已经删除项目<" + project_name + ">"));
                                                            } else {
                                                                String commend = "tellraw " + player + " {\"text\":\"项目<" + project_name + ">不纯在!\",\"color\":\"red\"}";
                                                                context.getSource().getServer().getCommandManager().executeWithPrefix(context.getSource().getServer().getCommandSource(), commend);
                                                            }
                                                        }
                                                        return 1;
                                                    })
                                            )
                                    )
                            )
                    );
                }
        );
    }
}
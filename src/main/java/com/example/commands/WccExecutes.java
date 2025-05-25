package com.example.commands;

import com.example.Wcc;
import com.example.datas.WccData;
import com.example.datas.WccDataList;
import com.example.datas.project_data;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class WccExecutes {
    public static void tellraw(){
        Path configDir = FabricLoader.getInstance().getConfigDir().resolve("wcc");
        Path dataFile = configDir.resolve("project_data.json");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("execute_wcc")
                    .then(literal("list")
                            .then(argument("integer",IntegerArgumentType.integer())
                                    .executes(context -> {
                                        WccData loadedData = project_data.loadedData(dataFile, WccData.class);

                                        int i = IntegerArgumentType.getInteger(context, "integer");
                                        ArrayList<Map<String, Object>> pach_data = loadedData.getList();
                                        Map<String, Object> data = pach_data.get(i);
                                        String player_name = (String) data.get("player_name");
                                        Object x = data.get("x");
                                        Object y = data.get("y");
                                        Object z = data.get("z");
                                        String dimension = (String) data.get("dimension");
                                        String commend = "execute in "+dimension+" run player "+player_name+" spawn at "+x+" "+y+" "+z;
                                        context.getSource().getServer().getCommandManager().executeWithPrefix(context.getSource().getServer().getCommandSource(), commend);
                                        return 1;
                                    })
                            )
                    )
                    .then(literal("remove")
                            .then(argument("i",IntegerArgumentType.integer())
                                    .then(argument("project_name", MessageArgumentType.message())
                                            .executes(context -> {
                                                WccData loadedData = project_data.loadedData(dataFile, WccData.class);
                                                int i = IntegerArgumentType.getInteger(context,"i");
                                                Text Rproject_name = MessageArgumentType.getMessage(context,"project_name");
                                                StringBuilder project_name =WccDataDou.project_name_dou(Rproject_name);

                                                ServerPlayerEntity Rplayer_name = context.getSource().getPlayer();
                                                StringBuilder player =WccDataDou.player_name_dou(Rplayer_name);

                                                ArrayList<Map<String, Object>> list = loadedData.getList();
                                                if (i<list.size()){
                                                    Map<String, Object> com_data = list.get(i);
                                                    String com = (String) com_data.get("project_name");
                                                    if (Objects.equals(com, String.valueOf(project_name))) {
                                                        WccDataList.remove(i,dataFile);
                                                        context.getSource().sendMessage(Text.of("已经删除项目<" + project_name + ">"));
                                                    } else  {
                                                        String commend ="tellraw "+player+" {\"text\":\"项目<"+project_name+">不纯在!\",\"color\":\"red\"}";
                                                        context.getSource().getServer().getCommandManager().executeWithPrefix(context.getSource().getServer().getCommandSource(), commend);
                                                    }
                                                }else {
                                                    int cxi=i+1;
                                                    String commend ="tellraw "+player+" {\"text\":\"列表中不纯在第<"+cxi+">个数据!\",\"color\":\"red\"}";
                                                    context.getSource().getServer().getCommandManager().executeWithPrefix(context.getSource().getServer().getCommandSource(), commend);
                                                }
                                            return 1;
                                            })
                                    )
                            )
                    )
            );
        });
    }
}

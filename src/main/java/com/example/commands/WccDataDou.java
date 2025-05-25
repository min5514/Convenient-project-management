package com.example.commands;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class WccDataDou {
    public static StringBuilder project_name_dou(Text Rproject_name){
        StringBuilder project_name =new StringBuilder();
        project_name.append(Rproject_name);
        project_name.delete(0,8);
        project_name.delete(project_name.length()-1,project_name.length());
        return project_name;
    }
    public static StringBuilder player_name_dou(ServerPlayerEntity Rplayer_name){
        StringBuilder player_name = new StringBuilder();
        player_name.append(Rplayer_name);
        player_name.delete(0,20);
        player_name.delete(player_name.indexOf("'/"),player_name.length());
        return player_name;
    }
    public static StringBuilder dimension_dou(RegistryKey<World> Rdimension){
        StringBuilder dimension =new StringBuilder();
        dimension.append(Rdimension);
        dimension.delete(0,34);
        dimension.delete(dimension.length()-1,dimension.length());
        return dimension;
    }
}

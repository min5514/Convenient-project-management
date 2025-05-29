package com.example.datas;

import com.example.Wcc;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

public class WccDataCopy {
    public static void Copy(){
        Path configDir = FabricLoader.getInstance().getConfigDir().resolve("wcc");
        Path dataFile = configDir.resolve("$$$project_data.json");
        Path FoodataFile = configDir.resolve("project_data.json");
        WccData loadedData = project_data.loadedData(FoodataFile, WccData.class);
        ArrayList<Map<String, Object>> list = null;
        if (loadedData != null) {
            list = loadedData.getList();
        }
        WccData data = new WccData();
        data.setList(list);
        project_data.saveData(dataFile, data);
        Wcc.LOGGER.info("已经备份目录到:config/wcc/$$$project_data.json");
    }
}


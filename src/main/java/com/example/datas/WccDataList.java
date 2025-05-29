package com.example.datas;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

public class WccDataList {
    public static void add(Map<String,Object> ProjectData,Path dataFile){
        WccData loadedData = project_data.loadedData(dataFile, WccData.class);
        if (loadedData != null) {
            ArrayList<Map<String, Object>> list = loadedData.getList();
            WccData data = new WccData();
            list.add(ProjectData);
            data.setList(list);
            project_data.saveData(dataFile, data);
        }else {
            ArrayList<Map<String, Object>> list = new ArrayList<>();
            WccData data = new WccData();
            list.add(ProjectData);
            data.setList(list);
            project_data.saveData(dataFile, data);
        }
    }
    public static void remove(int pach,Path dataFile){
        WccData loadedData = project_data.loadedData(dataFile, WccData.class);
        if (loadedData != null) {
            ArrayList<Map<String,Object>> list = loadedData.getList();
            WccData data = new WccData();
            list.remove(pach);
            data.setList(list);
            project_data.saveData(dataFile, data);
        }
    }
}

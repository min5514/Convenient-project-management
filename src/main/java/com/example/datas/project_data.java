package com.example.datas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class project_data {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // 写入数据到 JSON 文件
    public static void saveData(Path filePath, Object data) {
        try {
            Files.createDirectories(filePath.getParent()); // 创建父目录
            String json = GSON.toJson(data);
            Files.writeString(filePath, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从 JSON 文件读取数据
    public static <T> T loadedData(Path filePath, Class<T> type) {
        try {
            if (Files.exists(filePath)) {
                String json = Files.readString(filePath);
                return GSON.fromJson(json, type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // 或返回默认值
    }
}
package com.nhom2oop.dangkymonhoc.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class FileUtils {
    private static final Gson gson = new Gson();

    public static <T> List<T> readList(String filePath, Class<T> clazz) {
        try (FileReader reader = new FileReader(filePath)) {
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            List<T> result = gson.fromJson(reader, listType);
            return result != null ? result : new java.util.ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Không đọc được file: " + filePath, e);
        }
    }

    public static <T> void writeList(String filePath, List<T> data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException("Không ghi được file: " + filePath, e);
        }
    }
}
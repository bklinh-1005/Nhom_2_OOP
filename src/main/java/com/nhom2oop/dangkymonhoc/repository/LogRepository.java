package com.nhom2oop.dangkymonhoc.repository;

import com.nhom2oop.dangkymonhoc.model.LogEntry;
import com.nhom2oop.dangkymonhoc.utils.FileUtils;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Repository
public class LogRepository {

    private static final String JSON_FILE_PATH = "src/main/resources/data/logs.json";
    private static final String TEXT_LOG_DIR = "logs";
    private static final String TEXT_LOG_FILE = "logs/app.log";


    public synchronized void save(LogEntry logEntry) {
        saveToJson(logEntry);
        saveToTextFile(logEntry);
    }


    public List<LogEntry> findAll() {
        return FileUtils.readList(JSON_FILE_PATH, LogEntry.class);
    }


    private void saveToJson(LogEntry logEntry) {
        try {
            List<LogEntry> logs = findAll();
            logs.add(logEntry);
            FileUtils.writeList(JSON_FILE_PATH, logs);
        } catch (Exception e) {
            System.err.println("Lỗi khi ghi log JSON: " + e.getMessage());
        }
    }


    private void saveToTextFile(LogEntry logEntry) {
        try {
            File logDir = new File(TEXT_LOG_DIR);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            try (PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(TEXT_LOG_FILE, true),
                            StandardCharsets.UTF_8))) {
                writer.println(logEntry.toFormattedString());
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi log text: " + e.getMessage());
        }
    }
}

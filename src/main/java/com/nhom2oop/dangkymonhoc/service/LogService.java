package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.model.LogEntry;
import com.nhom2oop.dangkymonhoc.repository.LogRepository;
import com.nhom2oop.dangkymonhoc.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;




    public void logLogin(String username, String userId, String role, String status) {
        log(username, userId, role, "LOGIN",
                "Người dùng đăng nhập hệ thống.", status);
    }


    public void logLogout(String username, String userId, String role) {
        log(username, userId, role, "LOGOUT",
                "Người dùng đăng xuất hệ thống.", "SUCCESS");
    }


    public void logRegistration(String username, String userId,
                                String courseId, String status, String detail) {
        log(username, userId, "Student", "REGISTER_COURSE",
                "Đăng ký môn " + courseId + ". " + detail, status);
    }


    public void logCancellation(String username, String userId,
                                String courseId, String status, String detail) {
        log(username, userId, "Student", "CANCEL_REGISTRATION",
                "Hủy đăng ký môn " + courseId + ". " + detail, status);
    }


    public void logError(String username, String action, String detail) {
        log(username, null, "System", action,
                "LỖI: " + detail, "ERROR");
    }


    public void logLecturerAction(String username, String userId,
                                  String action, String detail) {
        log(username, userId, "Lecturer", action, detail, "SUCCESS");
    }


    public void log(String username, String userId, String role,
                    String action, String description, String status) {
        try {
            String logId = IdGenerator.generate("LOG");

            LogEntry entry = new LogEntry(
                    logId, username, userId, role,
                    action, description, null, status
            );

            logRepository.save(entry);
        } catch (Exception e) {

            System.err.println("Lỗi khi ghi log: " + e.getMessage());
        }
    }
}

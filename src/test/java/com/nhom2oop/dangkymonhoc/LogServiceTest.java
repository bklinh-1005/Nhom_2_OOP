package com.nhom2oop.dangkymonhoc;

import com.nhom2oop.dangkymonhoc.model.LogEntry;
import com.nhom2oop.dangkymonhoc.repository.LogRepository;
import com.nhom2oop.dangkymonhoc.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogServiceTest {

    private LogService logService;
    private List<LogEntry> savedLogs;

    @BeforeEach
    public void setUp() {
        savedLogs = new ArrayList<>();

        LogRepository fakeLogRepo = new LogRepository() {
            @Override
            public synchronized void save(LogEntry logEntry) {
                savedLogs.add(logEntry);
            }
        };

        logService = new LogService();
        injectField(logService, "logRepository", fakeLogRepo);
    }

    @Test
    public void testLogLogin_ThanhCong() {
        logService.logLogin("sv001", "u1", "Student", "SUCCESS");

        assertEquals(1, savedLogs.size());

        LogEntry entry = savedLogs.get(0);
        assertNotNull(entry.getId());
        assertEquals("sv001", entry.getUsername());
        assertEquals("u1", entry.getUserId());
        assertEquals("Student", entry.getRole());
        assertEquals("LOGIN", entry.getAction());
        assertEquals("SUCCESS", entry.getStatus());
        assertNotNull(entry.getTimestamp());
        assertTrue(entry.getDescription().contains("đăng nhập"));
    }

    @Test
    public void testLogCancellation_ThanhCong() {
        logService.logCancellation("sv001", "u1", "HP001",
                "SUCCESS", "Huy thanh cong");

        assertEquals(1, savedLogs.size());

        LogEntry entry = savedLogs.get(0);
        assertEquals("CANCEL_REGISTRATION", entry.getAction());
        assertEquals("SUCCESS", entry.getStatus());
        assertTrue(entry.getDescription().contains("HP001"));
    }

    @Test
    public void testLogError_ThanhCong() {
        logService.logError("admin", "SYSTEM_CHECK", "Database timeout");

        assertEquals(1, savedLogs.size());

        LogEntry entry = savedLogs.get(0);
        assertEquals("SYSTEM_CHECK", entry.getAction());
        assertEquals("ERROR", entry.getStatus());
        assertEquals("System", entry.getRole());
        assertTrue(entry.getDescription().contains("Database timeout"));
    }

    @Test
    public void testLogLecturerAction_ThanhCong() {
        logService.logLecturerAction("giangvien1", "l1",
                "VIEW_STUDENTS", "Xem danh sach sinh vien mon HP001");

        assertEquals(1, savedLogs.size());

        LogEntry entry = savedLogs.get(0);
        assertEquals("Lecturer", entry.getRole());
        assertEquals("VIEW_STUDENTS", entry.getAction());
        assertEquals("SUCCESS", entry.getStatus());
    }

    @Test
    public void testToFormattedString_DungFormat() {
        logService.logLogin("admin", "u1", "Lecturer", "SUCCESS");

        LogEntry entry = savedLogs.get(0);
        String formatted = entry.toFormattedString();

        assertTrue(formatted.contains("USER: admin"));
        assertTrue(formatted.contains("ROLE: Lecturer"));
        assertTrue(formatted.contains("ACTION: LOGIN"));
        assertTrue(formatted.contains("STATUS: SUCCESS"));
        assertTrue(formatted.contains("---"));
    }

    @Test
    public void testMultipleLogs_LuuDuocTatCa() {
        logService.logLogin("sv001", "u1", "Student", "SUCCESS");
        logService.logRegistration("sv001", "u1", "HP001", "SUCCESS", "OK");
        logService.logLogout("sv001", "u1", "Student");

        assertEquals(3, savedLogs.size());
        assertEquals("LOGIN", savedLogs.get(0).getAction());
        assertEquals("REGISTER_COURSE", savedLogs.get(1).getAction());
        assertEquals("LOGOUT", savedLogs.get(2).getAction());
    }


    private void injectField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Không thể inject field: " + fieldName, e);
        }
    }
}

package com.nhom2oop.dangkymonhoc;

import com.nhom2oop.dangkymonhoc.exception.RegistrationNotFoundException;
import com.nhom2oop.dangkymonhoc.model.*;
import com.nhom2oop.dangkymonhoc.repository.RegistrationRepository;
import com.nhom2oop.dangkymonhoc.service.CancellationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CancellationServiceTest {

    private CancellationService cancellationService;
    private RegistrationRepository fakeRepository;
    private Registration testRegistration;

    @BeforeEach
    public void setUp() {
        Student student = new Student("u1", "sv001", "hash", "Nguyen Van A",
                "SV001", "CNTT1", "Cong nghe thong tin", 20);

        Lecturer lecturer = new Lecturer("gv1", "giangvien1", "hash", "Tran Thi B", "GV001");
        Schedule schedule = new Schedule("Thu 2", "07:00", "09:00");
        Course course = new Course("HP001", "Lap trinh Java", 3, lecturer, 40, 1, schedule);

        testRegistration = new Registration("DK001", student);
        testRegistration.addDetail(new RegistrationDetail(course, "2026-07-28"));

        fakeRepository = new RegistrationRepository() {
            @Override
            public Registration findByStudentId(String studentId) {
                if (studentId.equals("SV001")) {
                    return testRegistration;
                }
                return null;
            }

            @Override
            public void save(Registration registration) {
                // Không cần ghi file thật khi test
            }
        };

        cancellationService = new CancellationService();
        setRepository(cancellationService, fakeRepository);
    }

    private void setRepository(CancellationService service, RegistrationRepository repo) {
        try {
            var field = CancellationService.class.getDeclaredField("registrationRepository");
            field.setAccessible(true);
            field.set(service, repo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCancelRegistration_ThanhCong() {
        assertEquals(1, testRegistration.getDetails().size());

        cancellationService.cancelRegistration("SV001", "HP001");

        assertEquals(0, testRegistration.getDetails().size());
    }

    @Test
    public void testCancelRegistration_MonChuaDangKy_BaoLoi() {
        assertThrows(RegistrationNotFoundException.class, () -> {
            cancellationService.cancelRegistration("SV001", "HP999");
        });
    }
}
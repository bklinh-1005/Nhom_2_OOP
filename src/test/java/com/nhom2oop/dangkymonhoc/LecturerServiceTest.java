package com.nhom2oop.dangkymonhoc;

import com.nhom2oop.dangkymonhoc.exception.CourseNotFoundException;
import com.nhom2oop.dangkymonhoc.exception.UserNotFoundException;
import com.nhom2oop.dangkymonhoc.model.*;
import com.nhom2oop.dangkymonhoc.repository.CourseRepository;
import com.nhom2oop.dangkymonhoc.repository.LecturerRepository;
import com.nhom2oop.dangkymonhoc.repository.RegistrationRepository;
import com.nhom2oop.dangkymonhoc.service.LecturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LecturerServiceTest {

    private LecturerService lecturerService;

    private Lecturer testLecturer;
    private Course testCourse;
    private Student testStudent;
    private Registration testRegistration;

    @BeforeEach
    public void setUp() {
        testLecturer = new Lecturer("l1", "giangvien1", "hash", "Tran Thi B", "GV001");

        Schedule schedule = new Schedule("Thu 2", "1-3", "HK1");
        testCourse = new Course("HP001", "Lap trinh Java", 3, testLecturer, 40, 1, schedule);

        testStudent = new Student("u1", "sv001", "hash", "Nguyen Van A",
                "SV001", "CNTT1", "Cong nghe thong tin", 20);

        testRegistration = new Registration("DK001", testStudent);
        testRegistration.addDetail(new RegistrationDetail(testCourse, "2026-07-28"));


        LecturerRepository fakeLecturerRepo = new LecturerRepository() {
            @Override
            public Lecturer findByLecturerId(String lecturerId) {
                return "GV001".equals(lecturerId) ? testLecturer : null;
            }
        };

        CourseRepository fakeCourseRepo = new CourseRepository() {
            @Override
            public List<Course> findAll() {
                List<Course> list = new ArrayList<>();
                list.add(testCourse);
                return list;
            }
        };

        RegistrationRepository fakeRegistrationRepo = new RegistrationRepository() {
            @Override
            public List<Registration> findAll() {
                List<Registration> list = new ArrayList<>();
                list.add(testRegistration);
                return list;
            }
        };

        lecturerService = new LecturerService();
        injectField(lecturerService, "lecturerRepository", fakeLecturerRepo);
        injectField(lecturerService, "courseRepository", fakeCourseRepo);
        injectField(lecturerService, "registrationRepository", fakeRegistrationRepo);
    }

    @Test
    public void testGetLecturerById_ThanhCong() {
        Lecturer result = lecturerService.getLecturerById("GV001");
        assertNotNull(result);
        assertEquals("Tran Thi B", result.getFullName());
        assertEquals("GV001", result.getLecturerId());
    }

    @Test
    public void testGetLecturerById_KhongTimThay() {
        assertThrows(UserNotFoundException.class, () -> {
            lecturerService.getLecturerById("GV999");
        });
    }

    @Test
    public void testGetCoursesByLecturer_ThanhCong() {
        List<Course> courses = lecturerService.getCoursesByLecturer("GV001");
        assertEquals(1, courses.size());
        assertEquals("HP001", courses.get(0).getCourseId());
    }

    @Test
    public void testGetStudentsByLecturerCourse_ThanhCong() {
        List<Student> students =
                lecturerService.getStudentsByLecturerCourse("GV001", "HP001");
        assertEquals(1, students.size());
        assertEquals("SV001", students.get(0).getStudentId());
        assertEquals("Nguyen Van A", students.get(0).getFullName());
    }

    @Test
    public void testGetStudentsByLecturerCourse_MonKhongThuocGV() {
        assertThrows(CourseNotFoundException.class, () -> {
            lecturerService.getStudentsByLecturerCourse("GV001", "HP999");
        });
    }

    @Test
    public void testGetLecturerById_MaRong() {
        assertThrows(IllegalArgumentException.class, () -> {
            lecturerService.getLecturerById("");
        });
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

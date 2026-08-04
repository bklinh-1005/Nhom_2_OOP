package com.nhom2oop.dangkymonhoc.handler;

import com.nhom2oop.dangkymonhoc.model.Course;
import com.nhom2oop.dangkymonhoc.model.Lecturer;
import com.nhom2oop.dangkymonhoc.model.Student;
import com.nhom2oop.dangkymonhoc.service.LecturerService;
import com.nhom2oop.dangkymonhoc.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lecturers")
public class LecturerHandler {

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private LogService logService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getLecturer(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Mã giảng viên không được để trống."));
        }

        Lecturer lecturer = lecturerService.getLecturerById(id);
        return ResponseEntity.ok(lecturer);
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<?> getCourses(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Mã giảng viên không được để trống."));
        }

        List<Course> courses = lecturerService.getCoursesByLecturer(id);

        logService.logLecturerAction(null, id,
                "VIEW_COURSES", "Giảng viên xem danh sách môn dạy.");

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<?> getStudents(
            @PathVariable String id,
            @RequestParam(required = false) String courseId) {

        if (id == null || id.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Mã giảng viên không được để trống."));
        }

        if (courseId != null && !courseId.isBlank()) {

            List<Student> students =
                    lecturerService.getStudentsByLecturerCourse(id, courseId);

            logService.logLecturerAction(null, id,
                    "VIEW_STUDENTS",
                    "Xem danh sách sinh viên môn " + courseId);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("lecturerId", id);
            response.put("courseId", courseId);
            response.put("totalStudents", students.size());
            response.put("students", students);

            return ResponseEntity.ok(response);
        }


        Map<String, List<Student>> studentsByCoure =
                lecturerService.getStudentsByLecturer(id);
        List<Course> courses = lecturerService.getCoursesByLecturer(id);

        logService.logLecturerAction(null, id,
                "VIEW_ALL_STUDENTS",
                "Xem danh sách toàn bộ sinh viên.");


        List<Map<String, Object>> courseStudentList = new ArrayList<>();
        for (Course course : courses) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("courseId", course.getCourseId());
            entry.put("courseName", course.getCourseName());
            entry.put("credits", course.getCredits());

            List<Student> students =
                    studentsByCoure.getOrDefault(course.getCourseId(), List.of());
            entry.put("totalStudents", students.size());
            entry.put("students", students);

            courseStudentList.add(entry);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("lecturerId", id);
        response.put("totalCourses", courses.size());
        response.put("courses", courseStudentList);

        return ResponseEntity.ok(response);
    }
}

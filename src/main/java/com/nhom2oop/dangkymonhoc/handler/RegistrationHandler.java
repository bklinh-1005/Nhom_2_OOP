package com.nhom2oop.dangkymonhoc.handler;

import com.nhom2oop.dangkymonhoc.model.Registration;
import com.nhom2oop.dangkymonhoc.model.RegistrationDetail;
import com.nhom2oop.dangkymonhoc.service.CancellationService;
import com.nhom2oop.dangkymonhoc.service.RegistrationService;
import com.nhom2oop.dangkymonhoc.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationHandler {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private CancellationService cancellationService;

    @Autowired
    private SummaryService summaryService;

    @PostMapping
    public Registration register(@RequestParam String studentId, @RequestParam String courseId) {
        return registrationService.register(studentId, courseId);
    }

    @DeleteMapping("/{studentId}")
    public Map<String, String> cancel(@PathVariable String studentId, @RequestParam String courseId) {
        cancellationService.cancelRegistration(studentId, courseId);
        return Map.of("message", "Hủy đăng ký thành công");
    }

    @GetMapping("/{studentId}/summary")
    public Map<String, Object> summary(@PathVariable String studentId) {
        List<RegistrationDetail> courses = summaryService.getRegisteredCourses(studentId);
        int totalCredits = summaryService.getTotalCredits(studentId);
        return Map.of(
                "studentId", studentId,
                "totalCredits", totalCredits,
                "courses", courses
        );
    }
}
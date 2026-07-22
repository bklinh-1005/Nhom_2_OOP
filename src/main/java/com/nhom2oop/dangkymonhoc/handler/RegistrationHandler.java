package com.nhom2oop.dangkymonhoc.handler;

import com.nhom2oop.dangkymonhoc.model.Registration;
import com.nhom2oop.dangkymonhoc.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationHandler {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public Registration register(@RequestParam String studentId, @RequestParam String courseId) {
        return registrationService.register(studentId, courseId);
    }
}
package com.nhom2oop.dangkymonhoc.handler;

import com.nhom2oop.dangkymonhoc.model.Student;
import com.nhom2oop.dangkymonhoc.service.StudentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentHandler {

    @PostConstruct
    public void init() {
        System.out.println(">>> StudentHandler loaded");
    }

    @Autowired
    private StudentService studentService;

    @GetMapping("/test")
    public String test() {
        System.out.println(">>> TEST API CALLED");
        return "OK";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable String id) {

        System.out.println(">>> getStudent called: " + id);

        Student student = studentService.getStudentById(id);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    @GetMapping("/{id}/credits")
    public ResponseEntity<Integer> getTotalCredits(@PathVariable String id) {
        return ResponseEntity.ok(studentService.getTotalCredits(id));
    }
}
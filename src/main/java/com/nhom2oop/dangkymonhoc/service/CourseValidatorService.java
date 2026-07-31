package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.model.Course;
import com.nhom2oop.dangkymonhoc.model.CourseValidator;
import com.nhom2oop.dangkymonhoc.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseValidatorService {

    private final List<CourseValidator> validators;

    @Autowired
    public CourseValidatorService(List<CourseValidator> validators) {
        this.validators = validators;
    }

    public void validateRegistration(Student student, Course course) {
        if (validators == null || validators.isEmpty()) {
            return;
        }

        for (CourseValidator validator : validators) {
            validator.validate(student, course);
        }
    }
}

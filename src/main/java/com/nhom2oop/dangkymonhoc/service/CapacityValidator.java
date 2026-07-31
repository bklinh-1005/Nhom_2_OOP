package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.exception.CourseFullException;
import com.nhom2oop.dangkymonhoc.model.Course;
import com.nhom2oop.dangkymonhoc.model.CourseValidator;
import com.nhom2oop.dangkymonhoc.model.Student;
import org.springframework.stereotype.Component;

@Component
public class CapacityValidator implements CourseValidator {

    @Override
    public void validate(Student student, Course course) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student hoặc Course không hợp lệ.");
        }

        if (course.getRegisteredSlots() >= course.getMaxSlots()) {
            throw new CourseFullException("Môn học " + course.getCourseName() + " đã hết chỗ.");
        }
    }
}

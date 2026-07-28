package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.exception.ScheduleConflictException;
import com.nhom2oop.dangkymonhoc.model.Course;
import com.nhom2oop.dangkymonhoc.model.CourseValidator;
import com.nhom2oop.dangkymonhoc.model.Registration;
import com.nhom2oop.dangkymonhoc.model.RegistrationDetail;
import com.nhom2oop.dangkymonhoc.model.Student;
import com.nhom2oop.dangkymonhoc.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleConflictValidator implements CourseValidator {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    public void validate(Student student, Course course) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student hoặc Course không hợp lệ.");
        }

        
        Registration currentRegistration = registrationRepository.findByStudentId(student.getStudentId());

        if (currentRegistration != null && currentRegistration.getDetails() != null) {
            List<RegistrationDetail> details = currentRegistration.getDetails();
            
            for (RegistrationDetail detail : details) {
                Course registeredCourse = detail.getCourse();
                
                if (registeredCourse.getSchedule() != null && course.getSchedule() != null) {
                    if (registeredCourse.getSchedule().conflictsWith(course.getSchedule())) {
                        throw new ScheduleConflictException("Trùng lịch học với môn: " + registeredCourse.getCourseName());
                    }
                }
            }
        }
        
    }
}

package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.exception.CreditLimitExceededException;
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
public class CreditLimitValidator implements CourseValidator {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    public void validate(Student student, Course course) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student hoặc Course không hợp lệ.");
        }

        
        Registration currentRegistration = registrationRepository.findByStudentId(student.getStudentId());
        
        int totalCredits = course.getCredits();

        if (currentRegistration != null && currentRegistration.getDetails() != null) {
            List<RegistrationDetail> details = currentRegistration.getDetails();
            for (RegistrationDetail detail : details) {
                totalCredits += detail.getCourse().getCredits();
            }
        }

        if (totalCredits > student.getMaxCredits()) {
            throw new CreditLimitExceededException("Vượt quá số tín chỉ tối đa cho phép (" + student.getMaxCredits() + ").");
        }
        
        
        if (course.getCredits() > student.getMaxCredits()) {
            throw new CreditLimitExceededException("Số tín chỉ của môn học vượt quá giới hạn tối đa của sinh viên.");
        }
    }
}

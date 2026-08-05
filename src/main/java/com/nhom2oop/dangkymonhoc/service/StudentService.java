package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.model.Registration;
import com.nhom2oop.dangkymonhoc.model.RegistrationDetail;
import com.nhom2oop.dangkymonhoc.model.Student;
import com.nhom2oop.dangkymonhoc.model.User;
import com.nhom2oop.dangkymonhoc.repository.RegistrationRepository;
import com.nhom2oop.dangkymonhoc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    public Student getStudentById(String id) {

        Student studentBySid = userRepository.findByStudentId(id);
        if (studentBySid != null) {
            return studentBySid;
        }

        User user = userRepository.findById(id);

        if (user instanceof Student) {
            return (Student) user;
        }

        return null;
    }

    public int getTotalCredits(String studentId) {

        Student student = getStudentById(studentId);

        if (student == null) {
            return 0;
        }

        List<Registration> registrations = registrationRepository.findAll();

        int totalCredits = 0;

        for (Registration registration : registrations) {

            if (registration.getStudent() == null) {
                continue;
            }

            if (!registration.getStudent().getId().equals(studentId)) {
                continue;
            }

            for (RegistrationDetail detail : registration.getDetails()) {

                if (detail.getCourse() != null) {
                    totalCredits += detail.getCourse().getCredits();
                }

            }
        }

        return totalCredits;
    }
}
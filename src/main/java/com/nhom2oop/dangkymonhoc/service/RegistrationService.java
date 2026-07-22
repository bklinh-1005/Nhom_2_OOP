package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.model.Registration;
import com.nhom2oop.dangkymonhoc.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    // TODO: inject CourseValidatorService, StudentService, CourseService, LogService
    // sau khi các thành viên khác hoàn thành module của mình ở Giai đoạn 1

    public Registration register(String studentId, String courseId) {
        throw new UnsupportedOperationException("Chưa cài đặt — chờ tích hợp Giai đoạn cuối");
    }
}
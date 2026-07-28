package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.exception.RegistrationNotFoundException;
import com.nhom2oop.dangkymonhoc.model.Registration;
import com.nhom2oop.dangkymonhoc.model.RegistrationDetail;
import com.nhom2oop.dangkymonhoc.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public int getTotalCredits(String studentId) {
        Registration registration = registrationRepository.findByStudentId(studentId);

        if (registration == null) {
            return 0;
        }

        int total = 0;
        for (RegistrationDetail detail : registration.getDetails()) {
            total += detail.getCourse().getCredits();
        }
        return total;
    }

    public List<RegistrationDetail> getRegisteredCourses(String studentId) {
        Registration registration = registrationRepository.findByStudentId(studentId);

        if (registration == null) {
            throw new RegistrationNotFoundException(
                    "Không tìm thấy phiếu đăng ký của sinh viên: " + studentId);
        }

        return registration.getDetails();
    }
}
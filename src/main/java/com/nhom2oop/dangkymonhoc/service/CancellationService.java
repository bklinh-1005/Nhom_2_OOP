package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.exception.RegistrationNotFoundException;
import com.nhom2oop.dangkymonhoc.model.Course;
import com.nhom2oop.dangkymonhoc.model.Registration;
import com.nhom2oop.dangkymonhoc.model.RegistrationDetail;
import com.nhom2oop.dangkymonhoc.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancellationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public void cancelRegistration(String studentId, String courseId) {
        Registration registration = registrationRepository.findByStudentId(studentId);

        if (registration == null) {
            throw new RegistrationNotFoundException(
                    "Không tìm thấy phiếu đăng ký của sinh viên: " + studentId);
        }

        RegistrationDetail detailToRemove = null;
        for (RegistrationDetail detail : registration.getDetails()) {
            if (detail.getCourse().getCourseId().equals(courseId)) {
                detailToRemove = detail;
                break;
            }
        }

        if (detailToRemove == null) {
            throw new RegistrationNotFoundException(
                    "Sinh viên " + studentId + " chưa đăng ký môn học: " + courseId);
        }

        Course course = detailToRemove.getCourse();
        course.cancel();

        registration.removeDetail(detailToRemove);
        registrationRepository.save(registration);
    }
}
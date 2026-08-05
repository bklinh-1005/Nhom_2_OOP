package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.model.Course;
import com.nhom2oop.dangkymonhoc.model.Registration;
import com.nhom2oop.dangkymonhoc.model.RegistrationDetail;
import com.nhom2oop.dangkymonhoc.model.Student;
import com.nhom2oop.dangkymonhoc.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseValidatorService validatorService;

    @Autowired
    private LogService logService;

    public Registration register(String studentId, String courseId) {
        try {
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                throw new RuntimeException("Không tìm thấy sinh viên: " + studentId);
            }
            Course course = courseService.getCourseById(courseId);
            if (course == null) {
                throw new RuntimeException("Không tìm thấy môn học: " + courseId);
            }

            validatorService.validateRegistration(student, course);

            course.register();
            courseService.updateCourse(courseId, course);

            Registration registration = registrationRepository.findByStudentId(studentId);
            if (registration == null) {
                registration = new Registration("REG_" + studentId, student);
            }

            RegistrationDetail detail = new RegistrationDetail(course, LocalDateTime.now().toString());
            registration.addDetail(detail);
            registrationRepository.save(registration);

            logService.logRegistration(student.getUsername(), studentId, courseId, "SUCCESS", "");

            return registration;
        } catch (RuntimeException ex) {
            logService.logRegistration(studentId, studentId, courseId, "ERROR", ex.getMessage());
            throw ex;
        }
    }
}
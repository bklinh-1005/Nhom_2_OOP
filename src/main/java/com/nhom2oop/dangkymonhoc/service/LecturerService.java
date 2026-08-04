package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.exception.CourseNotFoundException;
import com.nhom2oop.dangkymonhoc.exception.UserNotFoundException;
import com.nhom2oop.dangkymonhoc.model.Course;
import com.nhom2oop.dangkymonhoc.model.Lecturer;
import com.nhom2oop.dangkymonhoc.model.Registration;
import com.nhom2oop.dangkymonhoc.model.RegistrationDetail;
import com.nhom2oop.dangkymonhoc.model.Student;
import com.nhom2oop.dangkymonhoc.repository.CourseRepository;
import com.nhom2oop.dangkymonhoc.repository.LecturerRepository;
import com.nhom2oop.dangkymonhoc.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RegistrationRepository registrationRepository;


    public Lecturer getLecturerById(String lecturerId) {
        if (lecturerId == null || lecturerId.isBlank()) {
            throw new IllegalArgumentException("Mã giảng viên không được để trống.");
        }

        Lecturer lecturer = lecturerRepository.findByLecturerId(lecturerId);

        if (lecturer == null) {
            throw new UserNotFoundException(
                    "Không tìm thấy giảng viên với mã: " + lecturerId);
        }

        return lecturer;
    }


    public List<Course> getCoursesByLecturer(String lecturerId) {

        getLecturerById(lecturerId);

        List<Course> allCourses = courseRepository.findAll();
        List<Course> lecturerCourses = new ArrayList<>();

        for (Course course : allCourses) {
            if (course.getLecturer() != null
                    && lecturerId.equals(course.getLecturer().getLecturerId())) {
                lecturerCourses.add(course);
            }
        }

        return lecturerCourses;
    }


    public List<Student> getStudentsByLecturerCourse(String lecturerId, String courseId) {

        getLecturerById(lecturerId);


        Course targetCourse = null;
        List<Course> lecturerCourses = getCoursesByLecturer(lecturerId);

        for (Course course : lecturerCourses) {
            if (course.getCourseId().equals(courseId)) {
                targetCourse = course;
                break;
            }
        }

        if (targetCourse == null) {
            throw new CourseNotFoundException(
                    "Môn học " + courseId + " không thuộc giảng viên " + lecturerId);
        }


        return findStudentsByCourseId(courseId);
    }


    public Map<String, List<Student>> getStudentsByLecturer(String lecturerId) {
        List<Course> lecturerCourses = getCoursesByLecturer(lecturerId);
        Map<String, List<Student>> result = new LinkedHashMap<>();

        for (Course course : lecturerCourses) {
            List<Student> students = findStudentsByCourseId(course.getCourseId());
            result.put(course.getCourseId(), students);
        }

        return result;
    }


    private List<Student> findStudentsByCourseId(String courseId) {
        List<Registration> registrations = registrationRepository.findAll();
        List<Student> students = new ArrayList<>();

        for (Registration registration : registrations) {
            if (registration.getStudent() == null || registration.getDetails() == null) {
                continue;
            }

            for (RegistrationDetail detail : registration.getDetails()) {
                if (detail.getCourse() != null
                        && courseId.equals(detail.getCourse().getCourseId())) {
                    students.add(registration.getStudent());
                    break;
                }
            }
        }

        return students;
    }
}

package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.model.Course;
import com.nhom2oop.dangkymonhoc.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(String courseId) {
        return courseRepository.findById(courseId);
    }

    public List<Course> searchCourses(String keyword) {
        return courseRepository.search(keyword);
    }

    public boolean hasAvailableSlots(String courseId) {
        Course course = courseRepository.findById(courseId);

        if (course == null) {
            return false;
        }

        return course.getRegisteredSlots() < course.getMaxSlots();
    }

    public void updateRegisteredSlots(String courseId, int registeredSlots) {
        courseRepository.updateSlots(courseId, registeredSlots);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(String courseId, Course course) {
        return courseRepository.update(courseId, course);
    }

    public boolean deleteCourse(String courseId) {
        return courseRepository.delete(courseId);
    }
}
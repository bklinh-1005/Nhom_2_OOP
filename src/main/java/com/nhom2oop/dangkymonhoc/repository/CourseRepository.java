package com.nhom2oop.dangkymonhoc.repository;

import com.nhom2oop.dangkymonhoc.model.Course;
import com.nhom2oop.dangkymonhoc.utils.FileUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepository {

    private static final String FILE_PATH = "src/main/resources/data/courses.json";

    public List<Course> findAll() {
        return FileUtils.readList(FILE_PATH, Course.class);
    }

    public Course findById(String courseId) {
        List<Course> courses = findAll();

        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }

        return null;
    }

    public List<Course> search(String keyword) {
        List<Course> result = new ArrayList<>();

        for (Course course : findAll()) {
            if (course.getCourseId().toLowerCase().contains(keyword.toLowerCase())
                    || course.getCourseName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(course);
            }
        }

        return result;
    }


    public void updateSlots(String courseId, int registeredSlots) {

        List<Course> courses = findAll();

        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                course.setRegisteredSlots(registeredSlots);
                break;
            }
        }

        FileUtils.writeList(FILE_PATH, courses);
    }
    public Course save(Course course) {
        List<Course> courses = findAll();
        courses.add(course);
        FileUtils.writeList(FILE_PATH, courses);
        return course;
    }

    public Course update(String courseId, Course updatedCourse) {
        List<Course> courses = findAll();

        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId().equals(courseId)) {
                courses.set(i, updatedCourse);
                FileUtils.writeList(FILE_PATH, courses);
                return updatedCourse;
            }
        }

        return null;
    }

    public boolean delete(String courseId) {
        List<Course> courses = findAll();

        boolean removed = courses.removeIf(course ->
                course.getCourseId().equals(courseId));

        if (removed) {
            FileUtils.writeList(FILE_PATH, courses);
        }

        return removed;
    }
}

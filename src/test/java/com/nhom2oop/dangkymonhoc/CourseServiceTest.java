package com.nhom2oop.dangkymonhoc;

import com.nhom2oop.dangkymonhoc.model.Course;
import com.nhom2oop.dangkymonhoc.repository.CourseRepository;
import com.nhom2oop.dangkymonhoc.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void shouldReturnTrueWhenCourseHasAvailableSlots() {

        Course course = new Course(
                "C001",
                "Object Oriented Programming",
                3,
                null,
                50,
                35,
                null
        );

        when(courseRepository.findById("C001")).thenReturn(course);

        boolean result = courseService.hasAvailableSlots("C001");

        assertTrue(result);
        verify(courseRepository, times(1)).findById("C001");
    }

    @Test
    void shouldReturnFalseWhenCourseIsFull() {

        Course course = new Course(
                "C002",
                "Database",
                3,
                null,
                40,
                40,
                null
        );

        when(courseRepository.findById("C002")).thenReturn(course);

        boolean result = courseService.hasAvailableSlots("C002");

        assertFalse(result);
        verify(courseRepository, times(1)).findById("C002");
    }

    @Test
    void shouldReturnFalseWhenCourseDoesNotExist() {

        when(courseRepository.findById("C999")).thenReturn(null);

        boolean result = courseService.hasAvailableSlots("C999");

        assertFalse(result);
        verify(courseRepository, times(1)).findById("C999");
    }

    @Test
    void shouldDeleteCourseSuccessfully() {

        when(courseRepository.delete("C001")).thenReturn(true);

        boolean result = courseService.deleteCourse("C001");

        assertTrue(result);
        verify(courseRepository, times(1)).delete("C001");
    }
}
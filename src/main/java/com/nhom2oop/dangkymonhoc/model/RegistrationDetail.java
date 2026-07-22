package com.nhom2oop.dangkymonhoc.model;

public class RegistrationDetail {
    private Course course;
    private String registeredDate;

    public RegistrationDetail(Course course, String registeredDate) {
        this.course = course;
        this.registeredDate = registeredDate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
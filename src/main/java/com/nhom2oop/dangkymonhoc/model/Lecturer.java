package com.nhom2oop.dangkymonhoc.model;

public class Lecturer extends User {
    private String lecturerId;

    public Lecturer(String id, String username, String passwordHash, String fullName, String lecturerId) {
        super(id, username, passwordHash, fullName);
        this.lecturerId = lecturerId;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }
}
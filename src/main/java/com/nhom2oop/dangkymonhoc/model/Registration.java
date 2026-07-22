package com.nhom2oop.dangkymonhoc.model;

import java.util.ArrayList;
import java.util.List;

public class Registration {
    private String registrationId;
    private Student student;
    private List<RegistrationDetail> details = new ArrayList<>();

    public Registration(String registrationId, Student student) {
        this.registrationId = registrationId;
        this.student = student;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<RegistrationDetail> getDetails() {
        return details;
    }

    public void addDetail(RegistrationDetail detail) {
        this.details.add(detail);
    }

    public void removeDetail(RegistrationDetail detail) {
        this.details.remove(detail);
    }
}
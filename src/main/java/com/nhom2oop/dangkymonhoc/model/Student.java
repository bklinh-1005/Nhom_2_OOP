package com.nhom2oop.dangkymonhoc.model;

public class Student extends User {
    private String studentId;
    private String className;
    private String major;
    private int maxCredits;

    public Student(String id, String username, String passwordHash, String fullName,
                   String studentId, String className, String major, int maxCredits) {
        super(id, username, passwordHash, fullName);
        this.studentId = studentId;
        this.className = className;
        this.major = major;
        this.maxCredits = maxCredits;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(int maxCredits) {
        this.maxCredits = maxCredits;
    }
}
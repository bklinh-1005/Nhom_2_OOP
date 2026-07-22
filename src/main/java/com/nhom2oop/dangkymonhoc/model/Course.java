package com.nhom2oop.dangkymonhoc.model;

public class Course implements Registrable {
    private String courseId;
    private String courseName;
    private int credits;
    private Lecturer lecturer;
    private int maxSlots;
    private int registeredSlots;
    private Schedule schedule;

    public Course(String courseId, String courseName, int credits, Lecturer lecturer,
                  int maxSlots, int registeredSlots, Schedule schedule) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.lecturer = lecturer;
        this.maxSlots = maxSlots;
        this.registeredSlots = registeredSlots;
        this.schedule = schedule;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public void setMaxSlots(int maxSlots) {
        this.maxSlots = maxSlots;
    }

    public int getRegisteredSlots() {
        return registeredSlots;
    }

    public void setRegisteredSlots(int registeredSlots) {
        this.registeredSlots = registeredSlots;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public void register() {
        this.registeredSlots++;
    }

    @Override
    public void cancel() {
        this.registeredSlots--;
    }
}
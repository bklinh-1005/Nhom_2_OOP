package com.nhom2oop.dangkymonhoc.model;

public class Schedule {
    private String dayOfWeek;
    private String period;
    private String semester;

    public Schedule(String dayOfWeek, String period, String semester) {
        this.dayOfWeek = dayOfWeek;
        this.period = period;
        this.semester = semester;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean conflictsWith(Schedule other) {
        return this.dayOfWeek.equals(other.dayOfWeek)
                && this.period.equals(other.period)
                && this.semester.equals(other.semester);
    }
}
package com.nhom2oop.dangkymonhoc.model;

public abstract class User {
    private String id;
    private String fullName;

    public User(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    //các phương thức của lớp user
}
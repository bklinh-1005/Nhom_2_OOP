package com.nhom2oop.dangkymonhoc.model;

import com.nhom2oop.dangkymonhoc.utils.PasswordUtils;

public abstract class User {
    private String id;
    private String username;
    private String passwordHash;
    private String fullName;

    public User(String id, String username, String passwordHash, String fullName) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean login(String rawPassword) {
        return PasswordUtils.matches(rawPassword, this.passwordHash);
    }
}
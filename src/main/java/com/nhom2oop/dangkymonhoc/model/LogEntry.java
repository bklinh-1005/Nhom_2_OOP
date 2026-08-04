package com.nhom2oop.dangkymonhoc.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {

    private String id;
    private String timestamp;
    private String username;
    private String userId;
    private String role;
    private String action;
    private String description;
    private String ipAddress;
    private String status;

    public LogEntry() {
    }

    public LogEntry(String id, String username, String userId, String role,
                    String action, String description, String ipAddress, String status) {
        this.id = id;
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.username = username;
        this.userId = userId;
        this.role = role;
        this.action = action;
        this.description = description;
        this.ipAddress = ipAddress;
        this.status = status;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(timestamp).append("]\n");
        sb.append("USER: ").append(username != null ? username : "N/A").append("\n");
        sb.append("ROLE: ").append(role != null ? role : "N/A").append("\n");
        sb.append("ACTION: ").append(action != null ? action : "N/A").append("\n");
        sb.append("STATUS: ").append(status != null ? status : "N/A").append("\n");
        sb.append("DETAIL: ").append(description != null ? description : "N/A").append("\n");
        if (ipAddress != null && !ipAddress.isEmpty()) {
            sb.append("IP: ").append(ipAddress).append("\n");
        }
        sb.append("---");
        return sb.toString();
    }
}
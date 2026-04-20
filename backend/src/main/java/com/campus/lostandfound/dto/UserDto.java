package com.campus.lostandfound.dto;

import com.campus.lostandfound.model.User;

import java.time.LocalDateTime;

public class UserDto {
    private String id;
    private String name;
    private String email;
    private String studentId;
    private String role;
    private boolean isBlocked;
    private LocalDateTime createdAt;

    public UserDto() {
    }

    public UserDto(String id, String name, String email, String studentId, String role, boolean isBlocked, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.studentId = studentId;
        this.role = role;
        this.isBlocked = isBlocked;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStudentId(),
                user.getRole().name().toLowerCase(),
                user.isBlocked(),
                user.getCreatedAt());
    }
}

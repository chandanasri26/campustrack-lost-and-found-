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
    private boolean verifiedStudent;
    private String verificationStatus;
    private Integer verificationScore;
    private boolean livenessPassed;
    private boolean verifiedBadge;
    private Integer trustScore;
    private LocalDateTime verificationTimestamp;
    private LocalDateTime createdAt;

    public UserDto() {
    }

    public UserDto(String id, String name, String email, String studentId, String role, boolean isBlocked, boolean verifiedStudent, String verificationStatus, Integer verificationScore, boolean livenessPassed, boolean verifiedBadge, Integer trustScore, LocalDateTime verificationTimestamp, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.studentId = studentId;
        this.role = role;
        this.isBlocked = isBlocked;
        this.verifiedStudent = verifiedStudent;
        this.verificationStatus = verificationStatus;
        this.verificationScore = verificationScore;
        this.livenessPassed = livenessPassed;
        this.verifiedBadge = verifiedBadge;
        this.trustScore = trustScore;
        this.verificationTimestamp = verificationTimestamp;
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

    public boolean isVerifiedStudent() {
        return verifiedStudent;
    }

    public void setVerifiedStudent(boolean verifiedStudent) {
        this.verifiedStudent = verifiedStudent;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Integer getVerificationScore() {
        return verificationScore;
    }

    public void setVerificationScore(Integer verificationScore) {
        this.verificationScore = verificationScore;
    }

    public boolean isLivenessPassed() {
        return livenessPassed;
    }

    public void setLivenessPassed(boolean livenessPassed) {
        this.livenessPassed = livenessPassed;
    }

    public boolean isVerifiedBadge() {
        return verifiedBadge;
    }

    public void setVerifiedBadge(boolean verifiedBadge) {
        this.verifiedBadge = verifiedBadge;
    }

    public Integer getTrustScore() {
        return trustScore;
    }

    public void setTrustScore(Integer trustScore) {
        this.trustScore = trustScore;
    }

    public LocalDateTime getVerificationTimestamp() {
        return verificationTimestamp;
    }

    public void setVerificationTimestamp(LocalDateTime verificationTimestamp) {
        this.verificationTimestamp = verificationTimestamp;
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
                user.isVerifiedStudent(),
                user.getVerificationStatus(),
                user.getVerificationScore(),
                user.isLivenessPassed(),
                user.isVerifiedBadge(),
                user.getTrustScore(),
                user.getVerificationTimestamp(),
                user.getCreatedAt());
    }
}

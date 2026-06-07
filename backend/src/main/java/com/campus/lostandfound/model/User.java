package com.campus.lostandfound.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;
    private String studentId;
    private UserRole role = UserRole.STUDENT;
    private boolean blocked = false;
    private boolean verifiedStudent = false;
    private String verificationStatus = "unverified";
    private Integer verificationScore = 0;
    private boolean livenessPassed = false;
    private boolean verifiedBadge = false;
    private Integer trustScore = 0;
    private Integer verificationAttempts = 0;
    private LocalDateTime verificationTimestamp;
    private LocalDateTime createdAt = LocalDateTime.now();

    public User() {}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
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

    public Integer getVerificationAttempts() {
        return verificationAttempts;
    }

    public void setVerificationAttempts(Integer verificationAttempts) {
        this.verificationAttempts = verificationAttempts;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !blocked;
    }
}

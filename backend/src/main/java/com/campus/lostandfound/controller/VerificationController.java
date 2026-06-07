package com.campus.lostandfound.controller;

import com.campus.lostandfound.dto.UserDto;
import com.campus.lostandfound.dto.VerificationRequest;
import com.campus.lostandfound.model.User;
import com.campus.lostandfound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final UserRepository userRepository;

    @PostMapping("/submit")
    public ResponseEntity<UserDto> submitVerification(
            @AuthenticationPrincipal User currentUser,
            @RequestBody VerificationRequest request
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(401).build();
        }

        boolean livenessPassed = request.getLivenessPassed() != null && request.getLivenessPassed();
        int score = request.getMatchScore() != null ? request.getMatchScore() : 0;

        currentUser.setLivenessPassed(livenessPassed);
        currentUser.setVerificationScore(score);
        currentUser.setVerificationAttempts(currentUser.getVerificationAttempts() == null ? 1 : currentUser.getVerificationAttempts() + 1);
        currentUser.setVerificationTimestamp(LocalDateTime.now());

        String status;
        boolean verifiedBadge = false;
        boolean verifiedStudent = false;
        int trustScore = currentUser.getTrustScore() == null ? 0 : currentUser.getTrustScore();

        if (!livenessPassed) {
            status = "failed";
            trustScore = Math.max(0, trustScore - 2);
        } else if (score >= 85) {
            status = "verified";
            verifiedBadge = true;
            verifiedStudent = true;
            trustScore = Math.min(100, trustScore + 12);
        } else if (score >= 70) {
            status = "review";
            trustScore = Math.min(100, trustScore + 4);
        } else {
            status = "failed";
            trustScore = Math.max(0, trustScore - 1);
        }

        currentUser.setVerificationStatus(status);
        currentUser.setVerifiedBadge(verifiedBadge);
        currentUser.setVerifiedStudent(verifiedStudent);
        currentUser.setTrustScore(trustScore);

        userRepository.save(currentUser);
        return ResponseEntity.ok(UserDto.from(currentUser));
    }
}

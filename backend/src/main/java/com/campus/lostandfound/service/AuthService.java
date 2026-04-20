package com.campus.lostandfound.service;

import com.campus.lostandfound.dto.AuthResponse;
import com.campus.lostandfound.dto.LoginRequest;
import com.campus.lostandfound.dto.RegisterRequest;
import com.campus.lostandfound.dto.UserDto;
import com.campus.lostandfound.model.User;
import com.campus.lostandfound.model.UserRole;
import com.campus.lostandfound.repository.UserRepository;
import com.campus.lostandfound.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        UserRole userRole = UserRole.STUDENT;
        try {
            userRole = UserRole.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException ignored) {
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStudentId(request.getStudentId());
        user.setRole(userRole);
        user.setBlocked(false);

        user = userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, UserDto.from(user));
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.isBlocked()) {
            throw new IllegalArgumentException("Your account has been blocked. Contact an administrator.");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, UserDto.from(user));
    }
}

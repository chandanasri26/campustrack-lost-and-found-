package com.campus.lostandfound.config;

import com.campus.lostandfound.model.User;
import com.campus.lostandfound.model.UserRole;
import com.campus.lostandfound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Seed Admin if not exists
        if (!userRepository.existsByEmail("admin@campus.edu")) {
            User admin = new User();
            admin.setName("System Admin");
            admin.setEmail("admin@campus.edu");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setStudentId("ADMIN001");
            admin.setRole(UserRole.ADMIN);
            admin.setBlocked(false);
            admin.setVerifiedStudent(true);
            admin.setVerificationStatus("verified");
            admin.setTrustScore(100);
            userRepository.save(admin);
            System.out.println("Seeded admin account: admin@campus.edu / admin123");
        }

        // Seed Demo Student if not exists
        if (!userRepository.existsByEmail("student@mlrit.ac.in")) {
            User student = new User();
            student.setName("Demo Student");
            student.setEmail("student@mlrit.ac.in");
            student.setPassword(passwordEncoder.encode("password123"));
            student.setStudentId("STUDENT001");
            student.setRole(UserRole.STUDENT);
            student.setBlocked(false);
            student.setVerifiedStudent(true);
            student.setVerificationStatus("verified");
            student.setTrustScore(80);
            userRepository.save(student);
            System.out.println("Seeded student account: student@mlrit.ac.in / password123");
        }
    }
}

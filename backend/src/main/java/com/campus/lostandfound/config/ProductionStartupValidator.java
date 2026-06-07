package com.campus.lostandfound.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Profile("prod")
public class ProductionStartupValidator implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ProductionStartupValidator.class);

    @Value("${MONGODB_URI:}")
    private String mongoUri;

    @Value("${JWT_SECRET:}")
    private String jwtSecret;

    @Override
    public void run(ApplicationArguments args) {
        if (!StringUtils.hasText(mongoUri)) {
            throw new IllegalStateException("Production startup failed: MONGODB_URI is required. Set it in your hosting environment.");
        }

        if (!StringUtils.hasText(jwtSecret) || jwtSecret.length() < 64) {
            throw new IllegalStateException("Production startup failed: JWT_SECRET must be set and at least 64 characters long.");
        }

        if (!jwtSecret.matches(".*[A-Za-z].*") || !jwtSecret.matches(".*\\d.*")) {
            throw new IllegalStateException("Production startup failed: JWT_SECRET must contain letters and numbers for stronger key material.");
        }

        log.info("Production configuration validation passed for MongoDB and JWT settings.");
    }
}

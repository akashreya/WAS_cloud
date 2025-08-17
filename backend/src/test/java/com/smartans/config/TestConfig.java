package com.smartans.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

/**
 * Test configuration for WAS application tests.
 * Ensures proper test environment setup and isolation.
 */
@TestConfiguration
@Profile("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class TestConfig {
    
    // Test-specific configuration beans can be added here if needed
}
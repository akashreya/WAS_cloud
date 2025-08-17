package com.smartans;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = com.smartans.WorkspaceAllocationSystemApplication.class)
@ActiveProfiles("test")
class WorkspaceAllocationSystemApplicationTests {

    @Test
    void contextLoads() {
        // Test that the Spring Boot application context loads successfully
        // This verifies that all beans are properly configured and can be instantiated
    }
}
package com.smartans;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = WorkspaceAllocationSystemApplication.class)
@ActiveProfiles("test")
class SimpleContextTest {

    @Test
    void contextLoads() {
        // This test simply verifies that the Spring context loads successfully
    }
}
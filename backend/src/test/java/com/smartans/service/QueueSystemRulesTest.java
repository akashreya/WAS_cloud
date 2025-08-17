package com.smartans.service;

import com.smartans.entity.Employee;
import com.smartans.entity.Seat;
import com.smartans.exception.SeatAssignmentException;
import com.smartans.common.constants.WASConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * JUnit tests for Queue System Rules Business Rules.
 * 
 * Business Rules Tested:
 * - Queue Size: Limited to 10 employees at a time (BR-QS-001)
 * - Queue Eligibility: Only unassigned employees are eligible (BR-QS-002)
 * - Queue Order: First-come, first-served basis (BR-QS-003)
 * - Queue Updates: Queue automatically updates after seat assignments (BR-QS-004)
 * - Assignment Priority: Employees are assigned based on their position in the queue (BR-QS-005)
 * - Queue Validation: Assignment requests are validated against current queue status (BR-QS-006)
 * - Queue Refresh: New employees enter queue as others are assigned (BR-QS-007)
 */
@SpringBootTest(classes = com.smartans.WorkspaceAllocationSystemApplication.class)
@ActiveProfiles("test")
@Transactional
@Sql("/test-data.sql")
@DisplayName("Queue System Rules - Business Rules")
class QueueSystemRulesTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SeatService seatService;

    @BeforeEach
    void setUp() {
        // Clean slate for each test
    }

    @Test
    @DisplayName("BR-QS-001: Queue should be limited to 10 employees maximum")
    void testQueueSizeLimit() {
        // Create 15 unassigned employees
        for (int i = 1; i <= 15; i++) {
            employeeService.createEmployee(String.format("M%07d", i), "Employee " + i, 1);
        }

        List<Employee> queue = employeeService.getEmployeeQueue();
        
        // Queue should contain only first 10 employees
        assertThat(queue).hasSize(WASConstants.QUEUE_SIZE);
        assertThat(queue.get(0).getEmployeeId()).isEqualTo("M0000001");
        assertThat(queue.get(9).getEmployeeId()).isEqualTo("M0000010");
    }

    @Test
    @DisplayName("BR-QS-002: Only unassigned employees should be eligible for queue")
    void testQueueEligibilityUnassignedOnly() {
        // Create employees and seat
        Employee employee1 = employeeService.createEmployee("M0000001", "Employee One", 1);
        Employee employee2 = employeeService.createEmployee("M0000002", "Employee Two", 1);
        Seat seat = seatService.createSeat("WCP3-5F-001", "60001", false);

        // Initially both should be in queue
        assertThat(employeeService.isEmployeeInQueue("M0000001")).isTrue();
        assertThat(employeeService.isEmployeeInQueue("M0000002")).isTrue();

        // Assign employee1 to seat
        seatService.assignSeat("WCP3-5F-001", "M0000001");

        // employee1 should no longer be in queue, employee2 should remain
        assertThat(employeeService.isEmployeeInQueue("M0000001")).isFalse();
        assertThat(employeeService.isEmployeeInQueue("M0000002")).isTrue();
    }

    @Test
    @DisplayName("BR-QS-003: Queue should maintain FIFO (First-In-First-Out) order")
    void testQueueFIFOOrder() {
        // Create employees with slight delays to ensure order
        Employee emp1 = employeeService.createEmployee("M0000001", "First Employee", 1);
        Employee emp2 = employeeService.createEmployee("M0000002", "Second Employee", 1);
        Employee emp3 = employeeService.createEmployee("M0000003", "Third Employee", 1);

        List<Employee> queue = employeeService.getEmployeeQueue();
        
        // Should be in creation order (FIFO)
        assertThat(queue).hasSize(3);
        assertThat(queue.get(0).getEmployeeId()).isEqualTo("M0000001");
        assertThat(queue.get(1).getEmployeeId()).isEqualTo("M0000002");
        assertThat(queue.get(2).getEmployeeId()).isEqualTo("M0000003");
    }

    @Test
    @DisplayName("BR-QS-004: Queue should automatically update after seat assignments")
    void testQueueUpdatesAfterAssignment() {
        // Create 12 employees (queue will show first 10)
        for (int i = 1; i <= 12; i++) {
            employeeService.createEmployee(String.format("M%07d", i), "Employee " + i, 1);
        }

        List<Employee> initialQueue = employeeService.getEmployeeQueue();
        assertThat(initialQueue).hasSize(10);
        assertThat(initialQueue.get(0).getEmployeeId()).isEqualTo("M0000001");
        assertThat(initialQueue.get(9).getEmployeeId()).isEqualTo("M0000010");

        // Create seat and assign first employee
        seatService.createSeat("WCP3-5F-001", "60001", false);
        seatService.assignSeat("WCP3-5F-001", "M0000001");

        // Queue should update to show next 10 unassigned employees
        List<Employee> updatedQueue = employeeService.getEmployeeQueue();
        assertThat(updatedQueue).hasSize(10);
        assertThat(updatedQueue.get(0).getEmployeeId()).isEqualTo("M0000002");
        assertThat(updatedQueue.get(9).getEmployeeId()).isEqualTo("M0000011");
    }

    @Test
    @DisplayName("BR-QS-005: Employee assignment priority should follow queue position")
    void testAssignmentPriorityFollowsQueuePosition() {
        // Create 3 employees
        employeeService.createEmployee("M0000001", "First Employee", 1);
        employeeService.createEmployee("M0000002", "Second Employee", 1);
        employeeService.createEmployee("M0000003", "Third Employee", 1);

        // Create seat
        seatService.createSeat("WCP3-5F-001", "60001", false);

        // First employee (front of queue) should be assignable
        assertThat(employeeService.isEmployeeInQueue("M0000001")).isTrue();
        seatService.assignSeat("WCP3-5F-001", "M0000001");

        // Verify assignment
        Seat assignedSeat = seatService.getSeatById("WCP3-5F-001").orElseThrow();
        assertThat(assignedSeat.getEmployee().getEmployeeId()).isEqualTo("M0000001");
    }

    @Test
    @DisplayName("BR-QS-006: Assignment validation should check current queue status")
    void testAssignmentValidationChecksQueueStatus() {
        // Create 12 employees (only first 10 will be in queue)
        for (int i = 1; i <= 12; i++) {
            employeeService.createEmployee(String.format("M%07d", i), "Employee " + i, 1);
        }

        // Create seat
        seatService.createSeat("WCP3-5F-001", "60001", false);

        // Employee in queue (position 1) should be assignable
        assertThat(employeeService.isEmployeeInQueue("M0000001")).isTrue();
        seatService.assignSeat("WCP3-5F-001", "M0000001");

        // Employee not in queue (position 12) should NOT be assignable
        seatService.createSeat("WCP3-5F-002", "60002", false);
        assertThat(employeeService.isEmployeeInQueue("M0000012")).isFalse();
        
        assertThatThrownBy(() -> seatService.assignSeat("WCP3-5F-002", "M0000012"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("queue");
    }

    @Test
    @DisplayName("BR-QS-007: New employees should enter queue as others are assigned")
    void testNewEmployeesEnterQueueAsOthersAssigned() {
        // Create 10 employees (fills the queue)
        for (int i = 1; i <= 10; i++) {
            employeeService.createEmployee(String.format("M%07d", i), "Employee " + i, 1);
        }

        assertThat(employeeService.getEmployeeQueue()).hasSize(10);

        // Create seat and assign first employee
        seatService.createSeat("WCP3-5F-001", "60001", false);
        seatService.assignSeat("WCP3-5F-001", "M0000001");

        // Create 11th employee
        employeeService.createEmployee("M0000011", "Employee 11", 1);

        // Queue should still be 10, but now includes the 11th employee
        List<Employee> queue = employeeService.getEmployeeQueue();
        assertThat(queue).hasSize(10);
        assertThat(queue.get(0).getEmployeeId()).isEqualTo("M0000002");
        assertThat(queue.get(9).getEmployeeId()).isEqualTo("M0000011");
    }

    @Test
    @DisplayName("BR-QS-008: Queue should handle mixed employee types correctly")
    void testQueueHandlesMixedEmployeeTypes() {
        // Create mix of regular and manager employees
        employeeService.createEmployee("M0000001", "Regular Employee", 1);
        employeeService.createEmployee("M0000002", "Manager Employee", 2);
        employeeService.createEmployee("M0000003", "Regular Employee", 1);

        List<Employee> queue = employeeService.getEmployeeQueue();
        
        // All should be in queue regardless of type
        assertThat(queue).hasSize(3);
        assertThat(employeeService.isEmployeeInQueue("M0000001")).isTrue();
        assertThat(employeeService.isEmployeeInQueue("M0000002")).isTrue();
        assertThat(employeeService.isEmployeeInQueue("M0000003")).isTrue();

        // Create appropriate seats and assign
        seatService.createSeat("WCP3-5F-001", "60001", false);
        seatService.createSeat("MGR-5F-001", "70001", true);

        // Assign regular employee to regular seat
        seatService.assignSeat("WCP3-5F-001", "M0000001");
        assertThat(employeeService.isEmployeeInQueue("M0000001")).isFalse();

        // Assign manager to manager seat
        seatService.assignSeat("MGR-5F-001", "M0000002");
        assertThat(employeeService.isEmployeeInQueue("M0000002")).isFalse();

        // Last employee should still be in queue
        assertThat(employeeService.isEmployeeInQueue("M0000003")).isTrue();
    }

    @Test
    @DisplayName("BR-QS-009: Queue should persist employee order during multiple operations")
    void testQueuePersistsOrderDuringMultipleOperations() {
        // Create 5 employees
        for (int i = 1; i <= 5; i++) {
            employeeService.createEmployee(String.format("M%07d", i), "Employee " + i, 1);
        }

        // Create 3 seats
        seatService.createSeat("WCP3-5F-001", "60001", false);
        seatService.createSeat("WCP3-5F-002", "60002", false);
        seatService.createSeat("WCP3-5F-003", "60003", false);

        // Assign and verify queue order is maintained
        List<Employee> queue1 = employeeService.getEmployeeQueue();
        assertThat(queue1.get(0).getEmployeeId()).isEqualTo("M0000001");

        // Assign first employee
        seatService.assignSeat("WCP3-5F-001", "M0000001");
        
        List<Employee> queue2 = employeeService.getEmployeeQueue();
        assertThat(queue2).hasSize(4);
        assertThat(queue2.get(0).getEmployeeId()).isEqualTo("M0000002");

        // Assign second employee
        seatService.assignSeat("WCP3-5F-002", "M0000002");
        
        List<Employee> queue3 = employeeService.getEmployeeQueue();
        assertThat(queue3).hasSize(3);
        assertThat(queue3.get(0).getEmployeeId()).isEqualTo("M0000003");
    }

    @Test
    @DisplayName("BR-QS-010: Queue should handle edge cases correctly")
    void testQueueHandlesEdgeCases() {
        // Test empty queue
        List<Employee> emptyQueue = employeeService.getEmployeeQueue();
        assertThat(emptyQueue).isEmpty();

        // Test single employee queue
        employeeService.createEmployee("M0000001", "Only Employee", 1);
        List<Employee> singleQueue = employeeService.getEmployeeQueue();
        assertThat(singleQueue).hasSize(1);
        assertThat(singleQueue.get(0).getEmployeeId()).isEqualTo("M0000001");

        // Test queue at exact capacity
        for (int i = 2; i <= 10; i++) {
            employeeService.createEmployee(String.format("M%07d", i), "Employee " + i, 1);
        }
        List<Employee> fullQueue = employeeService.getEmployeeQueue();
        assertThat(fullQueue).hasSize(10);

        // Test queue beyond capacity
        employeeService.createEmployee("M0000011", "Extra Employee", 1);
        List<Employee> overQueue = employeeService.getEmployeeQueue();
        assertThat(overQueue).hasSize(10); // Should still be limited to 10
        assertThat(employeeService.isEmployeeInQueue("M0000011")).isFalse();
    }
}
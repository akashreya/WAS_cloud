package com.smartans.service;

import com.smartans.entity.Employee;
import com.smartans.entity.Seat;
import com.smartans.exception.SeatAssignmentException;
import com.smartans.exception.SeatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * JUnit tests for Seat Assignment Process Business Rules.
 * 
 * Business Rules Tested:
 * - Complete assignment validation chain (BR-SAP-001)
 * - Seat availability verification (BR-SAP-002)
 * - Employee eligibility validation (BR-SAP-003)
 * - Seat type matching requirements (BR-SAP-004)
 * - Assignment state transitions (BR-SAP-005)
 * - Reassignment process validation (BR-SAP-006)
 * - Seat swapping process validation (BR-SAP-007)
 * - Unassignment process validation (BR-SAP-008)
 * - Complex multi-step assignment scenarios (BR-SAP-009)
 * - Error handling and rollback scenarios (BR-SAP-010)
 */
@SpringBootTest(classes = com.smartans.WorkspaceAllocationSystemApplication.class)
@ActiveProfiles("test")
@Transactional
@Sql("/test-data.sql")
@DisplayName("Seat Assignment Process - Business Rules")
class SeatAssignmentProcessTest {

    @Autowired
    private SeatService seatService;

    @Autowired
    private EmployeeService employeeService;

    private Employee managerEmployee;
    private Employee regularEmployee1;
    private Employee regularEmployee2;
    private Seat managerSeat;
    private Seat regularSeat1;
    private Seat regularSeat2;

    @BeforeEach
    void setUp() {
        // Create test employees
        managerEmployee = employeeService.createEmployee("M0000001", "Manager John", 2);
        regularEmployee1 = employeeService.createEmployee("M0000002", "Regular Jane", 1);
        regularEmployee2 = employeeService.createEmployee("M0000003", "Regular Bob", 1);

        // Create test seats
        managerSeat = seatService.createSeat("MGR-5F-001", "70001", true);
        regularSeat1 = seatService.createSeat("WCP3-5F-001", "60001", false);
        regularSeat2 = seatService.createSeat("WCP3-5F-002", "60002", false);
    }

    @Test
    @DisplayName("BR-SAP-001: Complete assignment validation chain should execute in proper order")
    void testCompleteAssignmentValidationChain() {
        // Test successful assignment with all validations
        Seat assignedSeat = seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        // Verify all aspects of successful assignment
        assertThat(assignedSeat.isOccupied()).isTrue();
        assertThat(assignedSeat.getEmployee().getEmployeeId()).isEqualTo("M0000002");
        assertThat(assignedSeat.getSeatNumber()).isEqualTo("WCP3-5F-001");
        
        // Verify employee is no longer in queue
        assertThat(employeeService.isEmployeeInQueue("M0000002")).isFalse();
        
        // Verify seat shows up in occupied seats list
        assertThat(seatService.getOccupiedSeats()).hasSize(1);
        assertThat(seatService.getAvailableSeats()).hasSize(2); // 2 remaining unoccupied seats
    }

    @Test
    @DisplayName("BR-SAP-002: Should validate seat availability before assignment")
    void testSeatAvailabilityValidation() {
        // Assign seat to first employee
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        // Attempt to assign already occupied seat should fail
        assertThatThrownBy(() -> seatService.assignSeat("WCP3-5F-001", "M0000003"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("occupied");
    }

    @Test
    @DisplayName("BR-SAP-003: Should validate employee eligibility (queue status)")
    void testEmployeeEligibilityValidation() {
        // Create many employees so some are not in queue
        for (int i = 4; i <= 15; i++) {
            employeeService.createEmployee(String.format("M%07d", i), "Employee " + i, 1);
        }
        
        // Employee not in queue should not be assignable
        boolean inQueue = employeeService.isEmployeeInQueue("M0000015");
        assertThat(inQueue).isFalse();
        
        assertThatThrownBy(() -> seatService.assignSeat("WCP3-5F-001", "M0000015"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("queue");
    }

    @Test
    @DisplayName("BR-SAP-004: Should validate seat type matching requirements")
    void testSeatTypeMatchingValidation() {
        // Manager to regular seat should fail
        assertThatThrownBy(() -> seatService.assignSeat("WCP3-5F-001", "M0000001"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("Manager seat");
        
        // Regular employee to manager seat should fail
        assertThatThrownBy(() -> seatService.assignSeat("MGR-5F-001", "M0000002"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("manager seat");
        
        // Valid assignments should succeed
        seatService.assignSeat("MGR-5F-001", "M0000001"); // Manager to manager seat
        seatService.assignSeat("WCP3-5F-001", "M0000002"); // Regular to regular seat
    }

    @Test
    @DisplayName("BR-SAP-005: Should handle assignment state transitions correctly")
    void testAssignmentStateTransitions() {
        // Initial state: all unassigned
        assertThat(employeeService.getUnassignedEmployees()).hasSize(3);
        assertThat(employeeService.getAssignedEmployees()).hasSize(0);
        assertThat(seatService.getAvailableSeats()).hasSize(3);
        assertThat(seatService.getOccupiedSeats()).hasSize(0);
        
        // Assign first employee
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        // State after first assignment
        assertThat(employeeService.getUnassignedEmployees()).hasSize(2);
        assertThat(employeeService.getAssignedEmployees()).hasSize(1);
        assertThat(seatService.getAvailableSeats()).hasSize(2);
        assertThat(seatService.getOccupiedSeats()).hasSize(1);
        
        // Assign second employee
        seatService.assignSeat("MGR-5F-001", "M0000001");
        
        // State after second assignment
        assertThat(employeeService.getUnassignedEmployees()).hasSize(1);
        assertThat(employeeService.getAssignedEmployees()).hasSize(2);
        assertThat(seatService.getAvailableSeats()).hasSize(1);
        assertThat(seatService.getOccupiedSeats()).hasSize(2);
    }

    @Test
    @DisplayName("BR-SAP-006: Should validate reassignment process correctly")
    void testReassignmentProcessValidation() {
        // Assign employee first
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        // Valid reassignment should succeed
        seatService.reassignSeat("WCP3-5F-001", "WCP3-5F-002");
        
        // Verify reassignment
        Seat fromSeat = seatService.getSeatById("WCP3-5F-001").orElseThrow();
        Seat toSeat = seatService.getSeatById("WCP3-5F-002").orElseThrow();
        
        assertThat(fromSeat.isOccupied()).isFalse();
        assertThat(toSeat.isOccupied()).isTrue();
        assertThat(toSeat.getEmployee().getEmployeeId()).isEqualTo("M0000002");
    }

    @Test
    @DisplayName("BR-SAP-007: Should validate seat swapping process correctly")
    void testSeatSwappingProcessValidation() {
        // Assign both employees to seats
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        seatService.assignSeat("WCP3-5F-002", "M0000003");
        
        // Swap should succeed
        seatService.swapSeats("WCP3-5F-001", "WCP3-5F-002");
        
        // Verify swap
        Seat seat1 = seatService.getSeatById("WCP3-5F-001").orElseThrow();
        Seat seat2 = seatService.getSeatById("WCP3-5F-002").orElseThrow();
        
        assertThat(seat1.getEmployee().getEmployeeId()).isEqualTo("M0000003");
        assertThat(seat2.getEmployee().getEmployeeId()).isEqualTo("M0000002");
    }

    @Test
    @DisplayName("BR-SAP-008: Should validate unassignment process correctly")
    void testUnassignmentProcessValidation() {
        // Assign employee first
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        // Verify assignment
        Seat occupiedSeat = seatService.getSeatById("WCP3-5F-001").orElseThrow();
        assertThat(occupiedSeat.isOccupied()).isTrue();
        
        // Unassign seat
        seatService.unassignSeat("WCP3-5F-001");
        
        // Verify unassignment
        Seat unoccupiedSeat = seatService.getSeatById("WCP3-5F-001").orElseThrow();
        assertThat(unoccupiedSeat.isOccupied()).isFalse();
        
        // Employee should be back in queue
        assertThat(employeeService.isEmployeeInQueue("M0000002")).isTrue();
    }

    @Test
    @DisplayName("BR-SAP-009: Should handle complex multi-step assignment scenarios")
    void testComplexMultiStepAssignmentScenarios() {
        // Scenario: Multiple assignments, reassignments, and swaps
        
        // Step 1: Initial assignments
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        seatService.assignSeat("MGR-5F-001", "M0000001");
        
        // Step 2: Create additional employee and seat
        Employee newEmployee = employeeService.createEmployee("M0000004", "New Employee", 1);
        seatService.assignSeat("WCP3-5F-002", "M0000004");
        
        // Step 3: Reassign manager to new manager seat
        Seat newManagerSeat = seatService.createSeat("MGR-5F-002", "70002", true);
        seatService.reassignSeat("MGR-5F-001", "MGR-5F-002");
        
        // Step 4: Swap regular employees
        seatService.swapSeats("WCP3-5F-001", "WCP3-5F-002");
        
        // Verify final state
        Seat managerSeat2 = seatService.getSeatById("MGR-5F-002").orElseThrow();
        Seat regularSeatA = seatService.getSeatById("WCP3-5F-001").orElseThrow();
        Seat regularSeatB = seatService.getSeatById("WCP3-5F-002").orElseThrow();
        
        assertThat(managerSeat2.getEmployee().getEmployeeId()).isEqualTo("M0000001");
        assertThat(regularSeatA.getEmployee().getEmployeeId()).isEqualTo("M0000004");
        assertThat(regularSeatB.getEmployee().getEmployeeId()).isEqualTo("M0000002");
    }

    @Test
    @DisplayName("BR-SAP-010: Should handle error scenarios and validation failures")
    void testErrorHandlingAndValidationFailures() {
        // Test assignment to non-existent seat
        assertThatThrownBy(() -> seatService.assignSeat("NONEXISTENT", "M0000002"))
            .isInstanceOf(SeatAssignmentException.class);
        
        // Test assignment of non-existent employee
        assertThatThrownBy(() -> seatService.assignSeat("WCP3-5F-001", "M9999999"))
            .isInstanceOf(SeatAssignmentException.class);
        
        // Test reassignment from unoccupied seat
        assertThatThrownBy(() -> seatService.reassignSeat("WCP3-5F-001", "WCP3-5F-002"))
            .isInstanceOf(SeatAssignmentException.class);
        
        // Test reassignment to occupied seat
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        seatService.assignSeat("WCP3-5F-002", "M0000003");
        
        assertThatThrownBy(() -> seatService.reassignSeat("WCP3-5F-001", "WCP3-5F-002"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("occupied");
        
        // Test swap with unoccupied seat
        seatService.unassignSeat("WCP3-5F-002");
        
        assertThatThrownBy(() -> seatService.swapSeats("WCP3-5F-001", "WCP3-5F-002"))
            .isInstanceOf(SeatAssignmentException.class);
        
        // Test unassignment of unoccupied seat
        assertThatThrownBy(() -> seatService.unassignSeat("WCP3-5F-002"))
            .isInstanceOf(SeatException.class);
    }

    @Test
    @DisplayName("BR-SAP-011: Should prevent already assigned employee from being assigned again")
    void testPreventDoubleAssignment() {
        // Assign employee to first seat
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        // Attempt to assign same employee to another seat should fail
        assertThatThrownBy(() -> seatService.assignSeat("WCP3-5F-002", "M0000002"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("already assigned");
    }

    @Test
    @DisplayName("BR-SAP-012: Should handle seat deletion with assignment constraints")
    void testSeatDeletionWithAssignmentConstraints() {
        // Assign employee to seat
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        // Cannot delete occupied seat
        assertThatThrownBy(() -> seatService.deleteSeat("WCP3-5F-001"))
            .isInstanceOf(SeatException.class)
            .hasMessageContaining("occupied");
        
        // Unassign and then delete should work
        seatService.unassignSeat("WCP3-5F-001");
        seatService.deleteSeat("WCP3-5F-001");
        
        // Verify seat is deleted
        assertThat(seatService.getSeatById("WCP3-5F-001")).isEmpty();
    }

    @Test
    @DisplayName("BR-SAP-013: Should maintain referential integrity during assignments")
    void testReferentialIntegrityDuringAssignments() {
        // Assign employee
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        // Verify bidirectional relationship
        Seat assignedSeat = seatService.getSeatById("WCP3-5F-001").orElseThrow();
        Employee assignedEmployee = employeeService.getEmployeeById("M0000002").orElseThrow();
        
        assertThat(assignedSeat.getEmployee().getEmployeeId()).isEqualTo("M0000002");
        assertThat(assignedEmployee.getSeat().getSeatNumber()).isEqualTo("WCP3-5F-001");
        
        // Unassign and verify relationship is cleared
        seatService.unassignSeat("WCP3-5F-001");
        
        Seat unassignedSeat = seatService.getSeatById("WCP3-5F-001").orElseThrow();
        Employee unassignedEmployee = employeeService.getEmployeeById("M0000002").orElseThrow();
        
        assertThat(unassignedSeat.getEmployee()).isNull();
        assertThat(unassignedEmployee.getSeat()).isNull();
    }
}
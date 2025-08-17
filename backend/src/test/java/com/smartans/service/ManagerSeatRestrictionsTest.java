package com.smartans.service;

import com.smartans.entity.Employee;
import com.smartans.entity.Seat;
import com.smartans.exception.SeatAssignmentException;
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
 * JUnit tests for Manager Seat Restrictions Business Rules.
 * 
 * Business Rules Tested:
 * - Manager employees MUST be assigned to manager seats (BR-MSR-001)
 * - Non-manager employees CANNOT be assigned to manager seats (BR-MSR-002)
 * - Manager seat protection during reassignment (BR-MSR-003)
 * - Manager seat protection during swapping (BR-MSR-004)
 * - Manager-to-regular seat reassignment prevention (BR-MSR-005)
 * - Regular-to-manager seat reassignment prevention (BR-MSR-006)
 */
@SpringBootTest(classes = com.smartans.WorkspaceAllocationSystemApplication.class)
@ActiveProfiles("test")
@Transactional
@Sql("/test-data.sql")
@DisplayName("Manager Seat Restrictions - Business Rules")
class ManagerSeatRestrictionsTest {

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
        managerEmployee = employeeService.createEmployee("M0000001", "Manager John", 2); // Manager designation
        regularEmployee1 = employeeService.createEmployee("M0000002", "Regular Jane", 1); // Regular designation  
        regularEmployee2 = employeeService.createEmployee("M0000003", "Regular Bob", 1); // Regular designation

        // Create test seats
        managerSeat = seatService.createSeat("MGR-5F-001", "70001", true);
        regularSeat1 = seatService.createSeat("WCP3-5F-001", "60001", false);
        regularSeat2 = seatService.createSeat("WCP3-5F-002", "60002", false);
    }

    @Test
    @DisplayName("BR-MSR-001: Manager employees MUST be assigned to manager seats")
    void testManagerEmployeeMustUseManagerSeat() {
        // Manager should be assignable to manager seat
        Seat assignedSeat = seatService.assignSeat("MGR-5F-001", "M0000001");
        
        assertThat(assignedSeat.getEmployee().getEmployeeId()).isEqualTo("M0000001");
        assertThat(assignedSeat.getIsManagerSeat()).isTrue();
    }

    @Test
    @DisplayName("BR-MSR-002: Manager employees CANNOT be assigned to regular seats")
    void testManagerEmployeeCannotUseRegularSeat() {
        // Manager should NOT be assignable to regular seat
        assertThatThrownBy(() -> seatService.assignSeat("WCP3-5F-001", "M0000001"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("Manager seat");
    }

    @Test
    @DisplayName("BR-MSR-003: Regular employees CANNOT be assigned to manager seats")
    void testRegularEmployeeCannotUseManagerSeat() {
        // Regular employee should NOT be assignable to manager seat
        assertThatThrownBy(() -> seatService.assignSeat("MGR-5F-001", "M0000002"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("manager seat");
    }

    @Test
    @DisplayName("BR-MSR-004: Regular employees CAN be assigned to regular seats")
    void testRegularEmployeeCanUseRegularSeat() {
        // Regular employee should be assignable to regular seat
        Seat assignedSeat = seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        assertThat(assignedSeat.getEmployee().getEmployeeId()).isEqualTo("M0000002");
        assertThat(assignedSeat.getIsManagerSeat()).isFalse();
    }

    @Test
    @DisplayName("BR-MSR-005: Manager cannot be reassigned from manager seat to regular seat")
    void testManagerCannotBeReassignedToRegularSeat() {
        // Assign manager to manager seat first
        seatService.assignSeat("MGR-5F-001", "M0000001");
        
        // Attempt to reassign manager to regular seat should fail
        assertThatThrownBy(() -> seatService.reassignSeat("MGR-5F-001", "WCP3-5F-001"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("Manager");
    }

    @Test
    @DisplayName("BR-MSR-006: Regular employee cannot be reassigned to manager seat")
    void testRegularEmployeeCannotBeReassignedToManagerSeat() {
        // Assign regular employee to regular seat first
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        // Attempt to reassign regular employee to manager seat should fail
        assertThatThrownBy(() -> seatService.reassignSeat("WCP3-5F-001", "MGR-5F-001"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("manager seat");
    }

    @Test
    @DisplayName("BR-MSR-007: Manager can be reassigned between manager seats")
    void testManagerCanBeReassignedBetweenManagerSeats() {
        // Create second manager seat
        Seat managerSeat2 = seatService.createSeat("MGR-5F-002", "70002", true);
        
        // Assign manager to first manager seat
        seatService.assignSeat("MGR-5F-001", "M0000001");
        
        // Reassign manager to second manager seat should succeed
        seatService.reassignSeat("MGR-5F-001", "MGR-5F-002");
        
        // Verify reassignment
        Seat fromSeat = seatService.getSeatById("MGR-5F-001").orElseThrow();
        Seat toSeat = seatService.getSeatById("MGR-5F-002").orElseThrow();
        
        assertThat(fromSeat.isOccupied()).isFalse();
        assertThat(toSeat.isOccupied()).isTrue();
        assertThat(toSeat.getEmployee().getEmployeeId()).isEqualTo("M0000001");
    }

    @Test
    @DisplayName("BR-MSR-008: Regular employees can be reassigned between regular seats")
    void testRegularEmployeeCanBeReassignedBetweenRegularSeats() {
        // Assign regular employee to first regular seat
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        // Reassign regular employee to second regular seat should succeed
        seatService.reassignSeat("WCP3-5F-001", "WCP3-5F-002");
        
        // Verify reassignment
        Seat fromSeat = seatService.getSeatById("WCP3-5F-001").orElseThrow();
        Seat toSeat = seatService.getSeatById("WCP3-5F-002").orElseThrow();
        
        assertThat(fromSeat.isOccupied()).isFalse();
        assertThat(toSeat.isOccupied()).isTrue();
        assertThat(toSeat.getEmployee().getEmployeeId()).isEqualTo("M0000002");
    }

    @Test
    @DisplayName("BR-MSR-009: Cannot swap manager with regular employee")
    void testCannotSwapManagerWithRegularEmployee() {
        // Assign manager to manager seat and regular employee to regular seat
        seatService.assignSeat("MGR-5F-001", "M0000001");
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        
        // Attempt to swap should fail
        assertThatThrownBy(() -> seatService.swapSeats("MGR-5F-001", "WCP3-5F-001"))
            .isInstanceOf(SeatAssignmentException.class)
            .hasMessageContaining("manager");
    }

    @Test
    @DisplayName("BR-MSR-010: Can swap two regular employees between regular seats")
    void testCanSwapRegularEmployeesBetweenRegularSeats() {
        // Assign two regular employees to regular seats
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
    @DisplayName("BR-MSR-011: Can swap two managers between manager seats")
    void testCanSwapManagersBetweenManagerSeats() {
        // Create second manager employee and manager seat
        Employee managerEmployee2 = employeeService.createEmployee("M0000004", "Manager Smith", 2);
        Seat managerSeat2 = seatService.createSeat("MGR-5F-002", "70002", true);
        
        // Assign managers to manager seats
        seatService.assignSeat("MGR-5F-001", "M0000001");
        seatService.assignSeat("MGR-5F-002", "M0000004");
        
        // Swap should succeed
        seatService.swapSeats("MGR-5F-001", "MGR-5F-002");
        
        // Verify swap
        Seat seat1 = seatService.getSeatById("MGR-5F-001").orElseThrow();
        Seat seat2 = seatService.getSeatById("MGR-5F-002").orElseThrow();
        
        assertThat(seat1.getEmployee().getEmployeeId()).isEqualTo("M0000004");
        assertThat(seat2.getEmployee().getEmployeeId()).isEqualTo("M0000001");
    }

    @Test
    @DisplayName("BR-MSR-012: Manager seat assignment with multiple queue validation")
    void testManagerSeatAssignmentWithQueueValidation() {
        // Manager should be in queue and assignable to manager seat
        boolean inQueue = employeeService.isEmployeeInQueue("M0000001");
        assertThat(inQueue).isTrue();
        
        // Assignment should succeed
        Seat assignedSeat = seatService.assignSeat("MGR-5F-001", "M0000001");
        
        assertThat(assignedSeat.getEmployee().getEmployeeId()).isEqualTo("M0000001");
        assertThat(assignedSeat.getIsManagerSeat()).isTrue();
        
        // Employee should no longer be in queue after assignment
        boolean stillInQueue = employeeService.isEmployeeInQueue("M0000001");
        assertThat(stillInQueue).isFalse();
    }

    @Test
    @DisplayName("BR-MSR-013: Seat type validation during assignment process")
    void testSeatTypeValidationDuringAssignment() {
        // Test all combinations of employee type and seat type
        
        // 1. Manager + Manager Seat = Valid
        seatService.assignSeat("MGR-5F-001", "M0000001");
        Seat managerOnManagerSeat = seatService.getSeatById("MGR-5F-001").orElseThrow();
        assertThat(managerOnManagerSeat.isOccupied()).isTrue();
        
        // 2. Regular + Regular Seat = Valid
        seatService.assignSeat("WCP3-5F-001", "M0000002");
        Seat regularOnRegularSeat = seatService.getSeatById("WCP3-5F-001").orElseThrow();
        assertThat(regularOnRegularSeat.isOccupied()).isTrue();
        
        // 3. Manager + Regular Seat = Invalid (already tested above)
        // 4. Regular + Manager Seat = Invalid (already tested above)
    }
}
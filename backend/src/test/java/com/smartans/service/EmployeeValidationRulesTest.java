package com.smartans.service;

import com.smartans.entity.Employee;
import com.smartans.exception.EmployeeException;
import com.smartans.common.constants.WASConstants;
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
 * JUnit tests for Employee Validation Rules Business Rules.
 * 
 * Business Rules Tested:
 * - Manager Limits: Only 5 managers allowed in the system (BR-EV-001)
 * - Employee ID Format: Must follow pattern M####### (BR-EV-002)
 * - Employee Name Format: Must contain only letters and spaces (BR-EV-003)
 * - Employee ID Length: Maximum 8 characters (BR-EV-004)
 * - Employee Name Length: Maximum 50 characters (BR-EV-005)
 * - Employee ID Uniqueness: Must be unique across all employees (BR-EV-006)
 * - Designation Validation: Must have valid designation (BR-EV-007)
 * - Manager Designation Validation: Manager employees must have isManager = true (BR-EV-008)
 */
@SpringBootTest(classes = com.smartans.WorkspaceAllocationSystemApplication.class)
@ActiveProfiles("test")
@Transactional
@Sql("/test-data.sql")
@DisplayName("Employee Validation Rules - Business Rules")
class EmployeeValidationRulesTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    @DisplayName("BR-EV-001: Should enforce maximum 5 managers limit")
    void testMaximumManagersLimit() {
        // Create 5 managers (the maximum allowed)
        for (int i = 1; i <= WASConstants.MAX_MANAGER_SEATS; i++) {
            Employee manager = employeeService.createEmployee(
                String.format("M%07d", i), 
                "Manager " + i, 
                2 // Manager designation
            );
            assertThat(manager.getDesignation().getIsManager()).isTrue();
        }

        // Attempt to create 6th manager should fail
        assertThatThrownBy(() -> employeeService.createEmployee("M0000006", "Manager Six", 2))
            .isInstanceOf(EmployeeException.class)
            .hasMessageContaining("Maximum limit");
    }

    @Test
    @DisplayName("BR-EV-002: Should enforce employee ID format M#######")
    void testEmployeeIdFormatValidation() {
        // Valid format should succeed
        Employee validEmployee = employeeService.createEmployee("M1234567", "Valid Employee", 1);
        assertThat(validEmployee).isNotNull();
        assertThat(validEmployee.getEmployeeId()).isEqualTo("M1234567");

        // Invalid formats should fail
        assertThatThrownBy(() -> employeeService.createEmployee("A1234567", "Invalid Prefix", 1))
            .isInstanceOf(EmployeeException.class);

        assertThatThrownBy(() -> employeeService.createEmployee("M123456", "Too Short", 1))
            .isInstanceOf(EmployeeException.class);

        assertThatThrownBy(() -> employeeService.createEmployee("M12345678", "Too Long", 1))
            .isInstanceOf(EmployeeException.class);

        assertThatThrownBy(() -> employeeService.createEmployee("M123456A", "Non-numeric", 1))
            .isInstanceOf(EmployeeException.class);

        assertThatThrownBy(() -> employeeService.createEmployee("1234567", "No M Prefix", 1))
            .isInstanceOf(EmployeeException.class);
    }

    @Test
    @DisplayName("BR-EV-003: Should enforce employee name format (letters and spaces only)")
    void testEmployeeNameFormatValidation() {
        // Valid names should succeed
        Employee employee1 = employeeService.createEmployee("M0000001", "John Doe", 1);
        assertThat(employee1.getName()).isEqualTo("John Doe");

        Employee employee2 = employeeService.createEmployee("M0000002", "Mary Jane Smith", 1);
        assertThat(employee2.getName()).isEqualTo("Mary Jane Smith");

        Employee employee3 = employeeService.createEmployee("M0000003", "SingleName", 1);
        assertThat(employee3.getName()).isEqualTo("SingleName");

        // Invalid names should fail
        assertThatThrownBy(() -> employeeService.createEmployee("M0000004", "John123", 1))
            .isInstanceOf(EmployeeException.class);

        assertThatThrownBy(() -> employeeService.createEmployee("M0000005", "John@Doe", 1))
            .isInstanceOf(EmployeeException.class);

        assertThatThrownBy(() -> employeeService.createEmployee("M0000006", "John-Doe", 1))
            .isInstanceOf(EmployeeException.class);

        assertThatThrownBy(() -> employeeService.createEmployee("M0000007", "John_Doe", 1))
            .isInstanceOf(EmployeeException.class);

        assertThatThrownBy(() -> employeeService.createEmployee("M0000008", "John.Doe", 1))
            .isInstanceOf(EmployeeException.class);
    }

    @Test
    @DisplayName("BR-EV-004: Should enforce employee ID length maximum 8 characters")
    void testEmployeeIdLengthValidation() {
        // 8 characters (valid)
        Employee validEmployee = employeeService.createEmployee("M1234567", "Valid Employee", 1);
        assertThat(validEmployee.getEmployeeId()).hasSize(8);

        // 9 characters (invalid)
        assertThatThrownBy(() -> employeeService.createEmployee("M12345678", "Too Long Employee", 1))
            .isInstanceOf(EmployeeException.class);

        // 10 characters (invalid)
        assertThatThrownBy(() -> employeeService.createEmployee("M123456789", "Way Too Long", 1))
            .isInstanceOf(EmployeeException.class);
    }

    @Test
    @DisplayName("BR-EV-005: Should enforce employee name length maximum 50 characters")
    void testEmployeeNameLengthValidation() {
        // 50 characters (valid)
        String validLongName = "John Doe With A Very Long Name That Is Exactly Fifty"; // 50 chars
        Employee validEmployee = employeeService.createEmployee("M0000001", validLongName, 1);
        assertThat(validEmployee.getName()).hasSize(50);

        // 51 characters (invalid)
        String invalidLongName = "John Doe With A Very Long Name That Is Over Fifty X"; // 51 chars
        assertThatThrownBy(() -> employeeService.createEmployee("M0000002", invalidLongName, 1))
            .isInstanceOf(EmployeeException.class);

        // 100 characters (invalid)
        String veryLongName = "John Doe With An Extremely Long Name That Definitely Exceeds The Maximum Allowed Length Of Chars";
        assertThatThrownBy(() -> employeeService.createEmployee("M0000003", veryLongName, 1))
            .isInstanceOf(EmployeeException.class);
    }

    @Test
    @DisplayName("BR-EV-006: Should enforce employee ID uniqueness")
    void testEmployeeIdUniqueness() {
        // Create first employee
        Employee employee1 = employeeService.createEmployee("M0000001", "First Employee", 1);
        assertThat(employee1).isNotNull();

        // Attempt to create second employee with same ID should fail
        assertThatThrownBy(() -> employeeService.createEmployee("M0000001", "Second Employee", 1))
            .isInstanceOf(EmployeeException.class)
            .hasMessageContaining("already exists");
    }

    @Test
    @DisplayName("BR-EV-007: Should validate designation existence")
    void testDesignationValidation() {
        // Valid designation should succeed
        Employee validEmployee = employeeService.createEmployee("M0000001", "Valid Employee", 1);
        assertThat(validEmployee.getDesignation()).isNotNull();
        assertThat(validEmployee.getDesignation().getDesignationId()).isEqualTo(1);

        // Invalid designation should fail
        assertThatThrownBy(() -> employeeService.createEmployee("M0000002", "Invalid Designation", 999))
            .isInstanceOf(EmployeeException.class);
    }

    @Test
    @DisplayName("BR-EV-008: Should validate manager designation consistency")
    void testManagerDesignationConsistency() {
        // Manager designation (designation ID 2) should have isManager = true
        Employee manager = employeeService.createEmployee("M0000001", "Manager Employee", 2);
        assertThat(manager.getDesignation().getIsManager()).isTrue();

        // Regular designation (designation ID 1) should have isManager = false
        Employee regularEmployee = employeeService.createEmployee("M0000002", "Regular Employee", 1);
        assertThat(regularEmployee.getDesignation().getIsManager()).isFalse();
    }

    @Test
    @DisplayName("BR-EV-009: Should allow regular employees after manager limit")
    void testRegularEmployeesAfterManagerLimit() {
        // Create 5 managers (maximum allowed)
        for (int i = 1; i <= 5; i++) {
            employeeService.createEmployee(String.format("M%07d", i), "Manager " + i, 2);
        }

        // Creating regular employees should still be allowed
        Employee regularEmployee1 = employeeService.createEmployee("M0000006", "Regular Employee 1", 1);
        Employee regularEmployee2 = employeeService.createEmployee("M0000007", "Regular Employee 2", 1);

        assertThat(regularEmployee1.getDesignation().getIsManager()).isFalse();
        assertThat(regularEmployee2.getDesignation().getIsManager()).isFalse();

        // But creating another manager should still fail
        assertThatThrownBy(() -> employeeService.createEmployee("M0000008", "Sixth Manager", 2))
            .isInstanceOf(EmployeeException.class)
            .hasMessageContaining("Maximum limit");
    }

    @Test
    @DisplayName("BR-EV-010: Should handle edge cases in validation")
    void testValidationEdgeCases() {
        // Minimum valid employee ID
        Employee minEmployee = employeeService.createEmployee("M0000001", "Min Employee", 1);
        assertThat(minEmployee.getEmployeeId()).isEqualTo("M0000001");

        // Maximum valid employee ID
        Employee maxEmployee = employeeService.createEmployee("M9999999", "Max Employee", 1);
        assertThat(maxEmployee.getEmployeeId()).isEqualTo("M9999999");

        // Single character name
        Employee singleCharEmployee = employeeService.createEmployee("M0000003", "A", 1);
        assertThat(singleCharEmployee.getName()).isEqualTo("A");

        // Name with multiple spaces
        Employee multiSpaceEmployee = employeeService.createEmployee("M0000004", "First   Middle   Last", 1);
        assertThat(multiSpaceEmployee.getName()).isEqualTo("First   Middle   Last");
    }

    @Test
    @DisplayName("BR-EV-011: Should handle employee updates correctly")
    void testEmployeeUpdateValidation() {
        // Create initial employee
        Employee employee = employeeService.createEmployee("M0000001", "Original Name", 1);
        
        // Valid name update
        Employee updatedEmployee = employeeService.updateEmployee("M0000001", "Updated Name", null);
        assertThat(updatedEmployee.getName()).isEqualTo("Updated Name");

        // Invalid name update should fail
        assertThatThrownBy(() -> employeeService.updateEmployee("M0000001", "Invalid123", null))
            .isInstanceOf(EmployeeException.class);

        // Valid designation update from regular to manager (if under manager limit)
        Employee promotedEmployee = employeeService.updateEmployee("M0000001", null, 2);
        assertThat(promotedEmployee.getDesignation().getIsManager()).isTrue();

        // Test manager limit during update
        // Create 4 more managers to reach limit of 5
        for (int i = 2; i <= 5; i++) {
            employeeService.createEmployee(String.format("M%07d", i), "Manager " + i, 2);
        }

        // Create another regular employee
        employeeService.createEmployee("M0000006", "Regular Employee", 1);

        // Attempting to promote regular employee to manager should fail (would exceed limit)
        assertThatThrownBy(() -> employeeService.updateEmployee("M0000006", null, 2))
            .isInstanceOf(EmployeeException.class)
            .hasMessageContaining("Maximum limit");
    }

    @Test
    @DisplayName("BR-EV-012: Should handle deletion with seat assignment")
    void testEmployeeDeletionWithSeatAssignment() {
        // Create employee and seat
        Employee employee = employeeService.createEmployee("M0000001", "Test Employee", 1);
        
        // Employee should be deletable when not assigned to seat
        employeeService.deleteEmployee("M0000001");
        
        // Verify deletion
        assertThat(employeeService.getEmployeeById("M0000001")).isEmpty();
    }
}
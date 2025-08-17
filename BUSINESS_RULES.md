# Workspace Allocation System (WAS) - Business Rules Documentation

## Overview

This document outlines the comprehensive business rules that govern the Workspace Allocation System, ensuring proper seat allocation, employee management, and system integrity.

## Table of Contents

1. [Seat Management Rules](#seat-management-rules)
2. [Employee Management Rules](#employee-management-rules)
3. [Seat Assignment Business Rules](#seat-assignment-business-rules)
4. [Data Integrity Rules](#data-integrity-rules)
5. [Queue System Rules](#queue-system-rules)
6. [Error Handling and Validation](#error-handling-and-validation)
7. [System Constants](#system-constants)

---

## Seat Management Rules

### Capacity Limits

- **Maximum Total Seats**: 100 seats allowed in the system
- **Maximum Manager Seats**: 5 manager seats allowed
- **Extension Number Uniqueness**: Each seat must have a unique extension number

### Seat Assignment Rules

- **Manager Seat Requirements**:
  - Manager employees MUST be assigned to manager seats
  - Non-manager employees CANNOT be assigned to manager seats
  - Regular employees can only be assigned to regular seats
- **Occupancy Rules**:
  - Only one employee can be assigned to a seat at a time
  - Occupied seats cannot be deleted
  - Employees cannot be assigned to already occupied seats

### Seat Format Rules

- **Seat Number Format**: Must follow pattern `WCP3-5F-###` (e.g., WCP3-5F-001)
- **Extension Number Format**: Must be 5 digits (e.g., 60000-60099)
- **Seat Number Length**: Maximum 11 characters
- **Extension Number Length**: Maximum 6 characters

---

## Employee Management Rules

### Manager Limits

- **Maximum Managers**: Only 5 managers allowed in the system
- **Manager Designation**: Employees with manager designations must have `isManager = true`

### Employee Assignment Rules

- **Queue System**: Only employees in the current assignment queue can be assigned to seats
- **Queue Size**: Queue is limited to first 10 unassigned employees
- **Single Assignment**: An employee can only be assigned to one seat at a time
- **Employee ID Format**: Must follow pattern `M#######` (e.g., M1234567)
- **Employee Name Format**: Must contain only letters and spaces

### Employee Data Validation

- **Employee ID**: Required, maximum 8 characters
- **Employee Name**: Required, maximum 50 characters
- **Designation ID**: Required for employee creation

---

## Seat Assignment Business Rules

### Assignment Process

- **Queue Priority**: Employees are assigned based on their position in the queue
- **Validation Chain**: Assignment requires seat availability, employee eligibility, and proper seat type matching
- **Manager Seat Matching**: Manager employees must be assigned to manager seats only

### Reassignment Rules

- **Manager Seat Protection**: Managers cannot be reassigned to regular seats
- **Regular Employee Protection**: Regular employees cannot be reassigned to manager seats
- **Occupancy Check**: Target seat must be available for reassignment

### Seat Swapping Rules

- **Manager Swap Restrictions**: Managers cannot be swapped with regular employees
- **Seat Type Compatibility**: Both employees must be compatible with their new seat types
- **Occupancy Requirement**: Both seats must be occupied for swapping

---

## Data Integrity Rules

### Unique Constraints

- **Seat Numbers**: Must be unique across the system
- **Extension Numbers**: Must be unique across all seats
- **Employee IDs**: Must be unique across all employees
- **Employee-Seat Relationship**: One-to-one relationship (one employee per seat)

### Referential Integrity

- **Designation Requirements**: Employees must have valid designations
- **Seat Assignment**: Seats can only be assigned to valid employees
- **Cascade Rules**: Deleting an employee removes their seat assignment

---

## Queue System Rules

### Queue Management

- **Queue Size**: Limited to 10 employees at a time
- **Queue Eligibility**: Only unassigned employees are eligible
- **Queue Order**: First-come, first-served basis
- **Queue Updates**: Queue automatically updates after seat assignments

### Assignment Priority

- **Queue Position**: Employees are assigned based on their position in the queue
- **Queue Validation**: Assignment requests are validated against current queue status
- **Queue Refresh**: New employees enter queue as others are assigned

---

## Error Handling and Validation

### Business Rule Violations

- **Seat Limit Exceeded**: Error when trying to create more than 100 seats
- **Manager Seat Limit Exceeded**: Error when trying to create more than 5 manager seats
- **Manager Limit Exceeded**: Error when trying to create more than 5 managers
- **Invalid Assignment**: Error when assignment violates business rules

### Data Validation

- **Required Fields**: All mandatory fields must be provided
- **Format Validation**: Data must conform to specified formats
- **Business Logic Validation**: Operations must comply with business rules

---

## System Constants

### Capacity Limits

```java
MAX_SEATS = 100
MAX_MANAGER_SEATS = 5
MAX_MANAGERS = 5
QUEUE_SIZE = 10
```

### Format Patterns

```java
SEAT_NUMBER_PATTERN = "WCP3-5F-###"
EMPLOYEE_ID_PATTERN = "M#######"
EXTENSION_NUMBER_PATTERN = "#####" (5 digits)
```

### Error Codes

```java
// Seat Assignment Errors
Err-SA-001 = Manager Seat cannot be assigned to Employee
Err-SA-002 = Normal Seat cannot be assigned to Manager
Err-SA-003 = Manager cannot be swapped with Employee
Err-SA-004 = Employee cannot be swapped with Manager
Err-SA-005 = Manager cannot be reassigned to normal seat
Err-SA-006 = Employee cannot be reassigned to manager seat
Err-SA-007 = Seat is already occupied

// Employee Management Errors
Err-ME-001 = Already 5 managers present
Err-ME-002 = Employee already exists

// Seat Management Errors
Err-MS-001 = Already 5 Manager seats Present
Err-MS-002 = Already 100 Seats Present
Err-MS-003 = Seat already exists
Err-MS-004 = Extension number should be unique
```

---

## Implementation Notes

### Backend Implementation

- Business rules are enforced in service layer (`SeatService`, `EmployeeService`)
- Validation annotations are used in DTOs for input validation
- Constants are defined in `WASConstants.java` for legacy system
- Modern Spring Boot backend uses hardcoded constants in service classes

### Frontend Implementation

- Client-side validation enforces format requirements
- UI components respect business rules (e.g., manager seat indicators)
- Queue system is visually represented in the employee queue component

### Database Constraints

- Unique constraints on seat numbers and extension numbers
- Foreign key relationships between employees, seats, and designations
- Check constraints ensure data integrity at database level

---

## Compliance Requirements

### Business Rule Enforcement

- All seat assignments must comply with manager seat requirements
- Queue system must be respected for all assignments
- Capacity limits must be enforced at all times
- Data validation must occur at multiple layers (UI, service, database)

### Audit and Monitoring

- Business rule violations are logged with appropriate error codes
- System maintains audit trail of all seat assignments and changes
- Queue status is continuously monitored and updated

---

_This document serves as the authoritative reference for all business rules governing the Workspace Allocation System. Any changes to these rules must be documented and implemented consistently across all system layers._

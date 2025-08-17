package com.smartans.common.constants;

/**
 * Constants for Workspace Allocation System containing all business rules,
 * error codes, success codes, and system limits.
 * 
 * @author akash.kantharaj
 * @version 2.0.0
 */
public final class WASConstants {
    
    // Business Rules - Limits
    /**
     * Maximum number of manager seats allowed in the system.
     */
    public static final int MAX_MANAGER_SEATS = 5;
    
    /**
     * Maximum number of total seats allowed in the system.
     */
    public static final int MAX_TOTAL_SEATS = 100;
    
    /**
     * Maximum number of employees in the assignment queue.
     */
    public static final int QUEUE_SIZE = 10;
    
    // Error Codes - Seat Assignment
    /**
     * Error code when trying to assign manager seat to regular employee.
     */
    public static final String MANAGER_SEAT_CANNOT_BE_ASSIGNED_TO_EMPLOYEE = "ERR_SA_001";

    /**
     * Error code when trying to assign regular seat to manager.
     */
    public static final String REGULAR_SEAT_CANNOT_BE_ASSIGNED_TO_MANAGER = "ERR_SA_002";

    /**
     * Error code when trying to swap manager with employee.
     */
    public static final String MANAGER_CANNOT_BE_SWAPPED_WITH_EMPLOYEE = "ERR_SA_003";

    /**
     * Error code when trying to swap employee with manager.
     */
    public static final String EMPLOYEE_CANNOT_BE_SWAPPED_WITH_MANAGER = "ERR_SA_004";

    /**
     * Error code when trying to reassign manager to regular seat.
     */
    public static final String MANAGER_CANNOT_BE_REASSIGNED_TO_REGULAR_SEAT = "ERR_SA_005";

    /**
     * Error code when trying to reassign employee to manager seat.
     */
    public static final String EMPLOYEE_CANNOT_BE_REASSIGNED_TO_MANAGER_SEAT = "ERR_SA_006";
    
    /**
     * Error code when trying to assign employee to occupied seat.
     */
    public static final String EMPLOYEE_CANNOT_BE_ASSIGNED_TO_OCCUPIED_SEAT = "ERR_SA_007";
    
    /**
     * Error code when trying to assign employee not in queue.
     */
    public static final String EMPLOYEE_NOT_IN_QUEUE = "ERR_SA_008";

    // Error Codes - Employee Management
    /**
     * Error code when maximum manager limit is reached.
     */
    public static final String ALREADY_MAX_MANAGERS_PRESENT = "ERR_EMP_001";
    
    /**
     * Error code when employee already exists.
     */
    public static final String DUPLICATE_EMPLOYEE = "ERR_EMP_002";

    // Error Codes - Seat Management
    /**
     * Error code when maximum manager seats limit is reached.
     */
    public static final String ALREADY_MAX_MANAGER_SEATS_PRESENT = "ERR_SEAT_001";

    /**
     * Error code when maximum total seats limit is reached.
     */
    public static final String ALREADY_MAX_TOTAL_SEATS_PRESENT = "ERR_SEAT_002";

    /**
     * Error code when seat already exists.
     */
    public static final String DUPLICATE_SEAT = "ERR_SEAT_003";
    
    /**
     * Error code when extension number is not unique.
     */
    public static final String EXTENSION_NUMBER_NOT_UNIQUE = "ERR_SEAT_004";
    
    /**
     * Error code when trying to delete occupied seat.
     */
    public static final String CANNOT_DELETE_OCCUPIED_SEAT = "ERR_SEAT_005";

    // Success Codes
    /**
     * Success code for seat assignment.
     */
    public static final String SEAT_ASSIGNED_SUCCESSFULLY = "SUCCESS_SA_001";

    /**
     * Success code for seat reassignment.
     */
    public static final String SEAT_REASSIGNED_SUCCESSFULLY = "SUCCESS_SA_002";
    
    /**
     * Success code for seat swap.
     */
    public static final String SEATS_SWAPPED_SUCCESSFULLY = "SUCCESS_SA_003";
    
    /**
     * Success code for seat unassignment.
     */
    public static final String SEAT_UNASSIGNED_SUCCESSFULLY = "SUCCESS_SA_004";

    /**
     * Success code for employee creation.
     */
    public static final String EMPLOYEE_ADDED_SUCCESSFULLY = "SUCCESS_EMP_001";

    /**
     * Success code for employee deletion.
     */
    public static final String EMPLOYEE_REMOVED_SUCCESSFULLY = "SUCCESS_EMP_002";

    /**
     * Success code for seat creation.
     */
    public static final String SEAT_ADDED_SUCCESSFULLY = "SUCCESS_SEAT_001";

    /**
     * Success code for seat deletion.
     */
    public static final String SEAT_DELETED_SUCCESSFULLY = "SUCCESS_SEAT_002";

    // Database Operation Error Messages
    /**
     * Generic save operation error message.
     */
    public static final String COULD_NOT_SAVE_OBJECT = "Could not save object";
    
    /**
     * Generic update operation error message.
     */
    public static final String COULD_NOT_UPDATE_OBJECT = "Could not update object";
    
    /**
     * Generic delete operation error message.
     */
    public static final String COULD_NOT_DELETE_OBJECT = "Could not delete object";
    
    /**
     * Generic search operation error message.
     */
    public static final String COULD_NOT_SEARCH_OBJECT = "Could not search object";
    
    /**
     * Generic retrieve operation error message.
     */
    public static final String COULD_NOT_RETRIEVE_OBJECT = "Could not retrieve object";
    
    /**
     * Database connectivity error message.
     */
    public static final String DATABASE_ERROR = "Database connectivity error";
    
    /**
     * Unexpected error message.
     */
    public static final String UNEXPECTED_ERROR_OCCURRED = "Unexpected error occurred";

    // JPA Queries
    /**
     * Query to find seat by employee ID.
     */
    public static final String QUERY_SEAT_BY_EMPLOYEE_ID = 
        "SELECT s FROM Seat s WHERE s.employee.employeeId = :employeeId";

    /**
     * Query to find unassigned employees (not in any seat).
     */
    public static final String QUERY_UNASSIGNED_EMPLOYEES = 
        "SELECT e FROM Employee e WHERE e.employeeId NOT IN " +
        "(SELECT s.employee.employeeId FROM Seat s WHERE s.employee IS NOT NULL)";
    
    /**
     * Query to find assigned employees.
     */
    public static final String QUERY_ASSIGNED_EMPLOYEES = 
        "SELECT e FROM Employee e WHERE e.employeeId IN " +
        "(SELECT s.employee.employeeId FROM Seat s WHERE s.employee IS NOT NULL)";
    
    /**
     * Query to find manager employees.
     */
    public static final String QUERY_MANAGER_EMPLOYEES = 
        "SELECT e FROM Employee e WHERE e.designation.isManager = true";
    
    /**
     * Query to count manager seats.
     */
    public static final String QUERY_COUNT_MANAGER_SEATS = 
        "SELECT COUNT(s) FROM Seat s WHERE s.isManagerSeat = true";
    
    /**
     * Query to count total seats.
     */
    public static final String QUERY_COUNT_TOTAL_SEATS = 
        "SELECT COUNT(s) FROM Seat s";
    
    /**
     * Query to count manager employees.
     */
    public static final String QUERY_COUNT_MANAGERS = 
        "SELECT COUNT(e) FROM Employee e WHERE e.designation.isManager = true";

    // String Constants
    /**
     * String separator for error code and message.
     */
    public static final String ERROR_MESSAGE_SEPARATOR = ": ";
    
    /**
     * Single quote character.
     */
    public static final String SINGLE_QUOTE = "'";
    
    /**
     * Hyphen character.
     */
    public static final String HYPHEN = "-";

    /**
     * Private constructor to prevent instantiation.
     */
    private WASConstants() {
        throw new IllegalStateException("Utility class - cannot be instantiated");
    }
}
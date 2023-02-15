package main.java.com.smartans.common.constants;

/**
 * <pre>
 * <b>Description : </b>
 * WASConstants.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-12 12:10:49 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class WASConstants {
    
    /**
     * String separator for error code and message.
     */
    public static final String ERROR_MESSAGE_SEPARATOR = ": ";
    
    /**
     * MANAGER_CANNOT_BE_ASSIGNED_TO_EMPLOYEE.
     */
    public static final String MANAGER_CANNOT_BE_ASSIGNED_TO_EMPLOYEE = "Err-SA-001";

    /**
     * NORMAL_SEAT_CANNOT_BE_ASSIGNED_TO_MANAGER.
     */
    public static final String NORMAL_SEAT_CANNOT_BE_ASSIGNED_TO_MANAGER = "Err-SA-002";

    /**
     * MANAGER_CANNOT_SWAPPED_WITH_EMPLOYEE.
     */
    public static final String MANAGER_CANNOT_SWAPPED_WITH_EMPLOYEE = "Err-SA-003";

    /**
     * EMPLOYEE_CANNOT_SWAPPED_WITH_MANAGER.
     */
    public static final String EMPLOYEE_CANNOT_SWAPPED_WITH_MANAGER = "Err-SA-004";

    /**
     * MANAGER_CANNOT_BE_REASSIGNED_TO_NORMAL_SEAT.
     */
    public static final String MANAGER_CANNOT_BE_REASSIGNED_TO_NORMAL_SEAT = "Err-SA-005";

    /**
     * EMPLOYEE_CANNOT_BE_REASSIGNED_TO_MANAGER_SEAT.
     */
    public static final String EMPLOYEE_CANNOT_BE_REASSIGNED_TO_MANAGER_SEAT = "Err-SA-006";
    
    /**
     * QUERY_SEAT_WHEN_EMPLOYEE_ID.
     */
    public static final String QUERY_SEAT_WHEN_EMPLOYEE_ID = "Select seat from Seat seat join "
          + "seat.employee emp where seat.employee.employeeId = '";

    /**
     * SINGLE_QUOTE.
     */
    public static final String SINGLE_QUOTE = "'";

    /**
     * HIPHEN.
     */
    public static final String HIPHEN = "-";

    /**
     * COULD_NOT_SAVE_OBJECT.
     */
    public static final String COULD_NOT_SAVE_OBJECT = "Could not Save Object";
    
    /**
     * UNEXPECTED_ERROR_OCCURED.
     */
    public static final String UNEXPECTED_ERROR_OCCURED = "Unexpected Error occured";
    
    /**
     * COULD_NOT_UPDATE_OBJECT.
     */
    public static final String COULD_NOT_UPDATE_OBJECT = "Could not update Object";
    /**
     * COULD_NOT_DELETE_OBJECT.
     */
    public static final String COULD_NOT_DELETE_OBJECT = "Could not Delete Object";
    /**
     * COULD_NOT_SEARCH_OBJECT.
     */
    public static final String COULD_NOT_SEARCH_OBJECT = "Could not Search Object";
    /**
     * COULD_NOT_RETRIEVE_OBJECT.
     */
    public static final String COULD_NOT_RETRIEVE_OBJECT = "Could not Retrieve Object";
    
    /**
     * DB_DOWN.
     */
    public static final String DB_DOWN = "DB Down";
    /**
     * COULD_NOT_SAVE_SEAT.
     */
    public static final String COULD_NOT_SAVE_SEAT = "Could not Save Seat";
    /**
     * COULD_NOT_DELETE_SEAT.
     */
    public static final String COULD_NOT_DELETE_SEAT = "Could not Delete Seat";
    /**
     * COULD_NOT_SEARCH_SEATS.
     */
    public static final String COULD_NOT_SEARCH_SEATS = "Could not Search Seats";

    /**
     * QUERY_SEAT.
     */
    public static final String QUERY_SEAT = "from Seat";
    
    /**
     * QUERY_NOT_SEATED_EMPLOYEE.
     */
    public static final String QUERY_NOT_SEATED_EMPLOYEE = "from Employee emp where emp.employeeId not"
    		+ " in (Select seat.employee.employeeId from Seat seat where seat.employee.employeeId is not null)";
    /**
     * QUERY_SEATED_EMPLOYEE.
     */
    public static final String QUERY_SEATED_EMPLOYEE = "Select emp from Employee emp, Seat seat "
    		+ "where seat.employee.employeeId = emp.employeeId";
    
    /**
     * QUERY_MANAGERS.
     */
    public static final String QUERY_MANAGERS = "Select emp from Employee emp join emp.designation des "
    		+ "where des.isManager = true";
    
    /**
     * ALREADY_5_MANAGERS_PRESENT.
     */
    public static final String ALREADY_5_MANAGERS_PRESENT = "Err-ME-001";
    
    /**
     * COULD_NOT_DELETE_EMPLOYEE.
     */
    public static final String COULD_NOT_DELETE_EMPLOYEE = "Could not Delete Employee";
    /**
     * COULD_NOT_SAVE_EMPLOYEE.
     */
    public static final String COULD_NOT_SAVE_EMPLOYEE = "Could not Save Employee";
    
    /**
     * QUERY_DESIGNATION.
     */
    public static final String QUERY_DESIGNATION = "from Designation";
    
    /**
     * COULD_NOT_SEARCH_DESIGNATIONS.
     */
    public static final String COULD_NOT_SEARCH_DESIGNATIONS = "Could not Search Designations";
    
    /**
     * SEAT_ID.
     */
    public static final String SEAT_ID = "sid";

    /**
     * MID.
     */
    public static final String MID = "mid";
    
    /**
     * SEAT_ADDED_SUCCESSFULLY.
     */
    public static final String SEAT_ADDED_SUCCESSFULLY = "Success-MS-001";
    
    /**
     * SEAT_DELETED_SUCCESSFULLY.
     */
    public static final String SEAT_DELETED_SUCCESSFULLY = "Success-MS-002";

    /**
     * EMPLOYEE_ADDED_SUCCESSFULLY.
     */
    public static final String EMPLOYEE_ADDED_SUCCESSFULLY = "Success-ME-001";

    /**
     * EMPLOYEE_REMOVED_SUCCESSFULLY.
     */
    public static final String EMPLOYEE_REMOVED_SUCCESSFULLY = "Success-ME-002";

    /**
     * ALREADY_5_MANAGER_SEATS_PRESENT.
     */
    public static final String ALREADY_5_MANAGER_SEATS_PRESENT = "Err-MS-001";

    /**
     * SEAT_ASSIGNED_SUCCESSFULLY.
     */
    public static final String SEAT_ASSIGNED_SUCCESSFULLY = "Success-SA-001";

    /**
     * SEAT_REASSIGNED_SUCCESSFULLY.
     */
    public static final String SEAT_REASSIGNED_SUCCESSFULLY = "Success-SA-002";

    /**
     * ALREADY_100_SEATS_PRESENT.
     */
    public static final String ALREADY_100_SEATS_PRESENT = "Err-MS-002";

    /**
     * DUPLICATE_SEAT.
     */
    public static final String DUPLICATE_SEAT = "Err-MS-003";
    /**
     * DUPLICATE_EMPLOYEE.
     */
    public static final String DUPLICATE_EMPLOYEE = "Err-ME-002";
    
    /**
     * COULD_NOT_SEARCH_EMPLOYEES.
     */
    public static final String COULD_NOT_SEARCH_EMPLOYEES = "Could not Search Employees";

    /**
     * EMPLOYEE_CANNOT_BE_ASSIGNED_TO_SEATED_FROM_QUEUE.
     */
    public static final String EMPLOYEE_CANNOT_BE_ASSIGNED_TO_OCCUPIED_SEAT_FROM_QUEUE = "Err-SA-007";

    /**
     * EXTN_SHOULD_BE_UNIQUE.
     */
    public static final String EXTN_SHOULD_BE_UNIQUE = "Err-MS-004";
    /**
     * COULD_NOT_ASSIGN_SEAT.
     */
    public static final String COULD_NOT_ASSIGN_SEAT = "Could not Assign Seat";

    /**
     * COULD_NOT_SEARCH.
     */
    public static final String COULD_NOT_SEARCH = "Could not Search";

    /**
     * FIVE.
     */
    public static final int FIVE = 5;

    /**
     * HUNDRED.
     */
    public static final int HUNDRED = 100;
    
    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'WASConstants'.
     * 
     * </pre>
     */
    public WASConstants() {
        super();
    }

}

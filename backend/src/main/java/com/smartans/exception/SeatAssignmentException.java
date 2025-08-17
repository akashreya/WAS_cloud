package com.smartans.exception;

/**
 * Exception thrown when seat assignment business rules are violated.
 * 
 * @author akash.kantharaj
 * @version 2.0.0
 */
public class SeatAssignmentException extends WorkspaceAllocationException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor with message.
     * 
     * @param message exception message
     */
    public SeatAssignmentException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     * 
     * @param message exception message
     * @param cause root cause
     */
    public SeatAssignmentException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructor with error code and parameters.
     * 
     * @param errorCode error code for internationalized messages
     * @param messageParams parameters for message formatting
     */
    public SeatAssignmentException(String errorCode, Object... messageParams) {
        super(errorCode, messageParams);
    }
    
    /**
     * Constructor with error code, cause and parameters.
     * 
     * @param errorCode error code for internationalized messages
     * @param cause root cause
     * @param messageParams parameters for message formatting
     */
    public SeatAssignmentException(String errorCode, Throwable cause, Object... messageParams) {
        super(errorCode, cause, messageParams);
    }
}

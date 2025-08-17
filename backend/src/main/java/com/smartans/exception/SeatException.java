package com.smartans.exception;

/**
 * Exception thrown when seat management business rules are violated.
 * 
 * @author akash.kantharaj
 * @version 2.0.0
 */
public class SeatException extends WorkspaceAllocationException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor with message.
     * 
     * @param message exception message
     */
    public SeatException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     * 
     * @param message exception message
     * @param cause root cause
     */
    public SeatException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructor with error code and parameters.
     * 
     * @param errorCode error code for internationalized messages
     * @param messageParams parameters for message formatting
     */
    public SeatException(String errorCode, Object... messageParams) {
        super(errorCode, messageParams);
    }
    
    /**
     * Constructor with error code, cause and parameters.
     * 
     * @param errorCode error code for internationalized messages
     * @param cause root cause
     * @param messageParams parameters for message formatting
     */
    public SeatException(String errorCode, Throwable cause, Object... messageParams) {
        super(errorCode, cause, messageParams);
    }
}

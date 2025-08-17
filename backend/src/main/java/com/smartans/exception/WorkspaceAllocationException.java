package com.smartans.exception;

/**
 * Base exception class for all Workspace Allocation System exceptions.
 * Provides common functionality for all business exceptions.
 * 
 * @author akash.kantharaj
 * @version 2.0.0
 */
public class WorkspaceAllocationException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Error code associated with this exception.
     */
    private final String errorCode;
    
    /**
     * Parameters for message formatting.
     */
    private final Object[] messageParams;
    
    /**
     * Constructor with message.
     * 
     * @param message exception message
     */
    public WorkspaceAllocationException(String message) {
        super(message);
        this.errorCode = null;
        this.messageParams = null;
    }
    
    /**
     * Constructor with message and cause.
     * 
     * @param message exception message
     * @param cause root cause
     */
    public WorkspaceAllocationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
        this.messageParams = null;
    }
    
    /**
     * Constructor with error code.
     * 
     * @param errorCode error code for internationalized messages
     * @param messageParams parameters for message formatting
     */
    public WorkspaceAllocationException(String errorCode, Object... messageParams) {
        super(errorCode);
        this.errorCode = errorCode;
        this.messageParams = messageParams;
    }
    
    /**
     * Constructor with error code and cause.
     * 
     * @param errorCode error code for internationalized messages
     * @param cause root cause
     * @param messageParams parameters for message formatting
     */
    public WorkspaceAllocationException(String errorCode, Throwable cause, Object... messageParams) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.messageParams = messageParams;
    }
    
    /**
     * Get the error code associated with this exception.
     * 
     * @return error code, may be null
     */
    public String getErrorCode() {
        return errorCode;
    }
    
    /**
     * Get the message parameters for formatting.
     * 
     * @return message parameters, may be null
     */
    public Object[] getMessageParams() {
        return messageParams != null ? messageParams.clone() : null;
    }
}

package main.java.com.smartans.exception;

public class DesignationException extends WorkspaceAlloctionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DesignationException() {
		super();
	}
	
	public DesignationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'DesignationException'.
     * 
     * @param message
     * </pre>
     */
    public DesignationException(String message) {
        super(message);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'DesignationException'.
     * 
     * @param cause
     * </pre>
     */
    public DesignationException(Throwable cause) {
        super(cause);
    }

}

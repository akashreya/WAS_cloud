/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.exception;

/**
 * <pre>
 * <b>Description : </b>
 * EmployeeException.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 10:05:03 AM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class EmployeeException extends WorkspaceAlloctionException {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4365433589381162932L;

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'EmployeeException'.
     * 
     * </pre>
     */
    public EmployeeException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'EmployeeException'.
     * 
     * @param message
     * @param cause
     * </pre>
     */
    public EmployeeException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'EmployeeException'.
     * 
     * @param message
     * </pre>
     */
    public EmployeeException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'EmployeeException'.
     * 
     * @param cause
     * </pre>
     */
    public EmployeeException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    

}

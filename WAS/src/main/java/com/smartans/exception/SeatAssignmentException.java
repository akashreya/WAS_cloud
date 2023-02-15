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
 * SeatAssignmentException.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 7:48:10 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class SeatAssignmentException extends WorkspaceAlloctionException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4776316025970356352L;

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SeatAssignmentException'.
     * 
     * </pre>
     */
    public SeatAssignmentException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SeatAssignmentException'.
     * 
     * @param message
     * @param cause
     * </pre>
     */
    public SeatAssignmentException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SeatAssignmentException'.
     * 
     * @param message
     * </pre>
     */
    public SeatAssignmentException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SeatAssignmentException'.
     * 
     * @param cause
     * </pre>
     */
    public SeatAssignmentException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
    

}

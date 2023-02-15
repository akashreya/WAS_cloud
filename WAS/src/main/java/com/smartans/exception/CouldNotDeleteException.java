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
 * CouldNotDeleteException.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 5:49:14 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class CouldNotDeleteException extends SeatException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1291296082088730533L;

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotDeleteException'.
     * 
     * </pre>
     */
    public CouldNotDeleteException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotDeleteException'.
     * 
     * @param message
     * @param cause
     * </pre>
     */
    public CouldNotDeleteException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotDeleteException'.
     * 
     * @param message
     * </pre>
     */
    public CouldNotDeleteException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotDeleteException'.
     * 
     * @param cause
     * </pre>
     */
    public CouldNotDeleteException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
    

}

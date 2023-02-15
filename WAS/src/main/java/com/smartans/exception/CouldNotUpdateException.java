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
 * CouldNotUpdateException.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 7:44:54 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class CouldNotUpdateException extends WorkspaceAlloctionException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1549875810762722058L;

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotUpdateException'.
     * 
     * </pre>
     */
    public CouldNotUpdateException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotUpdateException'.
     * 
     * @param message
     * @param cause
     * </pre>
     */
    public CouldNotUpdateException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotUpdateException'.
     * 
     * @param message
     * </pre>
     */
    public CouldNotUpdateException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotUpdateException'.
     * 
     * @param cause
     * </pre>
     */
    public CouldNotUpdateException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
    

}

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
 * CouldNotSaveException.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 5:31:04 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class CouldNotSaveException extends WorkspaceAlloctionException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 5774241765027505352L;

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotSaveException'.
     * 
     * </pre>
     */
    public CouldNotSaveException() {
        super();
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotSaveException'.
     * 
     * @param message
     * @param cause
     * </pre>
     */
    public CouldNotSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotSaveException'.
     * 
     * @param message
     * </pre>
     */
    public CouldNotSaveException(String message) {
        super(message);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CouldNotSaveException'.
     * 
     * @param cause
     * </pre>
     */
    public CouldNotSaveException(Throwable cause) {
        super(cause);
    }

}

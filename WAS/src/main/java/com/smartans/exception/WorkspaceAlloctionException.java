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
 * WorkspaceAlloctionException.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 5:32:34 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class WorkspaceAlloctionException extends Exception {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8931439777085883081L;

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'WorkspaceAlloctionException'.
     * 
     * </pre>
     */
    public WorkspaceAlloctionException() {
        super();
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'WorkspaceAlloctionException'.
     * 
     * @param message
     * @param cause
     * </pre>
     */
    public WorkspaceAlloctionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'WorkspaceAlloctionException'.
     * 
     * @param message
     * </pre>
     */
    public WorkspaceAlloctionException(String message) {
        super(message);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'WorkspaceAlloctionException'.
     * 
     * @param cause
     * </pre>
     */
    public WorkspaceAlloctionException(Throwable cause) {
        super(cause);
    }

    

}

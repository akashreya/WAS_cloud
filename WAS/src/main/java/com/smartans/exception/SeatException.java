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
 * SeatException.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 5:34:41 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class SeatException extends WorkspaceAlloctionException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 6377995695602121814L;

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SeatException'.
     * 
     * </pre>
     */
    public SeatException() {
        super();
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SeatException'.
     * 
     * @param message
     * @param cause
     * </pre>
     */
    public SeatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SeatException'.
     * 
     * @param message
     * </pre>
     */
    public SeatException(String message) {
        super(message);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SeatException'.
     * 
     * @param cause
     * </pre>
     */
    public SeatException(Throwable cause) {
        super(cause);
    }
    

}

/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seats.dtos;

import java.io.Serializable;

/**
 * <pre>
 * <b>Description : </b>
 * UpdateSeatDTO.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 12:02:36 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class UpdateSeatType extends SeatType implements Serializable{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 7921061941748160529L;
    /**
     * newSeatName.
     */
    private String newSeatName;
    /**
     * newExtension.
     */
    private String newExtension;
    
    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CreateSeatType'.
     * 
     * @param seatName
     * @param isManagerSeat
     * </pre>
     */
    public UpdateSeatType(String seatName, boolean isManagerSeat, String newSeatName, String newExtension) {
        super(seatName, isManagerSeat);
        this.newSeatName = newSeatName;
        this.newExtension = newExtension;
    }
    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'newSeatName' attribute value.
     * 
     * @return newSeatName , null if not found.
     * </pre>
     */
    
    public String getNewSeatName() {
        return newSeatName;
    }
    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'newSeatName' attribute value.
     * 
     * @param newSeatNameParam , may be null.
     * </pre>
     */
    public void setNewSeatName(String newSeatName) {
        this.newSeatName = newSeatName;
    }
    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'newExtension' attribute value.
     * 
     * @return newExtension , null if not found.
     * </pre>
     */
    
    public String getNewExtension() {
        return newExtension;
    }
    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'newExtension' attribute value.
     * 
     * @param newExtensionParam , may be null.
     * </pre>
     */
    public void setNewExtension(String newExtension) {
        this.newExtension = newExtension;
    }
    

}

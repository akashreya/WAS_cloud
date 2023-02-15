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

/**
 * <pre>
 * <b>Description : </b>
 * SeatType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 12:28:34 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class SeatType {
    /**
     * seatName.
     */
    private String seatName;

    /**
     * extensionNumber.
     */
    private String extensionNumber;
    
    /**
     * isManagerSeat.
     */
    private boolean isManagerSeat;
    
    
    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SeatType'.
     * 
     * </pre>
     */
    public SeatType() {
    }
    
    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SeatType'.
     * 
     * @param seatName
     * @param isManagerSeat
     * </pre>
     */
    public SeatType(String seatName, boolean isManagerSeat) {
        super();
        this.seatName = seatName;
        this.isManagerSeat = isManagerSeat;
    }
    
    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'SeatType'.
     * 
     * @param seatName
     * @param extensionNumber
     * @param isManagerSeat
     * </pre>
     */
    public SeatType(String seatName, String extensionNumber, boolean isManagerSeat) {
        super();
        this.seatName = seatName;
        this.extensionNumber = extensionNumber;
        this.isManagerSeat = isManagerSeat;
    }

    

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'isManagerSeat' attribute value.
     * 
     * @return isManagerSeat , null if not found.
     * </pre>
     */
    
    public boolean getIsManagerSeat() {
        return isManagerSeat;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'isManagerSeat' attribute value.
     * 
     * @param isManagerSeatParam , may be null.
     * </pre>
     */
    public void setManagerSeat(boolean isManagerSeat) {
        this.isManagerSeat = isManagerSeat;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'seatName' attribute value.
     * 
     * @return seatName , null if not found.
     * </pre>
     */
    
    public String getSeatName() {
        return seatName;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'seatName' attribute value.
     * 
     * @param seatNameParam , may be null.
     * </pre>
     */
    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }
    
    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'extensionNumber' attribute value.
     * 
     * @return extensionNumber , null if not found.
     * </pre>
     */
    
    public String getExtensionNumber() {
        return extensionNumber;
    }
    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'extensionNumber' attribute value.
     * 
     * @param extensionNumberParam , may be null.
     * </pre>
     */
    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }
}

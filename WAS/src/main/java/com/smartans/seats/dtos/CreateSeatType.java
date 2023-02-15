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
 * CreateSeatDTO.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 11:41:27 AM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class CreateSeatType extends SeatType implements Serializable{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -6499698352046018938L;
    
    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CreateSeatType'.
     * 
     * @param seatName
     * @param isManagerSeat
     * </pre>
     */
    public CreateSeatType(String seatName, boolean isManagerSeat) {
        super(seatName, isManagerSeat);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CreateSeatType'.
     * 
     * @param seatName
     * @param isManagerSeat
     * </pre>
     */
    public CreateSeatType(String seatName, String extensionNumber, boolean isManagerSeat) {
        super(seatName, extensionNumber, isManagerSeat);
        // TODO Auto-generated constructor stub
    }
    
    

}

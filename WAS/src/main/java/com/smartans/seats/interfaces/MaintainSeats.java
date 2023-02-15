/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seats.interfaces;

import main.java.com.smartans.seats.dtos.CreateSeatType;
import main.java.com.smartans.seats.dtos.DeleteSeatType;
import main.java.com.smartans.seats.dtos.ShowSeatType;
import main.java.com.smartans.seats.dtos.ShowSeatsResultSetType;
import main.java.com.smartans.seats.dtos.UpdateSeatType;

/**
 * <pre>
 * <b>Description : </b>
 * MaintainSeats.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 11:40:14 AM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public interface MaintainSeats {

    /**
     * <pre>
     * <b>Description : </b>
     * createSeats.
     * 
     * @param createSeatDTO , may be null.
     * @return ShowSeatDTO , null if not found.
     * </pre>
     */
    ShowSeatType createSeats(CreateSeatType createSeatDTO);
    
    /**
     * <pre>
     * <b>Description : </b>
     * deleteSeat.
     * 
     * @param deleteSeatType , may be null.
     * @return ShowSeatDTO , null if not found.
     * </pre>
     */
    ShowSeatType deleteSeat(DeleteSeatType deleteSeatType);
    
    /**
     * <pre>
     * <b>Description : </b>
     * updateSeat.
     * 
     * @param updateSeatDTO , may be null.
     * @return ShowSeatDTO , null if not found.
     * </pre>
     */
    ShowSeatType updateSeat(UpdateSeatType updateSeatDTO);
    
    
    /**
     * <pre>
     * <b>Description : </b>
     * searchSeats.
     * 
     * @return set of seats ShowSeatsResultSetType , null if not found.
     * </pre>
     */
    ShowSeatsResultSetType searchSeats();
    
    
    
    
}

/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seats.dao;

import java.util.List;

import main.java.com.smartans.entity.Seat;
import main.java.com.smartans.exception.SeatException;

/**
 * <pre>
 * <b>Description : </b>
 * ManageSeatsDao.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 4:46:03 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public interface ManageSeatsDao {
    
    /**
     * <pre>
     * <b>Description : </b>
     * addSeat.
     * 
     * @param seat
     * @return
     * </pre>
     * @throws SeatException 
     */
    Seat addSeat(Seat seat) throws SeatException;

    /**
     * <pre>
     * <b>Description : </b>
     * removeSeat.
     * 
     * @param seatName
     * @return
     * </pre>
     */
    boolean removeSeat(String seatName) throws SeatException;

    /**
     * <pre>
     * <b>Description : </b>
     * getSeats.
     * 
     * @return
     * </pre>
     */
    List<Seat> getSeats() throws SeatException;

}

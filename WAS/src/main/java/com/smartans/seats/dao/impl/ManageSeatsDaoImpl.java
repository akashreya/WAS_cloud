/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seats.dao.impl;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.smartans.common.constants.WASConstants;
import main.java.com.smartans.entity.Seat;
import main.java.com.smartans.exception.CouldNotDeleteException;
import main.java.com.smartans.exception.CouldNotSearchException;
import main.java.com.smartans.exception.SeatException;
import main.java.com.smartans.seats.dao.ManageSeatsDao;

import org.hibernate.exception.ConstraintViolationException;

/**
 * <pre>
 * <b>Description : </b>
 * ManageSeatsDaoImpl.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 4:53:45 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class ManageSeatsDaoImpl extends BaseDao implements ManageSeatsDao{


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
    @Override
    public Seat addSeat(Seat seat) throws SeatException {
        Seat addedSeat = null;
        try {
            addedSeat =  (Seat) create(seat);
        }
        catch (Exception ex) {
            ex = (Exception) ex.getCause();
            if (ex.getCause() instanceof ConstraintViolationException) {
                ex = (Exception) ex.getCause();
                if (ex.getCause() instanceof BatchUpdateException
                    && ex.getCause().getMessage().contains("Duplicate entry")) {
                    if (ex.getCause().getMessage().contains("PRIMARY")) {
                        throw new SeatException(WASConstants.DUPLICATE_SEAT);
                    }
                    if (ex.getCause().getMessage().contains("EXTENSION_NUMBER")) {
                        throw new SeatException(WASConstants.EXTN_SHOULD_BE_UNIQUE);
                    }

                }
            }
            throw new SeatException(WASConstants.COULD_NOT_SAVE_SEAT);
        }
        return addedSeat;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * removeSeat.
     * 
     * @param seatName
     * @return
     * </pre>
     */
    @Override
    public boolean removeSeat(String seatName) throws SeatException {
        boolean isSeatDeleted = false;
        try {
            Seat seat = (Seat) retrieve(Seat.class, seatName);
            delete(seat);
            isSeatDeleted = true;
        }
        catch (CouldNotDeleteException ex) {
            throw new SeatException(WASConstants.COULD_NOT_DELETE_SEAT);
        }
        catch (Exception ex) {
            throw new SeatException(WASConstants.DB_DOWN);
        }
        return isSeatDeleted;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * getSeats.
     * 
     * @return
     * @throws SeatException
     * </pre>
     */
    @Override
    public List<Seat> getSeats() throws SeatException {
        List<Seat> seats = null;
        try {
            String namedQuery = WASConstants.QUERY_SEAT;
            List<Object> objects = getObjects(namedQuery) ;
            if (objects != null && !objects.isEmpty()) {
                seats = new ArrayList<Seat>();
                for (Object object : objects) {
                    seats.add((Seat) object);
                }
            }
        }
        catch (CouldNotSearchException ex) {
            throw new SeatException(WASConstants.COULD_NOT_SEARCH_SEATS);
        }
        catch (Exception ex) {
            throw new SeatException(WASConstants.DB_DOWN);
        }
        return seats;
    }

    


}

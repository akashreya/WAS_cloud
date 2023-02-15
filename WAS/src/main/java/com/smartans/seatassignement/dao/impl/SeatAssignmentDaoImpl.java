/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seatassignement.dao.impl;

import java.util.List;

import main.java.com.smartans.common.constants.WASConstants;
import main.java.com.smartans.entity.Seat;
import main.java.com.smartans.exception.CouldNotSearchException;
import main.java.com.smartans.exception.CouldNotUpdateException;
import main.java.com.smartans.exception.SeatAssignmentException;
import main.java.com.smartans.seatassignement.dao.SeatAssignmentDao;
import main.java.com.smartans.seats.dao.impl.BaseDao;

/**
 * <pre>
 * <b>Description : </b>
 * SeatAssignmentDaoImpl.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 7:33:02 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class SeatAssignmentDaoImpl extends BaseDao implements SeatAssignmentDao {
    
    /**
     * <pre>
     * <b>Description : </b>
     * AssignSeat.
     * 
     * @param assignSeat
     * @return
     * </pre>
     * @throws SeatAssignmentException 
     */
    public Seat assignSeat(Seat assignSeat) throws SeatAssignmentException {
        Seat updatedSeat = null;
        try {
            updatedSeat = (Seat) update(assignSeat);
        }
        catch (CouldNotUpdateException ex) {
            throw new SeatAssignmentException(WASConstants.COULD_NOT_ASSIGN_SEAT);
        }
        catch (Exception ex) {
            throw new SeatAssignmentException(WASConstants.DB_DOWN);
        }
        return updatedSeat;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * retrieveObject.
     * 
     * @param className
     * @param id
     * @return
     * @throws SeatAssignmentException
     * </pre>
     */
    public Object retrieveObject(Class<?> className, String id) throws SeatAssignmentException {
        try {
            return retrieve(className,id);
        }
        catch (CouldNotSearchException ex) {
            throw new SeatAssignmentException(WASConstants.COULD_NOT_SEARCH);
        }
        catch (Exception ex) {
            throw new SeatAssignmentException(WASConstants.DB_DOWN);
        }
    }

    /**
     * <pre>
     * <b>Description : </b>
     * search.
     * 
     * @param query
     * @return
     * @throws SeatAssignmentException
     * </pre>
     */
    public List<Object> search(String query) throws SeatAssignmentException {
        List<Object> objects = null;
        try {
            objects =  getObjects(query);
        }
        catch (CouldNotSearchException ex) {
            throw new SeatAssignmentException(WASConstants.COULD_NOT_SEARCH);
        }
        catch (Exception ex) {
            throw new SeatAssignmentException(WASConstants.DB_DOWN);
        }
        return objects;
    }
}

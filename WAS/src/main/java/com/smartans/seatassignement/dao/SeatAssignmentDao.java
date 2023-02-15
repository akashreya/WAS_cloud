/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seatassignement.dao;

import java.util.List;

import main.java.com.smartans.entity.Seat;
import main.java.com.smartans.exception.SeatAssignmentException;

/**
 * <pre>
 * <b>Description : </b>
 * SeatAssignmentDao.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 7:31:26 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public interface SeatAssignmentDao {
    
    /**
     * <pre>
     * <b>Description : </b>
     * assignSeat.
     * 
     * @param assignSeat
     * @return
     * @throws SeatAssignmentException
     * </pre>
     */
    Seat assignSeat(Seat assignSeat) throws SeatAssignmentException;
    
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
    Object retrieveObject(Class<?> className, String id) throws SeatAssignmentException;
    
    List<Object> search(String query) throws SeatAssignmentException;

}

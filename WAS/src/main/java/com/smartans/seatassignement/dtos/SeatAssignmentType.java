/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seatassignement.dtos;

import main.java.com.smartans.employees.dtos.EmployeeType;
import main.java.com.smartans.seats.dtos.SeatType;

/**
 * <pre>
 * <b>Description : </b>
 * AssignSeatType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 1:41:56 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class SeatAssignmentType {
    
    /**
     * newSeatName.
     */
    private SeatType seatType;
    
    /**
     * employeeType.
     */
    private EmployeeType employeeType;

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'employeeType' attribute value.
     * 
     * @return employeeType , null if not found.
     * </pre>
     */
    
    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'employeeType' attribute value.
     * 
     * @param employeeTypeParam , may be null.
     * </pre>
     */
    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'seatType' attribute value.
     * 
     * @return seatType , null if not found.
     * </pre>
     */
    
    public SeatType getSeatType() {
        return seatType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'seatType' attribute value.
     * 
     * @param seatTypeParam , may be null.
     * </pre>
     */
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    
    
    

}

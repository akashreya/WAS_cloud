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

/**
 * <pre>
 * <b>Description : </b>
 * SwapSeatType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 1:47:18 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class SwapSeatType {

    /**
     * employee.
     */
    private SeatAssignmentType firstEmployee;
    
    /**
     * swappedEmployee.
     */
    private SeatAssignmentType swappedEmployee;

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'employee' attribute value.
     * 
     * @return employee , null if not found.
     * </pre>
     */
    
    public SeatAssignmentType getEmployee() {
        return firstEmployee;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'employee' attribute value.
     * 
     * @param employeeParam , may be null.
     * </pre>
     */
    public void setEmployee(SeatAssignmentType employee) {
        this.firstEmployee = employee;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'swappedEmployee' attribute value.
     * 
     * @return swappedEmployee , null if not found.
     * </pre>
     */
    
    public SeatAssignmentType getSwappedEmployee() {
        return swappedEmployee;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'swappedEmployee' attribute value.
     * 
     * @param swappedEmployeeParam , may be null.
     * </pre>
     */
    public void setSwappedEmployee(SeatAssignmentType swappedEmployee) {
        this.swappedEmployee = swappedEmployee;
    }
}

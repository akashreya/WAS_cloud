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
import java.util.List;

import main.java.com.smartans.employees.dtos.EmployeeType;


/**
 * <pre>
 * <b>Description : </b>
 * ShowSeatDTO.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 11:52:48 AM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class ShowSeatType extends SeatType implements Serializable{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8492579350731915652L;

    /**
     * employeeType.
     */
    private EmployeeType employeeType;
    
    /**
     * errorMessage.
     */
    private List<String> errorMessage;
    /**
     * isSuccess.
     */
    private boolean isSuccess;
    
    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CreateSeatType'.
     * 
     * @param seatName
     * @param isManagerSeat
     * </pre>
     */
    public ShowSeatType(String seatName, boolean isManagerSeat) {
        super(seatName, isManagerSeat);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'ShowSeatType'.
     * 
     * </pre>
     */
    public ShowSeatType() {
        super();
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'errorMessage' attribute value.
     * 
     * @return errorMessage , null if not found.
     * </pre>
     */
    
    public List<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'errorMessage' attribute value.
     * 
     * @param errorMessageParam , may be null.
     * </pre>
     */
    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'isSuccess' attribute value.
     * 
     * @return isSuccess , null if not found.
     * </pre>
     */
    
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'isSuccess' attribute value.
     * 
     * @param isSuccessParam , may be null.
     * </pre>
     */
    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

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

}

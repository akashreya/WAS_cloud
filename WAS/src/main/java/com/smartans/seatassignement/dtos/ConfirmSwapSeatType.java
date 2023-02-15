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

import java.util.List;

/**
 * <pre>
 * <b>Description : </b>
 * ConfirmSwapSeatType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 2:13:28 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class ConfirmSwapSeatType {

    /**
     * firstEmployee.
     */
    private ConfirmSeatType firstEmployee;
    
    /**
     * swappedEmployee.
     */
    private ConfirmSeatType swappedEmployee;
    
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
     * Get the 'firstEmployee' attribute value.
     * 
     * @return firstEmployee , null if not found.
     * </pre>
     */
    
    public ConfirmSeatType getFirstEmployee() {
        return firstEmployee;
    }
    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'firstEmployee' attribute value.
     * 
     * @param firstEmployeeParam , may be null.
     * </pre>
     */
    public void setFirstEmployee(ConfirmSeatType firstEmployee) {
        this.firstEmployee = firstEmployee;
    }
    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'swappedEmployee' attribute value.
     * 
     * @return swappedEmployee , null if not found.
     * </pre>
     */
    
    public ConfirmSeatType getSwappedEmployee() {
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
    public void setSwappedEmployee(ConfirmSeatType swappedEmployee) {
        this.swappedEmployee = swappedEmployee;
    }
}

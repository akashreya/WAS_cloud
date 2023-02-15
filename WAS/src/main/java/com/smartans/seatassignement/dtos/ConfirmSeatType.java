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
 * ConfirmSeatType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 2:04:35 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class ConfirmSeatType {
    
    /**
     * seatAssignmentType.
     */
    private SeatAssignmentType seatAssignmentType;
    
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
     * Get the 'seatAssignmentType' attribute value.
     * 
     * @return seatAssignmentType , null if not found.
     * </pre>
     */
    
    public SeatAssignmentType getSeatAssignmentType() {
        return seatAssignmentType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'seatAssignmentType' attribute value.
     * 
     * @param seatAssignmentTypeParam , may be null.
     * </pre>
     */
    public void setSeatAssignmentType(SeatAssignmentType seatAssignmentType) {
        this.seatAssignmentType = seatAssignmentType;
    }

}

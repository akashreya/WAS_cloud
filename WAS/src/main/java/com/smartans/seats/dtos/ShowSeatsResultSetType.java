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

import java.util.List;

/**
 * <pre>
 * <b>Description : </b>
 * ShowSeatsResultSetType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 1:31:50 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class ShowSeatsResultSetType {
    
    /**
     * seatsType.
     */
    private List<ShowSeatType> seatsType;
    
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
     * Get the 'seatsType' attribute value.
     * 
     * @return seatsType , null if not found.
     * </pre>
     */
    
    public List<ShowSeatType> getSeatsType() {
        return seatsType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'seatsType' attribute value.
     * 
     * @param seatsTypeParam , may be null.
     * </pre>
     */
    public void setSeatsType(List<ShowSeatType> seatsType) {
        this.seatsType = seatsType;
    }

}

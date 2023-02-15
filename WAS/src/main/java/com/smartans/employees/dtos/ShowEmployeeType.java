/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.employees.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import main.java.com.smartans.seats.dtos.SeatType;

/**
 * <pre>
 * <b>Description : </b>
 * ShowEmployeeType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 12:09:13 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class ShowEmployeeType extends EmployeeType implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -845442463573637429L;
    /**
     * errorMessage.
     */
    private List<String> errorMessage;
    /**
     * isSuccess.
     */
    private boolean isSuccess;

    /**
     * isSeatAssigned.
     */
    private boolean isSeatAssigned;

    /**
     * seatType.
     */
    private SeatType seatType;

    /**
     * lastModifiedDate.
     */
    private Date lastModifiedDate;

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'lastModifiedDate' attribute value.
     * 
     * @return lastModifiedDate , null if not found.
     * </pre>
     */

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'lastModifiedDate' attribute value.
     * 
     * @param lastModifiedDateParam , may be null.
     * </pre>
     */
    public void setLastModifiedDate(final Date lastModifiedDateParam) {
        this.lastModifiedDate = lastModifiedDateParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'isSeatAssigned' attribute value.
     * 
     * @return isSeatAssigned , null if not found.
     * </pre>
     */

    public boolean isSeatAssigned() {
        return isSeatAssigned;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'isSeatAssigned' attribute value.
     * 
     * @param isSeatAssignedParam , may be null.
     * </pre>
     */
    public void setSeatAssigned(final boolean isSeatAssignedParam) {
        this.isSeatAssigned = isSeatAssignedParam;
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
    public void setSeatType(final SeatType seatTypeParam) {
        this.seatType = seatTypeParam;
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
    public void setErrorMessage(final List<String> errorMessageParam) {
        this.errorMessage = errorMessageParam;
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
    public void setSuccess(final boolean isSuccessParam) {
        this.isSuccess = isSuccessParam;
    }

}

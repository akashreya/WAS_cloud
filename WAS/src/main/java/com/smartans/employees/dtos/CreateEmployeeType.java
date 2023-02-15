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

/**
 * <pre>
 * <b>Description : </b>
 * CreateEmployeeType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 12:05:50 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class CreateEmployeeType extends EmployeeType implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -4912280458965743468L;

    /**
     * lastUpdatedTime.
     */
    private Date lastUpdatedTime;

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CreateEmployeeType'.
     * 
     * </pre>
     */
    public CreateEmployeeType() {
        super();
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CreateEmployeeType'.
     * 
     * @param mid , not null
     * @param employeeName , not null
     * @param designation , not null
     * </pre>
     */
    public CreateEmployeeType(final String mid, final String employeeName, final String designation) {
        super(mid, employeeName, designation);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'lastUpdatedTime' attribute value.
     * 
     * @return lastUpdatedTime , null if not found.
     * </pre>
     */

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'lastUpdatedTime' attribute value.
     * 
     * @param lastUpdatedTimeParam , may be null.
     * </pre>
     */
    public void setLastUpdatedTime(final Date lastUpdatedTimeParam) {
        this.lastUpdatedTime = lastUpdatedTimeParam;
    }
}

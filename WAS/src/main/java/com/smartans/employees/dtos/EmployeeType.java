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

/**
 * <pre>
 * <b>Description : </b>
 * EmployeeType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 12:31:41 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class EmployeeType implements Serializable {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8474356323215299671L;
    /**
     * employeeName.
     */
    private String employeeName;
    /**
     * mID.
     */
    private String mID;

    /**
     * designation.
     */
    private String designation;

    /**
     * isManager.
     */
    private boolean isManager;

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'EmployeeType'.
     * 
     * </pre>
     */
    public EmployeeType() {
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'EmployeeType'.
     * 
     * @param mIDParam , not null
     * @param employeeNameParam , not null
     * @param designationParam , not null
     * </pre>
     */
    public EmployeeType(final String mIDParam, final String employeeNameParam, final String designationParam) {
        this.mID = mIDParam;
        this.employeeName = employeeNameParam;
        this.designation = designationParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'isManager' attribute value.
     * 
     * @return isManager , null if not found.
     * </pre>
     */

    public boolean getIsManager() {
        return isManager;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'isManager' attribute value.
     * 
     * @param isManagerParam , may be null.
     * </pre>
     */
    public void setManager(final boolean isManagerParam) {
        this.isManager = isManagerParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'designation' attribute value.
     * 
     * @return designation , null if not found.
     * </pre>
     */

    public String getDesignation() {
        return designation;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'designation' attribute value.
     * 
     * @param designationParam , may be null.
     * </pre>
     */
    public void setDesignation(final String designationParam) {
        this.designation = designationParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'employeeName' attribute value.
     * 
     * @return employeeName , null if not found.
     * </pre>
     */

    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'employeeName' attribute value.
     * 
     * @param employeeNameParam , may be null.
     * </pre>
     */
    public void setEmployeeName(final String employeeNameParam) {
        this.employeeName = employeeNameParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'mID' attribute value.
     * 
     * @return mID , null if not found.
     * </pre>
     */

    public String getmID() {
        return mID;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'mID' attribute value.
     * 
     * @param mIDParam , may be null.
     * </pre>
     */
    public void setmID(final String mIDParam) {
        this.mID = mIDParam;
    }
}

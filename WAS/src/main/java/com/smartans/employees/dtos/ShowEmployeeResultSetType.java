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

import java.util.List;

/**
 * <pre>
 * <b>Description : </b>
 * ShowEmployeeResultSetType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 12:24:23 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class ShowEmployeeResultSetType {
    
    /**
     * employeeTypes.
     */
    private List<ShowEmployeeType> employeesType;
    
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

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'employeeTypes' attribute value.
     * 
     * @return employeeTypes , null if not found.
     * </pre>
     */
    
    public List<ShowEmployeeType> getEmployeesType() {
        return employeesType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'employeeTypes' attribute value.
     * 
     * @param employeeTypesParam , may be null.
     * </pre>
     */
    public void setEmployeeTypes(final List<ShowEmployeeType> employeeTypesParam) {
        this.employeesType = employeeTypesParam;
    }

}

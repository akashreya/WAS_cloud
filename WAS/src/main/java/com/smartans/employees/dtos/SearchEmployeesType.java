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

/**
 * <pre>
 * <b>Description : </b>
 * SearchEmployeesType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-13 05:38:34 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class SearchEmployeesType {

    /**
     * isSeatedEmployee.
     */
    private boolean isSeatedEmployee;

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'isSeatedEmployee' attribute value.
     * 
     * @return isSeatedEmployee , null if not found.
     * </pre>
     */

    public boolean isSeatedEmployee() {
        return isSeatedEmployee;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'isSeatedEmployee' attribute value.
     * 
     * @param isSeatedEmployeeParam , may be null.
     * </pre>
     */
    public void setSeatedEmployee(final boolean isSeatedEmployeeParam) {
        this.isSeatedEmployee = isSeatedEmployeeParam;
    }

}

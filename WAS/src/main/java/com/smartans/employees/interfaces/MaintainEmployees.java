/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.employees.interfaces;

import main.java.com.smartans.employees.dtos.CreateEmployeeType;
import main.java.com.smartans.employees.dtos.DeleteEmployeeType;
import main.java.com.smartans.employees.dtos.SearchEmployeesType;
import main.java.com.smartans.employees.dtos.ShowEmployeeResultSetType;
import main.java.com.smartans.employees.dtos.ShowEmployeeType;

/**
 * <pre>
 * <b>Description : </b>
 * MaintainEmployees.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 12:37:05 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public interface MaintainEmployees {

    /**
     * <pre>
     * <b>Description : </b>
     * createEmployee.
     * 
     * @param createEmployeeType , may be null.
     * @return
     * </pre>
     */
    ShowEmployeeType createEmployee(final CreateEmployeeType createEmployeeType);
    
    /**
     * <pre>
     * <b>Description : </b>
     * deleteEmployee.
     * 
     * @param deleteEmployeeType , may be null.
     * @return
     * </pre>
     */
    ShowEmployeeType deleteEmployee(final DeleteEmployeeType deleteEmployeeType);
    /**
     * <pre>
     * <b>Description : </b>
     * searchEmployees.
     * 
     * @param searchEmployeesType , may be null.
     * @return showEmployeeResultSetType , null if not found
     * </pre>
     */
    ShowEmployeeResultSetType searchEmployees(final SearchEmployeesType searchEmployeesType);
}

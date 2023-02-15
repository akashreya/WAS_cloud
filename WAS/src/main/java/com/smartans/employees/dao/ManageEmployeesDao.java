/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.employees.dao;

import java.util.List;

import main.java.com.smartans.entity.Employee;
import main.java.com.smartans.exception.EmployeeException;

/**
 * <pre>
 * <b>Description : </b>
 * ManageEmployeesDao.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-13 09:51:43 AM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public interface ManageEmployeesDao {
    
    /**
     * <pre>
     * <b>Description : </b>
     * addEmployee.
     * 
     * @param employee
     * @return
     * @throws EmployeeException
     * </pre>
     */
    Employee addEmployee(Employee employee) throws EmployeeException;
    /**
     * <pre>
     * <b>Description : </b>
     * removeEmployee.
     * 
     * @param employeeId
     * @return
     * @throws EmployeeException
     * </pre>
     */
    Boolean removeEmployee(String employeeId)throws EmployeeException;
    /**
     * <pre>
     * <b>Description : </b>
     * searchEmployee.
     * 
     * @param namedQuery
     * @return
     * @throws EmployeeException
     * </pre>
     */
    List<Employee> searchEmployee(String namedQuery)throws EmployeeException;

}

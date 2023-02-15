/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.employees.dao.impl;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.smartans.common.constants.WASConstants;
import main.java.com.smartans.employees.dao.ManageEmployeesDao;
import main.java.com.smartans.entity.Employee;
import main.java.com.smartans.exception.CouldNotDeleteException;
import main.java.com.smartans.exception.CouldNotSearchException;
import main.java.com.smartans.exception.EmployeeException;
import main.java.com.smartans.exception.SeatException;
import main.java.com.smartans.seats.dao.impl.BaseDao;

import org.hibernate.exception.ConstraintViolationException;

/**
 * <pre>
 * <b>Description : </b>
 * ManageEmployeesDaoImpl.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-03 09:54:23 AM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class ManageEmployeesDaoImpl extends BaseDao implements ManageEmployeesDao{

    

    /**
     * <pre>
     * <b>Description : </b>
     * addEmployee.
     * 
     * @param employee
     * @return
     * </pre>
     * @throws SeatException 
     */
    @Override
    public Employee addEmployee(Employee employee) throws EmployeeException {
        Employee addedEmployee = null;
        try {
            addedEmployee = (Employee) create(employee);
        }
        catch (Exception ex) {
            ex = (Exception) ex.getCause();
            if (ex.getCause() instanceof ConstraintViolationException) {
                ex = (Exception) ex.getCause();
                if (ex.getCause() instanceof BatchUpdateException
                    && ex.getCause().getMessage().contains("Duplicate entry")
                    && ex.getCause().getMessage().contains("PRIMARY")) {
                    throw new EmployeeException(WASConstants.DUPLICATE_EMPLOYEE);
                }
            }
            throw new EmployeeException(WASConstants.COULD_NOT_SAVE_EMPLOYEE);
        }
        return addedEmployee;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * removeEmployee.
     * 
     * @param employee
     * @return
     * </pre>
     */
    @Override
    public Boolean removeEmployee(String employeeId) throws EmployeeException {
        boolean isEmployeedeleted = false;
        try {
            Employee employee =(Employee) retrieve(Employee.class, employeeId);
            delete(employee);
            isEmployeedeleted = true;
        }
        catch (CouldNotDeleteException ex) {
            throw new EmployeeException(WASConstants.COULD_NOT_DELETE_EMPLOYEE);
        }
        catch (Exception ex) {
            throw new EmployeeException(WASConstants.DB_DOWN);
        }
        return isEmployeedeleted;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * searchEmployee.
     * 
     * @param namedQuery
     * @return
     * </pre>
     */
    @Override
    public List<Employee> searchEmployee(String namedQuery) throws EmployeeException {
        List<Employee> employees = null;
        try {
            List<Object> objects = getObjects(namedQuery);
            if (objects != null && !objects.isEmpty()) {
                employees = new ArrayList<Employee>();
                for (Object object : objects) {
                    employees.add((Employee) object);
                }
            }
        }
        catch (CouldNotSearchException ex) {
            throw new EmployeeException(WASConstants.COULD_NOT_SEARCH_EMPLOYEES);
        }
        catch (Exception ex) {
            throw new EmployeeException(WASConstants.DB_DOWN);
        }

        return employees;
    }

}

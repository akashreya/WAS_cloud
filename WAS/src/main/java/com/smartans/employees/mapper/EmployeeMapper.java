/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.employees.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import main.java.com.smartans.common.constants.WASConstants;
import main.java.com.smartans.employees.dtos.CreateEmployeeType;
import main.java.com.smartans.employees.dtos.SearchEmployeesType;
import main.java.com.smartans.employees.dtos.ShowEmployeeResultSetType;
import main.java.com.smartans.employees.dtos.ShowEmployeeType;
import main.java.com.smartans.entity.Designation;
import main.java.com.smartans.entity.Employee;

/**
 * <pre>
 * <b>Description : </b>
 * EmployeeMapper.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-13 10:01:38 AM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class EmployeeMapper {

    /**
     * <pre>
     * <b>Description : </b>
     * mapRequestToEntity.
     * 
     * @param createEmployeeType , not null
     * @return employee , null if not found
     * </pre>
     */
    public Employee mapRequestToEntity(final CreateEmployeeType createEmployeeType) {
        Employee employee = new Employee();
        employee.setName(createEmployeeType.getEmployeeName());
        employee.setEmployeeId(createEmployeeType.getmID());
        Designation designation = new Designation();
        designation.setDesignationId(Integer.parseInt(createEmployeeType.getDesignation()));
        employee.setDesignation(designation);
        employee.setLastModifiedDate(new Date());
        return employee;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * mapEntityToResponse.
     * 
     * @param addedEmployee , not null
     * @return showEmployeeType , null if not found
     * </pre>
     */
    public ShowEmployeeType mapEntityToResponse(final Employee addedEmployee) {
        ShowEmployeeType showEmployeeType = new ShowEmployeeType();
        if (addedEmployee != null) {
            showEmployeeType.setEmployeeName(addedEmployee.getName());
            showEmployeeType.setmID(addedEmployee.getEmployeeId());
            showEmployeeType.setLastModifiedDate(addedEmployee.getLastModifiedDate());
            showEmployeeType.setSuccess(true);
            if (addedEmployee.getDesignation() != null) {
                if (addedEmployee.getDesignation().getDesignation() != null) {
                    showEmployeeType.setDesignation(addedEmployee.getDesignation().getDesignation());
                }
                if (addedEmployee.getDesignation().getIsManager() != null) {
                    showEmployeeType.setManager(addedEmployee.getDesignation().getIsManager());
                }
            }
        }
        return showEmployeeType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * createQuery.
     * 
     * @param searchEmployeesType , not null
     * @return string , null if not found
     * </pre>
     */
    public String createQuery(final SearchEmployeesType searchEmployeesType) {
        StringBuffer buffer = new StringBuffer();
        if (searchEmployeesType.isSeatedEmployee()) {
            buffer.append(WASConstants.QUERY_SEATED_EMPLOYEE);
        }
        else {
            buffer.append(WASConstants.QUERY_NOT_SEATED_EMPLOYEE);
        }
        return buffer.toString();
    }

    /**
     * <pre>
     * <b>Description : </b>
     * mapEmployeesToResponse.
     * 
     * @param employees , not null
     * @return employeeResultSetType , null if not found
     * </pre>
     */
    public ShowEmployeeResultSetType mapEmployeesToResponse(final List<Employee> employees) {
        ShowEmployeeResultSetType employeeResultSetType = new ShowEmployeeResultSetType();
        List<ShowEmployeeType> employeesType = new ArrayList<ShowEmployeeType>();
        for (Employee employee : employees) {
            employeesType.add(mapEntityToResponse(employee));
        }
        if (!employeesType.isEmpty()) {
            Collections.sort(employeesType, new Comparator<ShowEmployeeType>() {
                @Override
                public int compare(final ShowEmployeeType showEmployeeType1, final ShowEmployeeType showEmployeeType2) {
                    if (!showEmployeeType1.getLastModifiedDate().equals(showEmployeeType2.getLastModifiedDate())) {
                        return showEmployeeType1.getLastModifiedDate().compareTo(
                            (showEmployeeType2.getLastModifiedDate()));
                    }
                    return 0;
                }
            });
            employeeResultSetType.setEmployeeTypes(employeesType);
        }
        return employeeResultSetType;
    }

}

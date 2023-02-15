/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.employees.impl;

import java.util.ArrayList;
import java.util.List;

import main.java.com.smartans.common.constants.WASConstants;
import main.java.com.smartans.common.util.CommonHelper;
import main.java.com.smartans.common.util.ErrorCodeMessageUtil;
import main.java.com.smartans.designation.dtos.DesignationType;
import main.java.com.smartans.designation.dtos.ShowDesignationType;
import main.java.com.smartans.designation.interfaces.MaintainDesignations;
import main.java.com.smartans.employees.dao.ManageEmployeesDao;
import main.java.com.smartans.employees.dtos.CreateEmployeeType;
import main.java.com.smartans.employees.dtos.DeleteEmployeeType;
import main.java.com.smartans.employees.dtos.SearchEmployeesType;
import main.java.com.smartans.employees.dtos.ShowEmployeeResultSetType;
import main.java.com.smartans.employees.dtos.ShowEmployeeType;
import main.java.com.smartans.employees.interfaces.MaintainEmployees;
import main.java.com.smartans.employees.mapper.EmployeeMapper;
import main.java.com.smartans.entity.Employee;
import main.java.com.smartans.exception.EmployeeException;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * <b>Description : </b>
 * ManageEmployeesImpl.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-14 09:58:14 AM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class MaintainEmployeesImpl implements MaintainEmployees {

    /**
     * manageEmployeesDaoImpl.
     */
    @Autowired
    private ManageEmployeesDao manageEmployeesDaoImpl;

    /**
     * employeeMapper.
     */
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * maintainDesignation.
     */
    @Autowired
    private MaintainDesignations maintainDesignation;

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'employeeMapper' attribute value.
     * 
     * @param employeeMapperParam , may be null.
     * </pre>
     */
    public void setEmployeeMapper(final EmployeeMapper employeeMapperParam) {
        this.employeeMapper = employeeMapperParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'manageEmployeesDaoImpl' attribute value.
     * 
     * @param manageEmployeesDaoImplParam , may be null.
     * </pre>
     */
    public void setManageEmployeesDaoImpl(final ManageEmployeesDao manageEmployeesDaoImplParam) {
        this.manageEmployeesDaoImpl = manageEmployeesDaoImplParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'maintainDesignation' attribute value.
     * 
     * @param maintainDesignationParam , may be null.
     * </pre>
     */
    public void setMaintainDesignation(final MaintainDesignations maintainDesignationParam) {
        this.maintainDesignation = maintainDesignationParam;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * createEmployee.
     * 
     * @param createEmployeeType , not null
     * @return showEmployeeType , null if not found
     * </pre>
     */
    @Override
    public ShowEmployeeType createEmployee(final CreateEmployeeType createEmployeeType) {
        ShowEmployeeType showEmployeeType = new ShowEmployeeType();
        List<String> errorMessages = new ArrayList<String>();
        try {
            List<Employee> managers = manageEmployeesDaoImpl.searchEmployee(WASConstants.QUERY_MANAGERS);
            ShowDesignationType searchDesignation = maintainDesignation.searchDesignation();
            boolean canProceed = true;
            if (searchDesignation.getDesignations() != null && !searchDesignation.getDesignations().isEmpty()) {
                for (DesignationType designationType : searchDesignation.getDesignations()) {
                    if (managers != null
                        && managers.size() == WASConstants.FIVE
                        && createEmployeeType.getDesignation().equalsIgnoreCase(
                            String.valueOf(designationType.getDesignationId())) && designationType.getIsManager()) {
                        canProceed = false;
                    }
                }
            }
            if (canProceed) {
                Employee employee = employeeMapper.mapRequestToEntity(createEmployeeType);
                employee = manageEmployeesDaoImpl.addEmployee(employee);
                showEmployeeType = employeeMapper.mapEntityToResponse(employee);
            }
            else {
                showEmployeeType.setSuccess(false);
                CommonHelper
                    .addToInvalidErrMsgDescList(
                        ErrorCodeMessageUtil.getMessageForErrorCode(WASConstants.ALREADY_5_MANAGERS_PRESENT),
                        errorMessages);
                showEmployeeType.setErrorMessage(errorMessages);
            }
        }
        catch (EmployeeException ex) {
            CommonHelper.addToInvalidErrMsgDescList(ErrorCodeMessageUtil.getMessageForErrorCode(ex.getMessage()),
                errorMessages);
            showEmployeeType.setSuccess(false);
            showEmployeeType.setErrorMessage(errorMessages);
        }
        return showEmployeeType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * deleteEmployee.
     * 
     * @param deleteEmployeeType , not null
     * @return showEmployeeType , null if not found
     * </pre>
     */
    @Override
    public ShowEmployeeType deleteEmployee(final DeleteEmployeeType deleteEmployeeType) {
        ShowEmployeeType showEmployeeType = new ShowEmployeeType();
        try {
            Boolean isSuccess = manageEmployeesDaoImpl.removeEmployee(deleteEmployeeType.getmID());
            showEmployeeType.setSuccess(isSuccess);
        }
        catch (EmployeeException ex) {
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add(ex.getMessage());
            showEmployeeType.setSuccess(false);
            showEmployeeType.setErrorMessage(errorMessages);
        }
        return showEmployeeType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * searchEmployees.
     * 
     * @param searchEmployeesType , not null
     * @return showEmployeeResultSetType , null if not found
     * </pre>
     */
    @Override
    public ShowEmployeeResultSetType searchEmployees(final SearchEmployeesType searchEmployeesType) {
        ShowEmployeeResultSetType showEmployeeResultSetType = new ShowEmployeeResultSetType();
        if (searchEmployeesType != null) {
            try {
                String namedQuery = employeeMapper.createQuery(searchEmployeesType);
                List<Employee> employees = manageEmployeesDaoImpl.searchEmployee(namedQuery);
                if (employees != null && !employees.isEmpty()) {
                    showEmployeeResultSetType = employeeMapper.mapEmployeesToResponse(employees);
                    showEmployeeResultSetType.setSuccess(true);
                }
            }
            catch (EmployeeException ex) {
                List<String> errorMessages = new ArrayList<String>();
                errorMessages.add(ex.getMessage());
                showEmployeeResultSetType.setSuccess(false);
                showEmployeeResultSetType.setErrorMessage(errorMessages);
            }
        }
        return showEmployeeResultSetType;
    }

}

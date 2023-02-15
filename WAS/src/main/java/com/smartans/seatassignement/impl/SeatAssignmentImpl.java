/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seatassignement.impl;

import java.util.ArrayList;
import java.util.List;

import main.java.com.smartans.common.constants.WASConstants;
import main.java.com.smartans.common.util.CommonHelper;
import main.java.com.smartans.common.util.ErrorCodeMessageUtil;
import main.java.com.smartans.employees.dtos.DeleteEmployeeType;
import main.java.com.smartans.employees.dtos.ShowEmployeeType;
import main.java.com.smartans.employees.interfaces.MaintainEmployees;
import main.java.com.smartans.entity.Employee;
import main.java.com.smartans.entity.Seat;
import main.java.com.smartans.exception.SeatAssignmentException;
import main.java.com.smartans.seatassignement.dao.SeatAssignmentDao;
import main.java.com.smartans.seatassignement.dtos.AssignSeatType;
import main.java.com.smartans.seatassignement.dtos.ConfirmSeatType;
import main.java.com.smartans.seatassignement.dtos.ConfirmSwapSeatType;
import main.java.com.smartans.seatassignement.dtos.ConfirmUnassignSeatType;
import main.java.com.smartans.seatassignement.dtos.IdentifierType;
import main.java.com.smartans.seatassignement.dtos.ReassignSeatType;
import main.java.com.smartans.seatassignement.dtos.SwapSeatType;
import main.java.com.smartans.seatassignement.dtos.UnassignSeatType;
import main.java.com.smartans.seatassignement.interfaces.SeatAssignment;
import main.java.com.smartans.seatassignement.mapper.SeatAssignmentMapper;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * <b>Description : </b>
 * SeatAssignmentImpl.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 7:34:03 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class SeatAssignmentImpl implements SeatAssignment {
    
    /**
     * seatAssignmentDaoImpl.
     */
    @Autowired
    private SeatAssignmentDao seatAssignmentDaoImpl;
    /**
     * assignmentMapper.
     */
    @Autowired
    private SeatAssignmentMapper assignmentMapper;
    
    /**
     * maintainEmployees.
     */
    private MaintainEmployees maintainEmployees;
    
    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'maintainEmployees' attribute value.
     * 
     * @param maintainEmployeesParam , may be null.
     * </pre>
     */
    public void setMaintainEmployees(MaintainEmployees maintainEmployees) {
        this.maintainEmployees = maintainEmployees;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'assignmentMapper' attribute value.
     * 
     * @param assignmentMapperParam , may be null.
     * </pre>
     */
    public void setAssignmentMapper(SeatAssignmentMapper assignmentMapper) {
        this.assignmentMapper = assignmentMapper;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'seatAssignmentDaoImpl' attribute value.
     * 
     * @param seatAssignmentDaoImplParam , may be null.
     * </pre>
     */
    public void setSeatAssignmentDaoImpl(SeatAssignmentDao seatAssignmentDaoImpl) {
        this.seatAssignmentDaoImpl = seatAssignmentDaoImpl;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * assignSeat.
     * 
     * @param assignSeatType
     * @return
     * </pre>
     */
    @Override
    public ConfirmSeatType assignSeat(AssignSeatType assignSeatType) {
        ConfirmSeatType confirmSeatType = new ConfirmSeatType();
        List<String> errorMessages = new ArrayList<String>();
        try {
            Seat retrievedSeat = (Seat) seatAssignmentDaoImpl.retrieveObject(Seat.class, assignSeatType.getSeatType().getSeatName());
            Employee employee = (Employee) seatAssignmentDaoImpl.retrieveObject(Employee.class, assignSeatType.getEmployeeType().getmID());
            if (retrievedSeat != null && employee != null) {
                if (retrievedSeat.getIsManagerSeat() && employee.getDesignation().getIsManager()
                    || (!retrievedSeat.getIsManagerSeat() && !employee.getDesignation().getIsManager())) {
                    confirmSeatType = assignNewSeat(retrievedSeat, employee);
                }
                else {
                    confirmSeatType.setSuccess(false);
                    if (retrievedSeat.getIsManagerSeat() && !employee.getDesignation().getIsManager()) {
                        CommonHelper.addToInvalidErrMsgDescList(ErrorCodeMessageUtil.getMessageForErrorCode(
                            WASConstants.MANAGER_CANNOT_BE_ASSIGNED_TO_EMPLOYEE, retrievedSeat.getSeatNumber()),
                            errorMessages);
                    }
                    else if (!retrievedSeat.getIsManagerSeat() && employee.getDesignation().getIsManager()) {
                        CommonHelper.addToInvalidErrMsgDescList(ErrorCodeMessageUtil.getMessageForErrorCode(
                            WASConstants.NORMAL_SEAT_CANNOT_BE_ASSIGNED_TO_MANAGER, retrievedSeat.getSeatNumber()),
                            errorMessages);
                    }
                    confirmSeatType.setErrorMessage(errorMessages);
                }
            }
            
        }
        catch (SeatAssignmentException ex) {
            errorMessages.add(ex.getMessage());
            confirmSeatType.setSuccess(false);
            confirmSeatType.setErrorMessage(errorMessages);
        }
        return confirmSeatType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * assignNewSeat.
     * 
     * @param retrievedSeat
     * @param employee
     * @return
     * @throws SeatAssignmentException
     * </pre>
     */
    private ConfirmSeatType assignNewSeat(Seat retrievedSeat, Employee employee) throws SeatAssignmentException {
        ConfirmSeatType confirmSeatType;
        retrievedSeat.setEmployee(employee);
        Seat assignSeat = seatAssignmentDaoImpl.assignSeat(retrievedSeat);    
        confirmSeatType = assignmentMapper.mapEntityToResponse(assignSeat);
        return confirmSeatType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * unAssignSeat.
     * 
     * @param unassignSeatType
     * @return
     * </pre>
     */
    @Override
    public ConfirmUnassignSeatType unAssignSeat(UnassignSeatType unassignSeatType) {
        ConfirmUnassignSeatType confirmUnassignSeatType = new ConfirmUnassignSeatType();
        if (unassignSeatType != null) {
            List<ConfirmSeatType> confirmSeatType = new ArrayList<ConfirmSeatType>();
            try {
                List<IdentifierType> employeeIds = unassignSeatType.getEmployeeIds();
                for (IdentifierType identifierType : employeeIds) {
                    Seat seat = getSeatEntity(identifierType.getEmployeeId());
                    Seat assignSeat = unassignSeat(seat);
                    if (assignSeat != null) {
                        confirmSeatType.add(assignmentMapper.mapEntityToResponse(assignSeat));
                        DeleteEmployeeType deleteEmployeeType = new DeleteEmployeeType();
                        deleteEmployeeType.setmID(identifierType.getEmployeeId());
                        ShowEmployeeType showEmployeeType = maintainEmployees.deleteEmployee(deleteEmployeeType);
                        if (!showEmployeeType.isSuccess()) {
                            List<String> errorMessages = new ArrayList<String>();
                            errorMessages.addAll(showEmployeeType.getErrorMessage());
                            confirmUnassignSeatType.setSuccess(false);
                            confirmUnassignSeatType.setErrorMessage(errorMessages);
                        }
                    }
                }
                if (!confirmSeatType.isEmpty()) {
                    confirmUnassignSeatType.setSeats(confirmSeatType);
                    confirmUnassignSeatType.setSuccess(true);
                }
            }
            catch (SeatAssignmentException ex) {
                List<String> errorMessages = new ArrayList<String>();
                errorMessages.add(ex.getMessage());
                confirmUnassignSeatType.setSuccess(false);
                confirmUnassignSeatType.setErrorMessage(errorMessages);
            }
        }
        return confirmUnassignSeatType;
    }
    
	/**
	 * @param employeeId
	 * @return
	 * @throws SeatAssignmentException
	 */
	private Seat unassignSeat(Seat seat) throws SeatAssignmentException {
		if (seat != null) {
		    seat.setEmployee(null);
		    return seatAssignmentDaoImpl.assignSeat(seat);
		}
		else {
		    return null;
		}
	}

    /**
     * <pre>
     * <b>Description : </b>
     * getSeatEntity.
     * 
     * @param employeeId
     * @return
     * @throws SeatAssignmentException
     * </pre>
     */
    private Seat getSeatEntity(String employeeId) throws SeatAssignmentException {
        List<Object> seats = seatAssignmentDaoImpl.search(WASConstants.QUERY_SEAT_WHEN_EMPLOYEE_ID + employeeId
            + WASConstants.SINGLE_QUOTE);
        if (seats != null && !seats.isEmpty()) {
            return (Seat) seats.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * <pre>
     * <b>Description : </b>
     * swapSeat.
     * 
     * @param swapSeatType
     * @return
     * </pre>
     */
    @Override
    public ConfirmSwapSeatType swapSeat(SwapSeatType swapSeatType) {
        ConfirmSwapSeatType confirmSwapSeatType = new ConfirmSwapSeatType();
        List<String> errorMessages = new ArrayList<String>();
        try {
            Seat firstSeat = getSeatEntity(swapSeatType.getEmployee().getEmployeeType().getmID());
            Employee firstEmployee = firstSeat.getEmployee();
            Seat secondSeat = getSeatEntity(swapSeatType.getSwappedEmployee().getEmployeeType().getmID());
            Employee secondEmployee = secondSeat.getEmployee();

            if ((firstEmployee.getDesignation().getIsManager() && secondEmployee.getDesignation().getIsManager())
                || (!firstEmployee.getDesignation().getIsManager() && !secondEmployee.getDesignation().getIsManager())) {
                //Unassign both seats
                firstSeat.setEmployee(null);
                secondSeat.setEmployee(null);
                Seat firstUnassignedSeat = seatAssignmentDaoImpl.assignSeat(firstSeat);
                Seat secondUnassignedSeat = seatAssignmentDaoImpl.assignSeat(secondSeat);
                
                // swap Seats
                firstUnassignedSeat.setEmployee(secondEmployee);
                secondUnassignedSeat.setEmployee(firstEmployee);
                confirmSwapSeatType.setFirstEmployee(assignmentMapper.mapEntityToResponse(seatAssignmentDaoImpl
                    .assignSeat(firstUnassignedSeat)));
                confirmSwapSeatType.setSwappedEmployee(assignmentMapper.mapEntityToResponse(seatAssignmentDaoImpl
                    .assignSeat(secondUnassignedSeat)));
                if (confirmSwapSeatType.getFirstEmployee() != null && confirmSwapSeatType.getSwappedEmployee() != null) {
                    confirmSwapSeatType.setSuccess(true);
                }
            }
            else {
                confirmSwapSeatType.setSuccess(false);
                if (firstEmployee.getDesignation().getIsManager() && !secondEmployee.getDesignation().getIsManager()) {
                    CommonHelper.addToInvalidErrMsgDescList(ErrorCodeMessageUtil.getMessageForErrorCode(
                        WASConstants.MANAGER_CANNOT_SWAPPED_WITH_EMPLOYEE, firstEmployee.getName(), secondEmployee.getName()),
                        errorMessages);
                }
                else if (!firstEmployee.getDesignation().getIsManager() && secondEmployee.getDesignation().getIsManager()) {
                    CommonHelper.addToInvalidErrMsgDescList(ErrorCodeMessageUtil.getMessageForErrorCode(
                        WASConstants.EMPLOYEE_CANNOT_SWAPPED_WITH_MANAGER, firstEmployee.getName(), secondEmployee.getName()),
                        errorMessages);
                }
                confirmSwapSeatType.setErrorMessage(errorMessages);
            }
            
        }
        catch (SeatAssignmentException ex) {
            
            errorMessages.add(ex.getMessage());
            confirmSwapSeatType.setSuccess(false);
            confirmSwapSeatType.setErrorMessage(errorMessages);
        }
        return confirmSwapSeatType;
    }

	/**
	 * <pre>
	 * <b>Description : </b>
	 * reassignSeat.
	 * 
	 * @param reassignSeatType
	 * @return
	 * </pre>
	 */
	@Override
	public ConfirmSeatType reassignSeat(ReassignSeatType reassignSeatType) {
		ConfirmSeatType confirmSeatType = new ConfirmSeatType();
		List<String> errorMessages = new ArrayList<String>();
		try {
		    Seat seat = getSeatEntity(reassignSeatType.getEmployeeType().getmID());
		    Seat toReassignSeat = (Seat) seatAssignmentDaoImpl.retrieveObject(Seat.class, reassignSeatType.getSeatType().getSeatName());
		    if (seat != null && toReassignSeat != null) {
		        if ((seat.getIsManagerSeat() && toReassignSeat.getIsManagerSeat())
		            || (!seat.getIsManagerSeat() && !toReassignSeat.getIsManagerSeat())) {
		            unassignSeat(seat);
		            AssignSeatType assignSeatType = new AssignSeatType();
		            assignSeatType.setEmployeeType(reassignSeatType.getEmployeeType());
		            assignSeatType.setSeatType(reassignSeatType.getSeatType());
		            confirmSeatType = assignSeat(assignSeatType );
		        }
		        else {
		            confirmSeatType.setSuccess(false);
		            if (seat.getIsManagerSeat() && !toReassignSeat.getIsManagerSeat()) {
		                CommonHelper.addToInvalidErrMsgDescList(ErrorCodeMessageUtil.getMessageForErrorCode(
	                        WASConstants.MANAGER_CANNOT_BE_REASSIGNED_TO_NORMAL_SEAT, reassignSeatType.getEmployeeType().getmID(), toReassignSeat.getSeatNumber()),
	                        errorMessages);
                    }
                    else if (!seat.getIsManagerSeat() && toReassignSeat.getIsManagerSeat()) {
                        CommonHelper.addToInvalidErrMsgDescList(ErrorCodeMessageUtil.getMessageForErrorCode(
                            WASConstants.EMPLOYEE_CANNOT_BE_REASSIGNED_TO_MANAGER_SEAT, reassignSeatType.getEmployeeType().getmID(), toReassignSeat.getSeatNumber()),
                            errorMessages);
                    }
                    confirmSeatType.setErrorMessage(errorMessages);
		        }
		    }
		} catch (SeatAssignmentException ex) {
			
			errorMessages.add(ex.getMessage());
			confirmSeatType.setSuccess(false);
			confirmSeatType.setErrorMessage(errorMessages);
		}
		return confirmSeatType;
	}
}

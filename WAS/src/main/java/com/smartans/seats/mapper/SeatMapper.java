/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seats.mapper;

import java.util.ArrayList;
import java.util.List;

import main.java.com.smartans.employees.dtos.EmployeeType;
import main.java.com.smartans.entity.Employee;
import main.java.com.smartans.entity.Seat;
import main.java.com.smartans.seats.dtos.CreateSeatType;
import main.java.com.smartans.seats.dtos.ShowSeatType;
import main.java.com.smartans.seats.dtos.ShowSeatsResultSetType;

/**
 * <pre>
 * <b>Description : </b>
 * SeatMapperToSeatEntity.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 4:40:57 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class SeatMapper {

    /**
     * <pre>
     * <b>Description : </b>
     * mapRequestToEntity.
     * 
     * @param createSeatDTO
     * @return
     * </pre>
     */
    public Seat mapRequestToEntity(CreateSeatType createSeatType) {
        Seat seat = null;
        if (createSeatType != null) {
            seat = new Seat();
            seat.setSeatNumber(createSeatType.getSeatName().toUpperCase());
            seat.setExtensionNumber(createSeatType.getExtensionNumber());
            seat.setIsManagerSeat(createSeatType.getIsManagerSeat());
        }
        return seat;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * mapEntityToResponse.
     * 
     * @param confirmSeat
     * @return
     * </pre>
     */
    public ShowSeatType mapEntityToResponse(Seat confirmSeat) {
        ShowSeatType showSeatType = null;
        if (confirmSeat != null) {
            showSeatType = new ShowSeatType();
            setSeatResponse(confirmSeat, showSeatType);
        }
        return showSeatType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * setSeatResponse.
     * 
     * @param confirmSeat
     * @param showSeatType
     * </pre>
     */
    private void setSeatResponse(Seat confirmSeat, ShowSeatType showSeatType) {
        showSeatType.setExtensionNumber(String.valueOf(confirmSeat.getExtensionNumber()));
        showSeatType.setSeatName(confirmSeat.getSeatNumber());
        showSeatType.setManagerSeat(confirmSeat.getIsManagerSeat());
        showSeatType.setSuccess(true);
        showSeatType.setErrorMessage(null);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * mapSeatsToResponse.
     * 
     * @param seats
     * </pre>
     * @return 
     */
    public ShowSeatsResultSetType mapSeatsToResponse(List<Seat> seats) {
        ShowSeatsResultSetType seatsResultSetType = new ShowSeatsResultSetType();
        seatsResultSetType.setSuccess(true);
        List<ShowSeatType> seatsType = new ArrayList<ShowSeatType>();
        for (Seat seat : seats) {
            ShowSeatType showSeatType = new ShowSeatType();
            setSeatResponse(seat, showSeatType);
            if (seat.getEmployee() != null) {
                Employee employee = seat.getEmployee();
                EmployeeType employeeType = new EmployeeType();
                employeeType.setManager((boolean) employee.getDesignation().getIsManager());
                employeeType.setDesignation(employee.getDesignation().getDesignation());
                employeeType.setEmployeeName(employee.getName());
                employeeType.setmID(employee.getEmployeeId());
                showSeatType.setEmployeeType(employeeType);
            }
            seatsType.add(showSeatType);
        }
        seatsResultSetType.setSeatsType(seatsType);
        return seatsResultSetType;
    }
            
        

}

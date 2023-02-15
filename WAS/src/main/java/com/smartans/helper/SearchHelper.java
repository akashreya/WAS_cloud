/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.helper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import main.java.com.smartans.employees.dtos.SearchEmployeesType;
import main.java.com.smartans.employees.dtos.ShowEmployeeResultSetType;
import main.java.com.smartans.employees.dtos.ShowEmployeeType;
import main.java.com.smartans.employees.interfaces.MaintainEmployees;
import main.java.com.smartans.seats.dtos.ShowSeatType;
import main.java.com.smartans.seats.dtos.ShowSeatsResultSetType;
import main.java.com.smartans.seats.interfaces.MaintainSeats;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * <pre>
 * <b>Description : </b>
 * SearchHelper.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 8:30:17 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class SearchHelper {
    
    
    
    @Autowired
    private MaintainSeats maintainSeats;
    /**
     * maintainEmployees.
     */
    @Autowired
    private MaintainEmployees maintainEmployees;
    
    
    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'maintainSeats' attribute value.
     * 
     * @param maintainSeatsParam , may be null.
     * </pre>
     */
    public void setMaintainSeats(MaintainSeats maintainSeats) {
        this.maintainSeats = maintainSeats;
    }

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
     * Get the 'maintainSeats' attribute value.
     * 
     * @return maintainSeats , null if not found.
     * </pre>
     */
    
    public MaintainSeats getMaintainSeats() {
        return maintainSeats;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'maintainEmployees' attribute value.
     * 
     * @return maintainEmployees , null if not found.
     * </pre>
     */
    
    public MaintainEmployees getMaintainEmployees() {
        return maintainEmployees;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * searchSeats.
     * 
     * </pre>
     * @param maintainSeats 
     * @param session 
     */
    public void searchSeats(HttpSession session) {
        
        //Search seats
        ShowSeatsResultSetType searchSeats = maintainSeats.searchSeats();
        int availableCount = 0;
        int occupiedCount = 0;
        int managerSeatCount = 0;
        Map<String, ShowSeatType> seatMap = new LinkedHashMap<String, ShowSeatType>();
        if (searchSeats != null && searchSeats.isSuccess()) {
            List<ShowSeatType> seatsType = searchSeats.getSeatsType();
            for (ShowSeatType showSeatType : seatsType) {
                seatMap.put(showSeatType.getSeatName(), showSeatType);
                if (showSeatType.getEmployeeType() != null) {
                    occupiedCount++;
                }
                else {
                    availableCount++;
                }
                if (showSeatType.getIsManagerSeat()) {
                    managerSeatCount++;
                }
                
            }
        }
        session.setAttribute("managerSeatCount", managerSeatCount);
        session.setAttribute("availableCount", availableCount);
        session.setAttribute("occupiedCount", occupiedCount);
        session.setAttribute("seatMap", seatMap);
        session.setAttribute("seatMapCount", seatMap.size());
    }

    /**
     * <pre>
     * <b>Description : </b>
     * searchEmployees.
     * 
     * </pre>
     * @param maintainEmployees 
     * @param session 
     */
    public void searchEmployees(HttpSession session) {
        
        int managerCount=0;
     // Search employees who have not got a seat.
        Map<String,ShowEmployeeType> employeesMap = new LinkedHashMap<String, ShowEmployeeType>();
        SearchEmployeesType searchEmployeesType = new SearchEmployeesType();
        searchEmployeesType.setSeatedEmployee(false);
        session.setAttribute("employeesTobeSeated", 0);
        ShowEmployeeResultSetType showEmployeesType = maintainEmployees.searchEmployees(searchEmployeesType);
        if (showEmployeesType != null && showEmployeesType.isSuccess()) {
            List<ShowEmployeeType> employeesType = showEmployeesType.getEmployeesType();
            if (employeesType != null && !employeesType.isEmpty()) {
                if (employeesType.size() < 10) {
                    for (ShowEmployeeType showEmployeeType : employeesType) {
                        employeesMap.put(showEmployeeType.getmID(), showEmployeeType);
                        if (showEmployeeType.getIsManager()) {
                            managerCount++;
                        }
                    }
                }
                else {
                    for (int i = 0; i < 10; i++) {
                        ShowEmployeeType showEmployeeType = employeesType.get(i);
                        employeesMap.put(employeesType.get(i).getmID(), showEmployeeType);
                        if (showEmployeeType.getIsManager()) {
                            managerCount++;
                        }
                    }
                }
                session.setAttribute("employeesTobeSeated", employeesType.size());
            }
        }
        session.setAttribute("managerCount", managerCount);
        session.setAttribute("Employees", employeesMap);
        
    }

}

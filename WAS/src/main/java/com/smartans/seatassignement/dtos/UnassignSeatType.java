/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seatassignement.dtos;

import java.util.List;

/**
 * <pre>
 * <b>Description : </b>
 * UnassignSeatType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 1:45:51 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class UnassignSeatType {
    
    /**
     * seatAssignmentTypes.
     */
    private List<IdentifierType> employeeIds;

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'employeeIds' attribute value.
     * 
     * @return employeeIds , null if not found.
     * </pre>
     */
    
    public List<IdentifierType> getEmployeeIds() {
        return employeeIds;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'employeeIds' attribute value.
     * 
     * @param employeeIdsParam , may be null.
     * </pre>
     */
    public void setEmployeeIds(List<IdentifierType> employeeIds) {
        this.employeeIds = employeeIds;
    }

}

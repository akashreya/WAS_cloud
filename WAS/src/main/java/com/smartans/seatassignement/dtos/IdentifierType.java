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

/**
 * <pre>
 * <b>Description : </b>
 * IdentifierType.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 2:01:17 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class IdentifierType {
    
    /**
     * employeeId.
     */
    private String employeeId;

    /**
     * <pre>
     * <b>Description : </b>
     * Get the 'seatId' attribute value.
     * 
     * @return seatId , null if not found.
     * </pre>
     */
    
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'seatId' attribute value.
     * 
     * @param seatIdParam , may be null.
     * </pre>
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

}

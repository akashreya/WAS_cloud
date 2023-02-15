/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seatassignement.mapper;

import main.java.com.smartans.entity.Seat;
import main.java.com.smartans.seatassignement.dtos.ConfirmSeatType;
import main.java.com.smartans.seatassignement.dtos.SeatAssignmentType;
import main.java.com.smartans.seats.dtos.SeatType;

/**
 * <pre>
 * <b>Description : </b>
 * SeatAssignmentMapper.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 7:52:37 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class SeatAssignmentMapper {

    /**
     * <pre>
     * <b>Description : </b>
     * mapEntityToResponse.
     * 
     * @param assignSeat
     * @return
     * </pre>
     */
    public ConfirmSeatType mapEntityToResponse(Seat assignSeat) {
        ConfirmSeatType confirmSeatType = null;
        if (assignSeat != null) {
            confirmSeatType = new ConfirmSeatType();
            confirmSeatType.setSuccess(true);
            SeatAssignmentType seatAssignmentType = new SeatAssignmentType();
            SeatType seatType = new SeatType();
            seatType.setSeatName(assignSeat.getSeatNumber());
            seatType.setManagerSeat(assignSeat.getIsManagerSeat());
            seatType.setExtensionNumber(String.valueOf(assignSeat.getExtensionNumber()));
            seatAssignmentType.setSeatType(seatType );
            confirmSeatType.setSeatAssignmentType(seatAssignmentType );
        }
        return confirmSeatType;
    }

}

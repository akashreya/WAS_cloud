/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seatassignement.interfaces;

import main.java.com.smartans.seatassignement.dtos.AssignSeatType;
import main.java.com.smartans.seatassignement.dtos.ConfirmSeatType;
import main.java.com.smartans.seatassignement.dtos.ConfirmSwapSeatType;
import main.java.com.smartans.seatassignement.dtos.ConfirmUnassignSeatType;
import main.java.com.smartans.seatassignement.dtos.ReassignSeatType;
import main.java.com.smartans.seatassignement.dtos.SwapSeatType;
import main.java.com.smartans.seatassignement.dtos.UnassignSeatType;

/**
 * <pre>
 * <b>Description : </b>
 * SeatAssignment.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-02 1:56:02 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public interface SeatAssignment {
    
    /**
     * <pre>
     * <b>Description : </b>
     * assignSeat.
     * 
     * @param assignSeatType
     * @return
     * </pre>
     */
    ConfirmSeatType assignSeat(AssignSeatType assignSeatType);
    /**
     * <pre>
     * <b>Description : </b>
     * unAssignSeat.
     * 
     * @param unassignSeatType
     * @return
     * </pre>
     */
    ConfirmUnassignSeatType unAssignSeat(UnassignSeatType unassignSeatType);
    
    /**
     * <pre>
     * <b>Description : </b>
     * swapSeat.
     * 
     * @param swapSeatType
     * @return
     * </pre>
     */
    ConfirmSwapSeatType swapSeat(SwapSeatType swapSeatType) ;
    /**
     * <pre>
     * <b>Description : </b>
     * reassignSeat.
     * 
     * @param reassignSeatType
     * @return
     * </pre>
     */
	ConfirmSeatType reassignSeat(ReassignSeatType reassignSeatType);
}

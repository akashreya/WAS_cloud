/*
 * This code contains copyright information which is the proprietary property
 * of SMARTANS. No part of this code may be reproduced, 
 * stored or transmitted in any form without the prior
 * written permission of SMARTANS.
 *
 * Copyright (C) SMARTANS 2013-2014.
 * All rights reserved.
 */
package main.java.com.smartans.seats.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.java.com.smartans.common.constants.WASConstants;
import main.java.com.smartans.common.util.CommonHelper;
import main.java.com.smartans.common.util.ErrorCodeMessageUtil;
import main.java.com.smartans.entity.Seat;
import main.java.com.smartans.exception.SeatException;
import main.java.com.smartans.seats.dao.ManageSeatsDao;
import main.java.com.smartans.seats.dtos.CreateSeatType;
import main.java.com.smartans.seats.dtos.DeleteSeatType;
import main.java.com.smartans.seats.dtos.ShowSeatType;
import main.java.com.smartans.seats.dtos.ShowSeatsResultSetType;
import main.java.com.smartans.seats.dtos.UpdateSeatType;
import main.java.com.smartans.seats.interfaces.MaintainSeats;
import main.java.com.smartans.seats.mapper.SeatMapper;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * <b>Description : </b>
 * MaintainSeatsImpl.java.
 * 
 * @version $Revision: 1 $ $Date: YYYY-MM-DD 3:27:55 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public class MaintainSeatsImpl implements MaintainSeats {
    
    /**
     * manageSeatsDaoImpl.
     */
    @Autowired
    private ManageSeatsDao manageSeatsDaoImpl;
    
    /**
     * seatMapper.
     */
    @Autowired
    private SeatMapper seatMapper;

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'seatMapper' attribute value.
     * 
     * @param seatMapperParam , may be null.
     * </pre>
     */
    public void setSeatMapper(SeatMapper seatMapper) {
        this.seatMapper = seatMapper;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Set the 'manageSeatsDaoImpl' attribute value.
     * 
     * @param manageSeatsDaoImplParam , may be null.
     * </pre>
     */
    public void setManageSeatsDaoImpl(ManageSeatsDao manageSeatsDaoImpl) {
        this.manageSeatsDaoImpl = manageSeatsDaoImpl;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * createSeats.
     * 
     * @param createSeatType
     * @return
     * </pre>
     */
    @Override
    public ShowSeatType createSeats(CreateSeatType createSeatType) {
        ShowSeatType showSeatType  = new ShowSeatType();
        
        try {
            Seat seat =  seatMapper.mapRequestToEntity(createSeatType);
            Seat confirmSeat = manageSeatsDaoImpl.addSeat(seat); 
            showSeatType = seatMapper.mapEntityToResponse(confirmSeat);
        }
        catch (SeatException ex) {
            List<String> errorMessages = new ArrayList<String>();
            CommonHelper.addToInvalidErrMsgDescList(ErrorCodeMessageUtil.getMessageForErrorCode(ex.getMessage()),
                errorMessages);
            showSeatType.setSuccess(false);
            showSeatType.setErrorMessage(errorMessages);
        }
        return showSeatType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * deleteSeat.
     * 
     * @param deleteSeatType
     * @return
     * </pre>
     */
    @Override
    public ShowSeatType deleteSeat(DeleteSeatType deleteSeatType) {
        ShowSeatType showSeatType  = new ShowSeatType();
        try {
            Boolean isSuccess = manageSeatsDaoImpl.removeSeat(deleteSeatType.getSeatName()); 
            showSeatType.setSuccess(isSuccess);
        }
        catch (SeatException ex) {
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add(ex.getMessage());
            showSeatType.setSuccess(false);
            showSeatType.setErrorMessage(errorMessages);
        }
        return showSeatType;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * updateSeat.
     * 
     * @param updateSeatDTO
     * @return
     * </pre>
     */
    @Override
    public ShowSeatType updateSeat(UpdateSeatType updateSeatType) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * <pre>
     * <b>Description : </b>
     * searchSeats.
     * 
     * @return
     * </pre>
     */
    @Override
    public ShowSeatsResultSetType searchSeats() {
        ShowSeatsResultSetType resultSetType = new ShowSeatsResultSetType();
        try {
            List<Seat> seats = manageSeatsDaoImpl.getSeats();
            if (seats != null && !seats.isEmpty()) {
                resultSetType = seatMapper.mapSeatsToResponse(seats);
            }
            if (resultSetType.getSeatsType() != null) {
				Collections.sort(resultSetType.getSeatsType(), new Comparator<ShowSeatType>() {
					public int compare(ShowSeatType showSeatType1, ShowSeatType showSeatType2) {
						String[] words1 = showSeatType1.getSeatName().split(WASConstants.HIPHEN);
						String[] words2 = showSeatType2.getSeatName().split(WASConstants.HIPHEN);
						if (words1.length > 0 && words2.length >0) {
							if (!words1[2].substring(0, 3).equalsIgnoreCase(words2[2].substring(0, 3))) {
								return Integer.valueOf(words1[2].substring(0, 3)).compareTo(Integer.valueOf(words2[2].substring(0, 3 )));
							}
							return 0;
						}
						return 0;
					}
				});
			}
        }
        catch (SeatException ex) {
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add(ex.getMessage());
            resultSetType.setSuccess(false);
            resultSetType.setErrorMessage(errorMessages);
        }
        return resultSetType;
    }

}

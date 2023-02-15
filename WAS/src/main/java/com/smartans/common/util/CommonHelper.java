/*
 * This code contains copyright information which is the proprietary property
 * of SITA Information Network Computing Limited (SITA). No part of this
 * code may be reproduced, stored or transmitted in any form without the prior
 * written permission of SITA.
 *
 * Copyright (C) SITA Information Network Computing Limited 2012.
 * All rights reserved.
 */
package main.java.com.smartans.common.util;

import java.util.List;

/**
 * <pre>
 * <b>Description : </b>
 * CommonHelper.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-12 12:27:47 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public final class CommonHelper {

    /**
     * 
     * <pre>
     * <b>Description : </b>
     * Constructs an instance of 'CommonHelper'.
     * 
     * </pre>
     */
    private CommonHelper() {

    }

    /**
     * <pre>
     * <b>Description : </b>
     * addToInvalidErrMsgDescList.
     * 
     * @param errorCode , not null.
     * @param errorMessages , may be null.
     * </pre>
     */
    public static void addToInvalidErrMsgDescList(final String errorCode, final List<String> errorMessages) {
        errorMessages.add(errorCode);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Populate the error message list.
     * 
     * @param errorCode , may be null.
     * @param errorMessages , may be null.
     * </pre>
     */
    public static void populateErrorMsgDescList(final String errorCode, final List<String> errorMessages) {
        String errorMsg = ErrorCodeMessageUtil.getMessageForErrorCode(errorCode);
        addToInvalidErrMsgDescList(errorMsg, errorMessages);
    }

      

    /**
     * 
     * <pre>
     * <b>Description : </b>
     * populate error message description list.
     * 
     * @param errorCode , not null.
     * @param requestFaults , may be null.
     * @param invalidName , may be null.
     * </pre>
     */
    public static void populateErrorMsgDescList(final String errorCode, final List<String> requestFaults,
        final String invalidName) {
        String errorMsg = ErrorCodeMessageUtil.getMessageForErrorCode(errorCode);
        if (errorMsg != null) {
            String errorDescription = errorMsg.concat(" - " + invalidName);
            addToInvalidErrMsgDescList(errorDescription, requestFaults);
        }

    }

}

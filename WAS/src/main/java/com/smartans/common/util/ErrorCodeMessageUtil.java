/*
 * This code contains copyright information which is the proprietary property
 * of SITA Information Network Computing Limited (SITA). No part of this
 * code may be reproduced, stored or transmitted in any form without the prior
 * written permission of SITA.
 *
 * Copyright (C) SITA Information Network Computing Limited 2011-2012.
 * All rights reserved.
 */
package main.java.com.smartans.common.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import main.java.com.smartans.common.constants.WASConstants;
/**
 * <pre>
 * <b>Description : </b>
 * ErrorCodeMessageUtil.java.
 * 
 * @version $Revision: 1 $ $Date: 2013-10-12 12:16:50 PM $
 * @author $Author: akash.kantharaj $ 
 * </pre>
 */
public final class ErrorCodeMessageUtil {
    /**
     * BUNDLE_NAME.
     */
    private static final String BUNDLE_NAME = "main.resources.properties.messages";

    /**
     * This attribute represents ResourceBundle error code.
     */
    private static ResourceBundle errorCodesResBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * 
     * <pre>
     * <b>Description : </b>
     * Constructor.
     *
     * @throws IllegalAccessException , if one tries to instantiate this class using the private constructor.
     * </pre>
     */
    private ErrorCodeMessageUtil() throws IllegalAccessException {
        throw new IllegalAccessException(StringUtil.addString("Can not create instance of util class ",
            this.getClass()));
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Method for generating error message.
     *
     * @param errorCode , the error code , not null.
     * 
     * @return error message found , null if not found.
     * </pre>
     */
    public static String getMessageForErrorCode(final String errorCode) {
        return getErrorMsgInternal(errorCode);
    }

    /**
     * <pre>
     * <b>Description : </b>
     * Method for generating error message.
     *
     * @param errorCode , the error code , not null.
     * @param errorParams , the error parameters to be replaced in the message read from property file , not null
     * 
     * @return error message found , null if not found.
     * </pre>
     */
    public static String getMessageForErrorCode(final String errorCode, final Object... errorParams) {
        return getErrorMsgInternal(errorCode, errorParams);
    }


    /**
     * <pre>
     * <b>Description : </b>
     * Private method to get error message.
     *
     * @param errorCode , the error code , not null.
     * @param errorParams , the error parameters to be replaced in the message read from property file , not null.
     * 
     * @return error message found , null if not found.
     * </pre>
     */
    private static String getErrorMsgInternal(final String errorCode, final Object... errorParams) {
        String errorMessage = null;
        if (errorCode != null) {
            try {
                if (ErrorCodeMessageUtil.errorCodesResBundle != null) {
                    errorMessage = (String) ErrorCodeMessageUtil.errorCodesResBundle.getObject(errorCode);
                    errorMessage = errorCode + WASConstants.ERROR_MESSAGE_SEPARATOR + errorMessage;
                }
            }
            catch (final Exception excp) {
                errorMessage = errorCode;
            }
            if ((errorMessage == null) || (errorMessage.length() == 0)) {
                errorMessage = errorCode;
            }
        }
        if (errorParams != null) {
            errorMessage = MessageFormat.format(errorMessage, errorParams);
        }
        return errorMessage;
    }
    
    

}

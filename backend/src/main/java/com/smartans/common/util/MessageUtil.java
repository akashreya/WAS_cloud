package com.smartans.common.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Utility class for internationalized error and success messages.
 * Provides methods to retrieve localized messages with parameter substitution.
 * 
 * @author akash.kantharaj
 * @version 2.0.0
 */
@Component
public class MessageUtil {
    
    private final MessageSource messageSource;
    
    /**
     * Constructor with MessageSource injection.
     * 
     * @param messageSource Spring MessageSource for internationalization
     */
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    /**
     * Get localized message for the given code.
     * 
     * @param code message code from messages.properties
     * @return localized message, or the code itself if not found
     */
    public String getMessage(String code) {
        return getMessage(code, null, LocaleContextHolder.getLocale());
    }
    
    /**
     * Get localized message for the given code with parameters.
     * 
     * @param code message code from messages.properties
     * @param params parameters to substitute in the message
     * @return localized message with parameters substituted
     */
    public String getMessage(String code, Object... params) {
        return getMessage(code, params, LocaleContextHolder.getLocale());
    }
    
    /**
     * Get localized message for the given code with specific locale.
     * 
     * @param code message code from messages.properties
     * @param params parameters to substitute in the message
     * @param locale specific locale for the message
     * @return localized message with parameters substituted
     */
    public String getMessage(String code, Object[] params, Locale locale) {
        try {
            return messageSource.getMessage(code, params, locale);
        } catch (Exception e) {
            // Return the code itself if message not found
            return code;
        }
    }
    
    /**
     * Get localized message with default fallback.
     * 
     * @param code message code from messages.properties
     * @param defaultMessage default message if code not found
     * @param params parameters to substitute in the message
     * @return localized message or default message if code not found
     */
    public String getMessageWithDefault(String code, String defaultMessage, Object... params) {
        try {
            return messageSource.getMessage(code, params, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return defaultMessage;
        }
    }
    
    /**
     * Format error message with error code prefix.
     * Legacy WAS format: "ERROR_CODE: Message"
     * 
     * @param errorCode error code
     * @param params parameters for the message
     * @return formatted error message with code prefix
     */
    public String getFormattedErrorMessage(String errorCode, Object... params) {
        String message = getMessage(errorCode, params);
        return errorCode + ": " + message;
    }
    
    /**
     * Get success message for the given code.
     * 
     * @param successCode success code from messages.properties
     * @param params parameters to substitute in the message
     * @return success message
     */
    public String getSuccessMessage(String successCode, Object... params) {
        return getMessage(successCode, params);
    }
}
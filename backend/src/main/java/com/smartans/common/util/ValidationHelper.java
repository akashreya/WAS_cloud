package com.smartans.common.util;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Helper class for validation operations.
 * Modernized version of legacy CommonHelper with additional validation utilities.
 * 
 * @author akash.kantharaj
 * @version 2.0.0
 */
public final class ValidationHelper {
    
    // Pattern for employee ID validation (M followed by 7 digits)
    private static final Pattern EMPLOYEE_ID_PATTERN = Pattern.compile("^M\\d{7}$");
    
    // Pattern for seat number validation (alphanumeric with hyphens)
    private static final Pattern SEAT_NUMBER_PATTERN = Pattern.compile("^[A-Z0-9-]+$");
    
    // Pattern for extension number validation (numeric only)
    private static final Pattern EXTENSION_PATTERN = Pattern.compile("^\\d+$");
    
    // Pattern for employee name validation (letters and spaces only)
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    
    /**
     * Private constructor to prevent instantiation.
     */
    private ValidationHelper() {
        throw new IllegalStateException("Utility class - cannot be instantiated");
    }
    
    /**
     * Add error message to the list.
     * 
     * @param errorMessage error message to add
     * @param errorMessages list to add the message to
     */
    public static void addErrorMessage(String errorMessage, List<String> errorMessages) {
        if (errorMessage != null && errorMessages != null) {
            errorMessages.add(errorMessage);
        }
    }
    
    /**
     * Check if a string is null or empty.
     * 
     * @param str string to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Check if a string is not null and not empty.
     * 
     * @param str string to check
     * @return true if not null and not empty, false otherwise
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * Validate employee ID format.
     * 
     * @param employeeId employee ID to validate
     * @return true if valid format, false otherwise
     */
    public static boolean isValidEmployeeId(String employeeId) {
        return isNotEmpty(employeeId) && EMPLOYEE_ID_PATTERN.matcher(employeeId).matches();
    }
    
    /**
     * Validate seat number format.
     * 
     * @param seatNumber seat number to validate
     * @return true if valid format, false otherwise
     */
    public static boolean isValidSeatNumber(String seatNumber) {
        return isNotEmpty(seatNumber) && SEAT_NUMBER_PATTERN.matcher(seatNumber).matches();
    }
    
    /**
     * Validate extension number format.
     * 
     * @param extensionNumber extension number to validate
     * @return true if valid format, false otherwise
     */
    public static boolean isValidExtensionNumber(String extensionNumber) {
        return isNotEmpty(extensionNumber) && EXTENSION_PATTERN.matcher(extensionNumber).matches();
    }
    
    /**
     * Validate employee name format.
     * 
     * @param name employee name to validate
     * @return true if valid format, false otherwise
     */
    public static boolean isValidEmployeeName(String name) {
        return isNotEmpty(name) && NAME_PATTERN.matcher(name.trim()).matches();
    }
    
    /**
     * Validate string length is within bounds.
     * 
     * @param str string to validate
     * @param minLength minimum length (inclusive)
     * @param maxLength maximum length (inclusive)
     * @return true if length is within bounds, false otherwise
     */
    public static boolean isValidLength(String str, int minLength, int maxLength) {
        if (str == null) {
            return minLength <= 0;
        }
        int length = str.length();
        return length >= minLength && length <= maxLength;
    }
    
    /**
     * Concatenate multiple objects into a single string.
     * Legacy support for StringUtil.addString method.
     * 
     * @param objects objects to concatenate
     * @return concatenated string
     */
    public static String concatenate(Object... objects) {
        if (objects == null || objects.length == 0) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Object obj : objects) {
            if (obj != null) {
                sb.append(obj.toString());
            }
        }
        return sb.toString();
    }
}
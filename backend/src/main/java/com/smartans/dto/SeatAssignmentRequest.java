package com.smartans.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object for seat assignment requests.
 * Contains validation rules based on legacy WAS business requirements.
 * 
 * @author akash.kantharaj
 * @version 2.0.0
 */
public class SeatAssignmentRequest {
    
    @NotBlank(message = "validation.seat.number.required")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "Invalid seat number format")
    private String seatNumber;
    
    @NotBlank(message = "validation.employee.id.required")
    @Pattern(regexp = "^M\\d{7}$", message = "Err-ME-003")
    private String employeeId;

    public SeatAssignmentRequest() {
    }

    public SeatAssignmentRequest(String seatNumber, String employeeId) {
        this.seatNumber = seatNumber;
        this.employeeId = employeeId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
package com.smartans.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating new seats.
 * Contains validation rules based on legacy WAS business requirements.
 * 
 * @author akash.kantharaj
 * @version 2.0.0
 */
public class CreateSeatRequest {
    
    @NotBlank(message = "validation.seat.number.required")
    @Size(min = 1, max = 11, message = "validation.seat.number.length")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "Invalid seat number format")
    private String seatNumber;
    
    @NotBlank(message = "validation.seat.extension.required")
    @Size(min = 1, max = 6, message = "validation.seat.extension.length")
    @Pattern(regexp = "^\\d+$", message = "Extension number must be numeric")
    private String extensionNumber;
    
    @NotNull(message = "validation.seat.manager.flag.required")
    private Boolean isManagerSeat;

    public CreateSeatRequest() {
    }

    public CreateSeatRequest(String seatNumber, String extensionNumber, Boolean isManagerSeat) {
        this.seatNumber = seatNumber;
        this.extensionNumber = extensionNumber;
        this.isManagerSeat = isManagerSeat;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public Boolean getIsManagerSeat() {
        return isManagerSeat;
    }

    public void setIsManagerSeat(Boolean isManagerSeat) {
        this.isManagerSeat = isManagerSeat;
    }
}
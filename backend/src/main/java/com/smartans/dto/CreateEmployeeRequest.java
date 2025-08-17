package com.smartans.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating new employees.
 * Contains validation rules based on legacy WAS business requirements.
 * 
 * @author akash.kantharaj
 * @version 2.0.0
 */
public class CreateEmployeeRequest {
    
    @NotBlank(message = "validation.employee.id.required")
    @Size(min = 1, max = 8, message = "validation.employee.id.length")
    @Pattern(regexp = "^M\\d{7}$", message = "Err-ME-003")
    private String employeeId;
    
    @NotBlank(message = "validation.employee.name.required")
    @Size(min = 1, max = 50, message = "validation.employee.name.length")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Err-ME-005")
    private String name;
    
    @NotNull(message = "validation.employee.designation.required")
    private Integer designationId;

    public CreateEmployeeRequest() {
    }

    public CreateEmployeeRequest(String employeeId, String name, Integer designationId) {
        this.employeeId = employeeId;
        this.name = name;
        this.designationId = designationId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Integer designationId) {
        this.designationId = designationId;
    }
}
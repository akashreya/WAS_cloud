package com.smartans.controller;

import com.smartans.dto.ApiResponse;
import com.smartans.dto.CreateEmployeeRequest;
import com.smartans.entity.Employee;
import com.smartans.exception.EmployeeException;
import com.smartans.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ApiResponse<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            return ApiResponse.success(employees);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch employees: " + e.getMessage());
        }
    }

    @GetMapping("/unassigned")
    public ApiResponse<List<Employee>> getUnassignedEmployees() {
        try {
            List<Employee> employees = employeeService.getUnassignedEmployees();
            return ApiResponse.success(employees);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch unassigned employees: " + e.getMessage());
        }
    }

    @GetMapping("/assigned")
    public ApiResponse<List<Employee>> getAssignedEmployees() {
        try {
            List<Employee> employees = employeeService.getAssignedEmployees();
            return ApiResponse.success(employees);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch assigned employees: " + e.getMessage());
        }
    }

    @GetMapping("/queue")
    public ApiResponse<List<Employee>> getEmployeeQueue() {
        try {
            List<Employee> queue = employeeService.getEmployeeQueue();
            return ApiResponse.success(queue);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch employee queue: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<Employee> getEmployeeById(@PathVariable String id) {
        try {
            return employeeService.getEmployeeById(id)
                    .map(ApiResponse::success)
                    .orElse(ApiResponse.error("Employee not found"));
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch employee: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ApiResponse<List<Employee>> searchEmployees(@RequestParam String name) {
        try {
            List<Employee> employees = employeeService.searchEmployeesByName(name);
            return ApiResponse.success(employees);
        } catch (Exception e) {
            return ApiResponse.error("Failed to search employees: " + e.getMessage());
        }
    }

    @PostMapping
    public ApiResponse<Employee> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        try {
            Employee employee = employeeService.createEmployee(
                    request.getEmployeeId(),
                    request.getName(),
                    request.getDesignationId()
            );
            return ApiResponse.success("Employee created successfully", employee);
        } catch (EmployeeException e) {
            return ApiResponse.error(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("Failed to create employee: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Employee> updateEmployee(
            @PathVariable String id,
            @RequestBody CreateEmployeeRequest request) {
        try {
            Employee employee = employeeService.updateEmployee(
                    id,
                    request.getName(),
                    request.getDesignationId()
            );
            return ApiResponse.success("Employee updated successfully", employee);
        } catch (EmployeeException e) {
            return ApiResponse.error(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("Failed to update employee: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteEmployee(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return ApiResponse.success("Employee deleted successfully");
        } catch (EmployeeException e) {
            return ApiResponse.error(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("Failed to delete employee: " + e.getMessage());
        }
    }
}
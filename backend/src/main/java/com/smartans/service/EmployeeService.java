package com.smartans.service;

import com.smartans.entity.Employee;
import com.smartans.entity.Designation;
import com.smartans.exception.EmployeeException;
import com.smartans.repository.EmployeeRepository;
import com.smartans.repository.DesignationRepository;
import com.smartans.common.util.MessageUtil;
import com.smartans.common.constants.WASConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class EmployeeService {

    private static final int MAX_MANAGERS = WASConstants.MAX_MANAGER_SEATS;
    private static final Pattern EMPLOYEE_ID_PATTERN = Pattern.compile("M\\d{7}"); // M#######
    private static final Pattern EMPLOYEE_NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$"); // Only letters and spaces

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    @Lazy
    private SeatService seatService; // Inject SeatService

    @Autowired
    private MessageUtil messageUtil; // Inject MessageUtil


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getUnassignedEmployees() {
        return employeeRepository.findUnassignedEmployees();
    }

    public List<Employee> getEmployeeQueue() {
        List<Employee> unassigned = employeeRepository.findUnassignedEmployees();
        // Return only first 10 employees as per WAS business rule
        return unassigned.size() > WASConstants.QUEUE_SIZE ? 
               unassigned.subList(0, WASConstants.QUEUE_SIZE) : unassigned;
    }

    public boolean isEmployeeInQueue(String employeeId) {
        List<Employee> queue = getEmployeeQueue();
        return queue.stream().anyMatch(emp -> emp.getEmployeeId().equals(employeeId));
    }

    public List<Employee> getAssignedEmployees() {
        return employeeRepository.findAssignedEmployees();
    }

    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    public Employee createEmployee(String employeeId, String name, Integer designationId) {
        // Validate Employee ID format
        if (!EMPLOYEE_ID_PATTERN.matcher(employeeId).matches()) {
            throw new EmployeeException(messageUtil.getMessage("Err-ME-003")); // Custom error code for format
        }
        // Validate Employee ID length
        if (employeeId.length() > 8) {
            throw new EmployeeException(messageUtil.getMessage("Err-ME-004")); // Custom error code for length
        }

        // Validate Employee Name format
        if (!EMPLOYEE_NAME_PATTERN.matcher(name).matches()) {
            throw new EmployeeException(messageUtil.getMessage("Err-ME-005")); // Custom error code for format
        }
        // Validate Employee Name length
        if (name.length() > 50) {
            throw new EmployeeException(messageUtil.getMessage("Err-ME-006")); // Custom error code for length
        }

        if (employeeRepository.existsById(employeeId)) {
            throw new EmployeeException(messageUtil.getMessage("Err-ME-002"));
        }

        Optional<Designation> designation = designationRepository.findById(designationId);
        if (designation.isEmpty()) {
            throw new EmployeeException(messageUtil.getMessage("Err-ME-007", designationId)); // Custom error code for invalid designation
        }

        // Check for maximum managers if the new employee is a manager
        if (designation.get().getIsManager()) {
            long managerCount = employeeRepository.countByDesignation_IsManager(true);
            if (managerCount >= MAX_MANAGERS) {
                throw new EmployeeException(messageUtil.getMessage("Err-ME-001"));
            }
        }

        Employee employee = new Employee(employeeId, name, designation.get());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeException(messageUtil.getMessage("Err-ME-008", employeeId))); // Custom error code for not found

        // If the employee is assigned to a seat, unassign it first
        if (employee.getSeat() != null) {
            seatService.unassignSeat(employee.getSeat().getSeatNumber());
        }

        employeeRepository.deleteById(employeeId);
    }

    public Employee updateEmployee(String employeeId, String name, Integer designationId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeException(messageUtil.getMessage("Err-ME-008", employeeId)));

        if (name != null) {
            // Validate Employee Name format for update
            if (!EMPLOYEE_NAME_PATTERN.matcher(name).matches()) {
                throw new EmployeeException(messageUtil.getMessage("Err-ME-005"));
            }
            // Validate Employee Name length for update
            if (name.length() > 50) {
                throw new EmployeeException(messageUtil.getMessage("Err-ME-006"));
            }
            employee.setName(name);
        }

        if (designationId != null) {
            Designation newDesignation = designationRepository.findById(designationId)
                    .orElseThrow(() -> new EmployeeException(messageUtil.getMessage("Err-ME-007", designationId)));
            
            // Check for maximum managers if changing to manager designation
            if (newDesignation.getIsManager() && !employee.getDesignation().getIsManager()) {
                long managerCount = employeeRepository.countByDesignation_IsManager(true);
                if (managerCount >= MAX_MANAGERS) {
                    throw new EmployeeException(messageUtil.getMessage("Err-ME-001"));
                }
            }
            employee.setDesignation(newDesignation);
        }

        return employeeRepository.save(employee);
    }
}


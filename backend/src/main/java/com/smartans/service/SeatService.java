package com.smartans.service;

import com.smartans.entity.Seat;
import com.smartans.entity.Employee;
import com.smartans.exception.SeatAssignmentException;
import com.smartans.exception.SeatException;
import com.smartans.repository.SeatRepository;
import com.smartans.repository.EmployeeRepository;
import com.smartans.common.util.MessageUtil;
import com.smartans.common.constants.WASConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private MessageUtil messageUtil;
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<Seat> getAllSeats() {
        return seatRepository.findAllWithEmployeeAndDesignation();
    }

    public Optional<Seat> getSeatById(String seatNumber) {
        return seatRepository.findById(seatNumber);
    }

    public List<Seat> getAvailableSeats() {
        return seatRepository.findAvailableSeats();
    }

    public List<Seat> getOccupiedSeats() {
        return seatRepository.findOccupiedSeats();
    }

    public List<Seat> getManagerSeats() {
        return seatRepository.findByIsManagerSeat(true);
    }

    public List<Seat> getAvailableManagerSeats() {
        return seatRepository.findAvailableManagerSeats();
    }

    public Seat createSeat(String seatNumber, String extensionNumber, Boolean isManagerSeat) {
        if (seatRepository.existsById(seatNumber)) {
            throw new SeatException(messageUtil.getMessage("Err-MS-003"));
        }

        if (seatRepository.existsByExtensionNumber(extensionNumber)) {
            throw new SeatException(messageUtil.getMessage("Err-MS-004"));
        }

        if (seatRepository.count() >= WASConstants.MAX_TOTAL_SEATS) {
            throw new SeatException(messageUtil.getMessage("Err-MS-002"));
        }

        if (isManagerSeat && seatRepository.countManagerSeats() >= WASConstants.MAX_MANAGER_SEATS) {
            throw new SeatException(messageUtil.getMessage("Err-MS-001"));
        }

        Seat seat = new Seat(seatNumber, extensionNumber, isManagerSeat);
        return seatRepository.save(seat);
    }

    public void deleteSeat(String seatNumber) {
        Seat seat = seatRepository.findById(seatNumber)
                .orElseThrow(() -> new SeatException(messageUtil.getMessage("Err-MS-005", seatNumber)));

        if (seat.isOccupied()) {
            throw new SeatException(messageUtil.getMessage("Err-MS-006", seatNumber));
        }

        seatRepository.deleteById(seatNumber);
    }

    public Seat assignSeat(String seatNumber, String employeeId) {
        Seat seat = seatRepository.findById(seatNumber)
                .orElseThrow(() -> new SeatAssignmentException(
                        messageUtil.getMessage("Err-SA-008", seatNumber)));

        if (seat.isOccupied()) {
            throw new SeatAssignmentException(messageUtil.getMessage("Err-SA-007"));
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new SeatAssignmentException(
                        messageUtil.getMessage("Err-SA-009", employeeId)));

        if (employee.getSeat() != null) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-010", employee.getSeat().getSeatNumber()));
        }

        // QUEUE BUSINESS RULE: Employee must be in the current queue to be assigned
        if (!employeeService.isEmployeeInQueue(employeeId)) {
            throw new SeatAssignmentException(messageUtil.getMessage("Err-SA-011"));
        }

        // Check if manager designation requires manager seat
        if (employee.getDesignation().getIsManager() && !seat.getIsManagerSeat()) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-001", seat.getSeatNumber()));
        }

        // Check if non-manager tries to take manager seat
        if (!employee.getDesignation().getIsManager() && seat.getIsManagerSeat()) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-002", seat.getSeatNumber()));
        }

        seat.setEmployee(employee);
        return seatRepository.save(seat);
    }

    public void unassignSeat(String seatNumber) {
        Seat seat = seatRepository.findById(seatNumber)
                .orElseThrow(() -> new SeatException(messageUtil.getMessage("Err-MS-005", seatNumber)));

        if (!seat.isOccupied()) {
            throw new SeatException(messageUtil.getMessage("Err-MS-007", seatNumber));
        }

        seat.setEmployee(null);
        seatRepository.save(seat);
    }

    public void reassignSeat(String fromSeatNumber, String toSeatNumber) {
        Seat fromSeat = seatRepository.findById(fromSeatNumber)
                .orElseThrow(() -> new SeatAssignmentException(
                        messageUtil.getMessage("Err-SA-012", fromSeatNumber)));

        Seat toSeat = seatRepository.findById(toSeatNumber)
                .orElseThrow(() -> new SeatAssignmentException(
                        messageUtil.getMessage("Err-SA-013", toSeatNumber)));

        if (!fromSeat.isOccupied()) {
            throw new SeatAssignmentException(messageUtil.getMessage("Err-SA-014", fromSeatNumber));
        }

        if (toSeat.isOccupied()) {
            throw new SeatAssignmentException(messageUtil.getMessage("Err-SA-007"));
        }

        Employee employee = fromSeat.getEmployee();

        // Check if manager designation requires manager seat
        if (employee.getDesignation().getIsManager() && !toSeat.getIsManagerSeat()) {
            throw new SeatAssignmentException(messageUtil.getMessage("Err-SA-005",
                    employee.getEmployeeId(), toSeat.getSeatNumber()));
        }

        // Check if non-manager tries to take manager seat
        if (!employee.getDesignation().getIsManager() && toSeat.getIsManagerSeat()) {
            throw new SeatAssignmentException(messageUtil.getMessage("Err-SA-006",
                    employee.getEmployeeId(), toSeat.getSeatNumber()));
        }

        fromSeat.setEmployee(null);
        toSeat.setEmployee(employee);

        seatRepository.save(fromSeat);
        seatRepository.save(toSeat);
    }

    public void swapSeats(String seatNumber1, String seatNumber2) {
        Seat seat1 = seatRepository.findById(seatNumber1)
                .orElseThrow(() -> new SeatAssignmentException(
                        messageUtil.getMessage("Err-SA-008", seatNumber1)));

        Seat seat2 = seatRepository.findById(seatNumber2)
                .orElseThrow(() -> new SeatAssignmentException(
                        messageUtil.getMessage("Err-SA-008", seatNumber2)));

        if (!seat1.isOccupied() || !seat2.isOccupied()) {
            throw new SeatAssignmentException(messageUtil.getMessage("Err-SA-015"));
        }

        Employee employee1 = seat1.getEmployee();
        Employee employee2 = seat2.getEmployee();

        // Check manager seat requirements for employee1 moving to seat2
        if (employee1.getDesignation().getIsManager() && !seat2.getIsManagerSeat()) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-003", employee1.getName(), employee2.getName()));
        }
        // Check manager seat requirements for employee2 moving to seat1
        if (employee2.getDesignation().getIsManager() && !seat1.getIsManagerSeat()) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-003", employee2.getName(), employee1.getName()));
        }

        // Check if non-manager employee1 tries to move to manager seat2
        if (!employee1.getDesignation().getIsManager() && seat2.getIsManagerSeat()) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-004", employee1.getName(), employee2.getName()));
        }
        // Check if non-manager employee2 tries to move to manager seat1
        if (!employee2.getDesignation().getIsManager() && seat1.getIsManagerSeat()) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-004", employee2.getName(), employee1.getName()));
        }

        seat1.setEmployee(employee2);
        seat2.setEmployee(employee1);

        seatRepository.save(seat1);
        seatRepository.save(seat2);
    }

    /**
     * Reassign specific employee to a different seat (employee-centric operation)
     */
    public void reassignEmployee(String employeeId, String toSeatNumber) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new SeatAssignmentException(
                        messageUtil.getMessage("Err-SA-009", employeeId)));

        // Employee must currently be seated to be reassigned
        if (employee.getSeat() == null) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-016", employeeId));
        }

        Seat toSeat = seatRepository.findById(toSeatNumber)
                .orElseThrow(() -> new SeatAssignmentException(
                        messageUtil.getMessage("Err-SA-013", toSeatNumber)));

        if (toSeat.isOccupied()) {
            throw new SeatAssignmentException(messageUtil.getMessage("Err-SA-007"));
        }

        // Check if manager designation requires manager seat
        if (employee.getDesignation().getIsManager() && !toSeat.getIsManagerSeat()) {
            throw new SeatAssignmentException(messageUtil.getMessage("Err-SA-005",
                    employee.getEmployeeId(), toSeat.getSeatNumber()));
        }

        // Check if non-manager tries to take manager seat
        if (!employee.getDesignation().getIsManager() && toSeat.getIsManagerSeat()) {
            throw new SeatAssignmentException(messageUtil.getMessage("Err-SA-006",
                    employee.getEmployeeId(), toSeat.getSeatNumber()));
        }

        Seat fromSeat = employee.getSeat();
        fromSeat.setEmployee(null);
        toSeat.setEmployee(employee);

        seatRepository.save(fromSeat);
        seatRepository.save(toSeat);
    }

    /**
     * Swap two specific employees between their current seats (employee-centric operation)
     */
    public void swapEmployees(String employeeId1, String employeeId2) {
        Employee employee1 = employeeRepository.findById(employeeId1)
                .orElseThrow(() -> new SeatAssignmentException(
                        messageUtil.getMessage("Err-SA-009", employeeId1)));

        Employee employee2 = employeeRepository.findById(employeeId2)
                .orElseThrow(() -> new SeatAssignmentException(
                        messageUtil.getMessage("Err-SA-009", employeeId2)));

        // Both employees must be currently seated to be swapped
        if (employee1.getSeat() == null) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-016", employeeId1));
        }
        if (employee2.getSeat() == null) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-016", employeeId2));
        }

        Seat seat1 = employee1.getSeat();
        Seat seat2 = employee2.getSeat();

        // Check if both employees can swap seats based on manager/employee seat requirements
        if (employee1.getDesignation().getIsManager() && !seat2.getIsManagerSeat()) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-003", employee1.getName(), employee2.getName()));
        }
        if (employee2.getDesignation().getIsManager() && !seat1.getIsManagerSeat()) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-003", employee2.getName(), employee1.getName()));
        }
        if (!employee1.getDesignation().getIsManager() && seat2.getIsManagerSeat()) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-004", employee1.getName(), employee2.getName()));
        }
        if (!employee2.getDesignation().getIsManager() && seat1.getIsManagerSeat()) {
            throw new SeatAssignmentException(
                    messageUtil.getMessage("Err-SA-004", employee2.getName(), employee1.getName()));
        }

        // Perform JPA-based swap with temporary null assignments for SQLite compatibility
        // Step 1: Temporarily unassign both employees to avoid constraint violations
        Employee emp1 = seat1.getEmployee();
        Employee emp2 = seat2.getEmployee();
        
        seat1.setEmployee(null);
        seat2.setEmployee(null);
        
        // Flush to ensure the null assignments are persisted
        entityManager.flush();
        
        // Step 2: Assign employees to swapped seats
        seat1.setEmployee(emp2);
        seat2.setEmployee(emp1);
        
        // Save the updated seats
        seatRepository.save(seat1);
        seatRepository.save(seat2);
    }
}
package com.smartans.controller;

import com.smartans.dto.ApiResponse;
import com.smartans.dto.CreateSeatRequest;
import com.smartans.dto.SeatAssignmentRequest;
import com.smartans.entity.Seat;
import com.smartans.exception.SeatAssignmentException;
import com.smartans.exception.SeatException;
import com.smartans.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@CrossOrigin(origins = "http://localhost:3000")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    public ApiResponse<List<Seat>> getAllSeats() {
        try {
            List<Seat> seats = seatService.getAllSeats();
            return ApiResponse.success(seats);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch seats: " + e.getMessage());
        }
    }

    @GetMapping("/available")
    public ApiResponse<List<Seat>> getAvailableSeats() {
        try {
            List<Seat> seats = seatService.getAvailableSeats();
            return ApiResponse.success(seats);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch available seats: " + e.getMessage());
        }
    }

    @GetMapping("/occupied")
    public ApiResponse<List<Seat>> getOccupiedSeats() {
        try {
            List<Seat> seats = seatService.getOccupiedSeats();
            return ApiResponse.success(seats);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch occupied seats: " + e.getMessage());
        }
    }

    @GetMapping("/manager-seats")
    public ApiResponse<List<Seat>> getManagerSeats() {
        try {
            List<Seat> seats = seatService.getManagerSeats();
            return ApiResponse.success(seats);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch manager seats: " + e.getMessage());
        }
    }

    @GetMapping("/available-manager-seats")
    public ApiResponse<List<Seat>> getAvailableManagerSeats() {
        try {
            List<Seat> seats = seatService.getAvailableManagerSeats();
            return ApiResponse.success(seats);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch available manager seats: " + e.getMessage());
        }
    }

    @GetMapping("/{seatNumber}")
    public ApiResponse<Seat> getSeatById(@PathVariable String seatNumber) {
        try {
            return seatService.getSeatById(seatNumber)
                    .map(ApiResponse::success)
                    .orElse(ApiResponse.error("Seat not found"));
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch seat: " + e.getMessage());
        }
    }

    @PostMapping
    public ApiResponse<Seat> createSeat(@Valid @RequestBody CreateSeatRequest request) {
        try {
            Seat seat = seatService.createSeat(
                    request.getSeatNumber(),
                    request.getExtensionNumber(),
                    request.getIsManagerSeat());
            return ApiResponse.success("Seat created successfully", seat);
        } catch (SeatException e) {
            return ApiResponse.error(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("Failed to create seat: " + e.getMessage());
        }
    }

    @DeleteMapping("/{seatNumber}")
    public ApiResponse<String> deleteSeat(@PathVariable String seatNumber) {
        try {
            seatService.deleteSeat(seatNumber);
            return ApiResponse.success("Seat deleted successfully");
        } catch (SeatException e) {
            return ApiResponse.error(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("Failed to delete seat: " + e.getMessage());
        }
    }

    @PostMapping("/assign")
    public ApiResponse<Seat> assignSeat(@Valid @RequestBody SeatAssignmentRequest request) {
        try {
            Seat seat = seatService.assignSeat(request.getSeatNumber(), request.getEmployeeId());
            return ApiResponse.success("Seat assigned successfully", seat);
        } catch (SeatAssignmentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/{seatNumber}/unassign")
    public ApiResponse<String> unassignSeat(@PathVariable String seatNumber) {
        try {
            seatService.unassignSeat(seatNumber);
            return ApiResponse.success("Seat unassigned successfully");
        } catch (SeatAssignmentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/reassign")
    public ApiResponse<String> reassignEmployee(
            @RequestParam String employeeId,
            @RequestParam String toSeat) {
        try {
            seatService.reassignEmployee(employeeId, toSeat);
            return ApiResponse.success("Employee reassigned successfully");
        } catch (SeatAssignmentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/swap-employees")
    public ApiResponse<String> swapEmployees(
            @RequestParam String employee1,
            @RequestParam String employee2) {
        try {
            seatService.swapEmployees(employee1, employee2);
            return ApiResponse.success("Employees swapped successfully");
        } catch (SeatAssignmentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/swap")
    public ApiResponse<String> swapSeats(
            @RequestParam String seat1,
            @RequestParam String seat2) {
        try {
            seatService.swapSeats(seat1, seat2);
            return ApiResponse.success("Seats swapped successfully");
        } catch (SeatAssignmentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}
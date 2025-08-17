package com.smartans.service;

import com.smartans.entity.Seat;
import com.smartans.exception.SeatException;
import com.smartans.common.constants.WASConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * JUnit tests for Seat Capacity Limits Business Rules.
 * 
 * Business Rules Tested:
 * - Maximum Total Seats: 100 seats allowed in the system
 * - Maximum Manager Seats: 5 manager seats allowed
 * - Extension Number Uniqueness: Each seat must have a unique extension number
 * - Seat Number Uniqueness: Each seat must have a unique seat number
 */
@SpringBootTest(classes = com.smartans.WorkspaceAllocationSystemApplication.class)
@ActiveProfiles("test")
@Transactional
@Sql("/test-data.sql")
@DisplayName("Seat Capacity Limits - Business Rules")
class SeatCapacityLimitsTest {

    @Autowired
    private SeatService seatService;

    @Test
    @DisplayName("BR-SC-001: Should enforce maximum total seats limit of 100")
    void testMaximumTotalSeatsLimit() {
        // Create 100 seats (the maximum allowed)
        for (int i = 1; i <= WASConstants.MAX_TOTAL_SEATS; i++) {
            String seatNumber = String.format("WCP3-5F-%03d", i);
            String extensionNumber = String.format("6%04d", i);
            seatService.createSeat(seatNumber, extensionNumber, false);
        }

        // Verify all 100 seats were created
        assertThat(seatService.getAllSeats()).hasSize(WASConstants.MAX_TOTAL_SEATS);

        // Attempt to create 101st seat should fail
        assertThatThrownBy(() -> seatService.createSeat("WCP3-5F-101", "60101", false))
            .isInstanceOf(SeatException.class)
            .hasMessageContaining("Maximum");
    }

    @Test
    @DisplayName("BR-SC-002: Should enforce maximum manager seats limit of 5")
    void testMaximumManagerSeatsLimit() {
        // Create 5 manager seats (the maximum allowed)
        for (int i = 1; i <= WASConstants.MAX_MANAGER_SEATS; i++) {
            String seatNumber = String.format("MGR-5F-%03d", i);
            String extensionNumber = String.format("7%04d", i);
            seatService.createSeat(seatNumber, extensionNumber, true);
        }

        // Verify all 5 manager seats were created
        assertThat(seatService.getManagerSeats()).hasSize(WASConstants.MAX_MANAGER_SEATS);

        // Attempt to create 6th manager seat should fail
        assertThatThrownBy(() -> seatService.createSeat("MGR-5F-006", "70006", true))
            .isInstanceOf(SeatException.class)
            .hasMessageContaining("manager");
    }

    @Test
    @DisplayName("BR-SC-003: Should allow regular seats after manager seat limit is reached")
    void testRegularSeatsAllowedAfterManagerLimit() {
        // Create 5 manager seats (maximum allowed)
        for (int i = 1; i <= WASConstants.MAX_MANAGER_SEATS; i++) {
            String seatNumber = String.format("MGR-5F-%03d", i);
            String extensionNumber = String.format("7%04d", i);
            seatService.createSeat(seatNumber, extensionNumber, true);
        }

        // Creating regular seats should still be allowed
        Seat regularSeat = seatService.createSeat("WCP3-5F-001", "60001", false);
        
        assertThat(regularSeat).isNotNull();
        assertThat(regularSeat.getIsManagerSeat()).isFalse();
        assertThat(seatService.getManagerSeats()).hasSize(5);
        assertThat(seatService.getAllSeats()).hasSize(6);
    }

    @Test
    @DisplayName("BR-SC-004: Should enforce unique seat numbers")
    void testUniqueSeatNumbers() {
        // Create first seat
        seatService.createSeat("WCP3-5F-001", "60001", false);

        // Attempt to create seat with duplicate seat number should fail
        assertThatThrownBy(() -> seatService.createSeat("WCP3-5F-001", "60002", false))
            .isInstanceOf(SeatException.class)
            .hasMessageContaining("exists");
    }

    @Test
    @DisplayName("BR-SC-005: Should enforce unique extension numbers")
    void testUniqueExtensionNumbers() {
        // Create first seat
        seatService.createSeat("WCP3-5F-001", "60001", false);

        // Attempt to create seat with duplicate extension number should fail
        assertThatThrownBy(() -> seatService.createSeat("WCP3-5F-002", "60001", false))
            .isInstanceOf(SeatException.class)
            .hasMessageContaining("unique");
    }

    @Test
    @DisplayName("BR-SC-006: Should track manager and regular seat counts separately")
    void testSeparateManagerAndRegularSeatCounts() {
        // Create 3 manager seats
        for (int i = 1; i <= 3; i++) {
            String seatNumber = String.format("MGR-5F-%03d", i);
            String extensionNumber = String.format("7%04d", i);
            seatService.createSeat(seatNumber, extensionNumber, true);
        }

        // Create 5 regular seats
        for (int i = 1; i <= 5; i++) {
            String seatNumber = String.format("WCP3-5F-%03d", i);
            String extensionNumber = String.format("6%04d", i);
            seatService.createSeat(seatNumber, extensionNumber, false);
        }

        // Verify counts
        assertThat(seatService.getManagerSeats()).hasSize(3);
        assertThat(seatService.getAllSeats()).hasSize(8);
        
        // Should still be able to create 2 more manager seats
        seatService.createSeat("MGR-5F-004", "70004", true);
        seatService.createSeat("MGR-5F-005", "70005", true);
        
        assertThat(seatService.getManagerSeats()).hasSize(5);
        
        // Now manager seat limit should be reached
        assertThatThrownBy(() -> seatService.createSeat("MGR-5F-006", "70006", true))
            .isInstanceOf(SeatException.class)
            .hasMessageContaining("manager");
    }

    @Test
    @DisplayName("BR-SC-007: Should allow deletion and recreation within limits")
    void testDeletionAndRecreationWithinLimits() {
        // Create and then delete a manager seat
        seatService.createSeat("MGR-5F-001", "70001", true);
        seatService.deleteSeat("MGR-5F-001");
        
        assertThat(seatService.getManagerSeats()).hasSize(0);
        
        // Should be able to create manager seat again
        Seat newManagerSeat = seatService.createSeat("MGR-5F-002", "70002", true);
        
        assertThat(newManagerSeat).isNotNull();
        assertThat(newManagerSeat.getIsManagerSeat()).isTrue();
        assertThat(seatService.getManagerSeats()).hasSize(1);
    }

    @Test
    @DisplayName("BR-SC-008: Should validate seat format constraints")
    void testSeatFormatConstraints() {
        // Valid seat creation should succeed
        Seat validSeat = seatService.createSeat("WCP3-5F-001", "60001", false);
        assertThat(validSeat).isNotNull();
        
        // Verify seat was created with correct properties
        assertThat(validSeat.getSeatNumber()).isEqualTo("WCP3-5F-001");
        assertThat(validSeat.getExtensionNumber()).isEqualTo("60001");
        assertThat(validSeat.getIsManagerSeat()).isFalse();
    }

    @Test
    @DisplayName("BR-SC-009: Should handle edge cases at capacity limits")
    void testEdgeCasesAtCapacityLimits() {
        // Create exactly 5 manager seats
        for (int i = 1; i <= 5; i++) {
            seatService.createSeat(String.format("MGR-5F-%03d", i), String.format("7%04d", i), true);
        }
        
        // Create 95 regular seats to reach total of 100
        for (int i = 1; i <= 95; i++) {
            seatService.createSeat(String.format("WCP3-5F-%03d", i), String.format("6%04d", i), false);
        }
        
        // Verify we're at capacity
        assertThat(seatService.getAllSeats()).hasSize(100);
        assertThat(seatService.getManagerSeats()).hasSize(5);
        
        // Should not be able to create any more seats
        assertThatThrownBy(() -> seatService.createSeat("EXTRA-001", "90001", false))
            .isInstanceOf(SeatException.class)
            .hasMessageContaining("Maximum");
            
        assertThatThrownBy(() -> seatService.createSeat("EXTRA-002", "90002", true))
            .isInstanceOf(SeatException.class)
            .hasMessageContaining("Maximum");
    }
}
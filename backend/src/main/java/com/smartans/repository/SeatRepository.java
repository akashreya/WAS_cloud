package com.smartans.repository;

import com.smartans.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {
    
    @Query("SELECT s FROM Seat s LEFT JOIN FETCH s.employee e LEFT JOIN FETCH e.designation WHERE s.employee IS NULL")
    List<Seat> findAvailableSeats();
    
    @Query("SELECT s FROM Seat s LEFT JOIN FETCH s.employee e LEFT JOIN FETCH e.designation WHERE s.employee IS NOT NULL")
    List<Seat> findOccupiedSeats();
    
    @Query("SELECT s FROM Seat s LEFT JOIN FETCH s.employee e LEFT JOIN FETCH e.designation WHERE s.isManagerSeat = :isManagerSeat")
    List<Seat> findByIsManagerSeat(Boolean isManagerSeat);
    
    @Query("SELECT COUNT(s) FROM Seat s WHERE s.isManagerSeat = true")
    Long countManagerSeats();
    
    @Query("SELECT s FROM Seat s LEFT JOIN FETCH s.employee e LEFT JOIN FETCH e.designation WHERE s.isManagerSeat = true AND s.employee IS NULL")
    List<Seat> findAvailableManagerSeats();

    boolean existsByExtensionNumber(String extensionNumber);
    
    @Query("SELECT s FROM Seat s LEFT JOIN FETCH s.employee e LEFT JOIN FETCH e.designation")
    List<Seat> findAllWithEmployeeAndDesignation();
    
    /**
     * Complete employee swap using a single atomic SQL statement for SQLite
     * This avoids intermediate constraint violations by updating both records simultaneously
     */
    @Modifying
    @Query(value = """
        UPDATE seat 
        SET employee_id = CASE 
            WHEN seat_number = :seat1Number THEN :employee2Id
            WHEN seat_number = :seat2Number THEN :employee1Id
            ELSE employee_id 
        END 
        WHERE seat_number IN (:seat1Number, :seat2Number)
        """, nativeQuery = true)
    void atomicSwapEmployeesBySeat(@Param("seat1Number") String seat1Number, 
                                   @Param("seat2Number") String seat2Number,
                                   @Param("employee1Id") String employee1Id, 
                                   @Param("employee2Id") String employee2Id);
}
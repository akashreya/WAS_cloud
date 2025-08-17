package com.smartans.repository;

import com.smartans.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    
    @Query("SELECT e FROM Employee e WHERE NOT EXISTS (SELECT s FROM Seat s WHERE s.employee = e)")
    List<Employee> findUnassignedEmployees();
    
    @Query("SELECT e FROM Employee e WHERE EXISTS (SELECT s FROM Seat s WHERE s.employee = e)")
    List<Employee> findAssignedEmployees();
    
    List<Employee> findByNameContainingIgnoreCase(String name);

    long countByDesignation_IsManager(boolean isManager);
}
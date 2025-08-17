package com.smartans.repository;

import com.smartans.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Integer> {
    
    List<Designation> findByIsManager(Boolean isManager);
}
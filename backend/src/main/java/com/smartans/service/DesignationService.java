package com.smartans.service;

import com.smartans.entity.Designation;
import com.smartans.repository.DesignationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignationService {

    @Autowired
    private DesignationRepository designationRepository;

    public List<Designation> getAllDesignations() {
        return designationRepository.findAll();
    }

    public Optional<Designation> getDesignationById(Integer id) {
        return designationRepository.findById(id);
    }

    public List<Designation> getManagerDesignations() {
        return designationRepository.findByIsManager(true);
    }

    public List<Designation> getNonManagerDesignations() {
        return designationRepository.findByIsManager(false);
    }
}
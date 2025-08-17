package com.smartans.controller;

import com.smartans.dto.ApiResponse;
import com.smartans.entity.Designation;
import com.smartans.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/designations")
@CrossOrigin(origins = "http://localhost:3000")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @GetMapping
    public ApiResponse<List<Designation>> getAllDesignations() {
        try {
            List<Designation> designations = designationService.getAllDesignations();
            return ApiResponse.success(designations);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch designations: " + e.getMessage());
        }
    }

    @GetMapping("/managers")
    public ApiResponse<List<Designation>> getManagerDesignations() {
        try {
            List<Designation> designations = designationService.getManagerDesignations();
            return ApiResponse.success(designations);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch manager designations: " + e.getMessage());
        }
    }

    @GetMapping("/non-managers")
    public ApiResponse<List<Designation>> getNonManagerDesignations() {
        try {
            List<Designation> designations = designationService.getNonManagerDesignations();
            return ApiResponse.success(designations);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch non-manager designations: " + e.getMessage());
        }
    }
}
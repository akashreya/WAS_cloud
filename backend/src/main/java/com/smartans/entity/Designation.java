package com.smartans.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "designation")
public class Designation {

    @Id
    @Column(name = "designation_id")
    private Integer designationId;

    @Column(name = "designation", length = 50)
    private String designation;

    @Column(name = "is_manager")
    private Boolean isManager;

    public Designation() {
    }

    public Designation(Integer designationId, String designation, Boolean isManager) {
        this.designationId = designationId;
        this.designation = designation;
        this.isManager = isManager;
    }
    
    public Designation(String designation, Boolean isManager) {
        this.designation = designation;
        this.isManager = isManager;
    }

    public Integer getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Integer designationId) {
        this.designationId = designationId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(Boolean isManager) {
        this.isManager = isManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Designation that = (Designation) o;
        return Objects.equals(designationId, that.designationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(designationId);
    }

    @Override
    public String toString() {
        return "Designation{" +
                "designationId=" + designationId +
                ", designation='" + designation + '\'' +
                ", isManager=" + isManager +
                '}';
    }
}
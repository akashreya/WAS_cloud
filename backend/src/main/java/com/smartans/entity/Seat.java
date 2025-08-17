package com.smartans.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @Column(name = "seat_number", length = 11)
    private String seatNumber;

    @Column(name = "extension_number", unique = true, length = 6)
    private String extensionNumber;

    @Column(name = "is_manager_seat")
    private Boolean isManagerSeat;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    public Seat() {
    }

    public Seat(String seatNumber, String extensionNumber, Boolean isManagerSeat) {
        this.seatNumber = seatNumber;
        this.extensionNumber = extensionNumber;
        this.isManagerSeat = isManagerSeat;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public Boolean getIsManagerSeat() {
        return isManagerSeat;
    }

    public void setIsManagerSeat(Boolean isManagerSeat) {
        this.isManagerSeat = isManagerSeat;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isOccupied() {
        return employee != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(seatNumber, seat.seatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatNumber);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatNumber='" + seatNumber + '\'' +
                ", extensionNumber='" + extensionNumber + '\'' +
                ", isManagerSeat=" + isManagerSeat +
                ", employee=" + (employee != null ? employee.getEmployeeId() : "null") +
                '}';
    }
}
package com.smartans.config;

import com.smartans.entity.Designation;
import com.smartans.entity.Employee;
import com.smartans.entity.Seat;
import com.smartans.repository.DesignationRepository;
import com.smartans.repository.EmployeeRepository;
import com.smartans.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (designationRepository.count() > 0) {
            return; // Data already loaded
        }

        loadDesignations();
        loadSeats();
        loadEmployees();
    }

    private void loadDesignations() {
        designationRepository.save(new Designation(1, "Programmer Analyst", false));
        designationRepository.save(new Designation(2, "Software Engineer", false));
        designationRepository.save(new Designation(3, "Senior Engineer", false));
        designationRepository.save(new Designation(4, "Senior Software Engineer", false));
        designationRepository.save(new Designation(5, "Delivery Manager", true));
        designationRepository.save(new Designation(6, "Project Manager", true));
        designationRepository.save(new Designation(7, "Senior Project Manager", true));
        designationRepository.save(new Designation(8, "Technical Manager", true));
        designationRepository.save(new Designation(9, "Senior Architect", false));
        designationRepository.save(new Designation(10, "Program Architect", true));
        designationRepository.save(new Designation(11, "Program Director", true));
    }

    private void loadSeats() {
        // Create all 100 seats with proper extension numbers
        for (int i = 1; i <= 100; i++) {
            String seatNumber = String.format("WCP3-5F-%03d", i);
            String extensionNumber = String.valueOf(60000 + i - 1);
            
            // Mark specific seats as manager seats
            boolean isManagerSeat = (i == 13 || i == 43 || i == 63);
            
            seatRepository.save(new Seat(seatNumber, extensionNumber, isManagerSeat));
        }
    }

    private void loadEmployees() {
        // Get designations for employee creation
        Designation seniorEngineer = designationRepository.findById(3).orElse(null);
        Designation programmer = designationRepository.findById(1).orElse(null);
        Designation seniorPM = designationRepository.findById(7).orElse(null);

        if (seniorEngineer != null && programmer != null && seniorPM != null) {
            employeeRepository.save(new Employee("M1011955", "Aishwarya Rai", seniorEngineer));
            employeeRepository.save(new Employee("M1012860", "Amritha Rao", programmer));
            employeeRepository.save(new Employee("M1013955", "Katrina Kaif", seniorEngineer));
            employeeRepository.save(new Employee("M1014860", "Anushka Sharma", programmer));
            employeeRepository.save(new Employee("M1019755", "Kate Winslet", seniorEngineer));
            employeeRepository.save(new Employee("M1015660", "Penelope Cruz", programmer));
            employeeRepository.save(new Employee("M1016955", "Samantha", seniorEngineer));
            employeeRepository.save(new Employee("M1016260", "Bhavana", seniorPM));
            employeeRepository.save(new Employee("M1017955", "Kareena Kapoor", seniorEngineer));
            employeeRepository.save(new Employee("M1016160", "Raveena Tandon", programmer));
            employeeRepository.save(new Employee("M1018955", "Karishma Kapoor", seniorEngineer));
            employeeRepository.save(new Employee("M1012861", "Jai Pradha", programmer));
            employeeRepository.save(new Employee("M1016915", "Amritha Rao II", seniorEngineer));
            employeeRepository.save(new Employee("M1016830", "Shreya Ghoshal", programmer));
            employeeRepository.save(new Employee("M1016755", "Akash Kanthraj", seniorEngineer));
            employeeRepository.save(new Employee("M1016161", "Sneha Yogish", programmer));
            employeeRepository.save(new Employee("M1016355", "Shahrukh Khan", seniorEngineer));
            employeeRepository.save(new Employee("M1016660", "Brad Pitt", programmer));
            employeeRepository.save(new Employee("M1096955", "John Abraham", seniorEngineer));
            employeeRepository.save(new Employee("M1356860", "Abhiram R", programmer));
            employeeRepository.save(new Employee("M1416955", "Raju", seniorEngineer));
            employeeRepository.save(new Employee("M1616860", "Rashmi Ganiger", programmer));
            employeeRepository.save(new Employee("M1516860", "Incredible Hulk", programmer));
            employeeRepository.save(new Employee("M1916955", "Basil Harrison", seniorEngineer));
            employeeRepository.save(new Employee("M1816860", "Madhu Bala", programmer));
            employeeRepository.save(new Employee("M1716955", "Jack Sparrow", seniorEngineer));
            employeeRepository.save(new Employee("M1516955", "Ted Mosby", seniorEngineer));
            employeeRepository.save(new Employee("M1016861", "Marsh Mellow", seniorPM));
            employeeRepository.save(new Employee("M2016955", "Robin Scherbatsky", seniorEngineer));
            employeeRepository.save(new Employee("M3016860", "Vinod", programmer));
            employeeRepository.save(new Employee("M4016955", "Barney Stinson", seniorEngineer));
            employeeRepository.save(new Employee("M5016860", "Megha Karadi", programmer));
        }
    }
}
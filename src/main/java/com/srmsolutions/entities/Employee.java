package com.srmsolutions.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="employee")
public class Employee {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column(name="employee_id")
    private int id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name="emergency_contact_name")
    private String emergencyContactName;
    @Column(name="emergency_contact_number")
    private String emergencyContactNumber;
    @Column
    private String address;
    @Column(name="hire_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;
    @Column(name="termination_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate termDate;
    @Column(name="hours_of_pto")
    private int hoursPto;
    @Column(name="SSN")
    private String ssn;
    @Column(name="manager_id")
    private int managerId;
    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;
    @ManyToOne
    @JoinColumn(name = "job_role_id")
    private JobRole jobRole;
    @Column(name="is_good")
    private boolean isGood;
    @Column(name="is_hr")
    private boolean isHr;
    @Column(name="is_admin")
    private boolean isAdmin;
    @Column(name="has_image", nullable = false)
    private boolean hasImage;
    @Column(name="home_phone")
    private String homePhone;
    @Column(name="mobile_phone")
    private String mobilePhone;
    @Transient
    private String managerName;
}

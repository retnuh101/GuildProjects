package com.srmsolutions.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="employee_of_week")
public class EmployeeOfWeek {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="employee_of_week_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @Column(name = "week_of_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate weekStarting;
    @Column
    private String description;
}

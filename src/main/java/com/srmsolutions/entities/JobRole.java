package com.srmsolutions.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="job_role")

public class JobRole {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="job_role_id")
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column(name = "department_name")
    private String departmentName;
}

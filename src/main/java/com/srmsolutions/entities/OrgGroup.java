package com.srmsolutions.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="org_group")
public class OrgGroup {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "org_group_id")
    private int id;
    @Column(name = "group_name")
    private String name;

    @ManyToMany
    @JoinTable(name = "employee_org_group",
            joinColumns = {@JoinColumn(name = "org_group_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    private List<Employee> employeeList;



}
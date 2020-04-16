package com.srmsolutions.entities;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="event")
public class Event {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="event_id")
    private int id;
    @Column
    private String name;
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;
    //(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToMany
    @JoinTable(name="event_category",
        joinColumns = {@JoinColumn(name="event_id")},
        inverseJoinColumns = {@JoinColumn(name="category_id")})
    private List<Category> categoryList;
    @ManyToMany
    @JoinTable(name="event_employee_invited",
            joinColumns = {@JoinColumn(name="event_id")},
            inverseJoinColumns = {@JoinColumn(name="employee_id")})
    private List<Employee> inviteList;
    @ManyToMany
//    @ManyToMany(mappedBy = "events",
//        cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="event_employee_attending",
            joinColumns = {@JoinColumn(name="event_id")},
            inverseJoinColumns = {@JoinColumn(name="employee_id")})
    private List<Employee> attendingList;
    @Column(name = "start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;
    @Column(name = "end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;
    @Column
    private String description;
    @Column
    private String location;
    @Column(name="is_required")
    private boolean isRequired;
    @Column
    private double latitude;
    @Column
    private double longitude;
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Employee organizer;
    @ManyToOne
    @JoinColumn(name = "organizing_group_id")
    private OrgGroup organizingGroup;
    @Column(name = "is_public")
    private boolean isPublic;
}

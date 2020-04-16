package com.srmsolutions.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="category")
public class Category {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column(name="category_id")
    private int id;
    @Column
    private String name;
    @Column
    private String description;
}

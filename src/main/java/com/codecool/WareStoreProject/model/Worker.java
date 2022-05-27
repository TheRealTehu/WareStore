package com.codecool.WareStoreProject.model;

import com.codecool.WareStoreProject.model.enums.WorkPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="name", length=50, nullable=false, unique=false)
    private String name;
    @Column(name="position", nullable=false, unique=false)
    @Enumerated(EnumType.STRING)
    private WorkPosition position;
    @Column(name="salary", nullable=true, unique=false)
    private double salary;
}

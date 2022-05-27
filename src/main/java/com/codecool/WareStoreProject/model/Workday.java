package com.codecool.WareStoreProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "worker_to_workplace")
public class Workday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "worker_id", referencedColumnName = "id", insertable=false, updatable=false, foreignKey = @ForeignKey(name = "fk_workday_worker"))
    private Worker worker;

    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id", insertable=false, updatable=false, foreignKey = @ForeignKey(name = "fk_workday_warehouse"))
    private Warehouse warehouse;

    @Column(name="date", nullable=false, unique=false)
    private LocalDateTime date;

    @Column(name="hours_worked", nullable=false, unique=false)
    private double hoursWorked;
}

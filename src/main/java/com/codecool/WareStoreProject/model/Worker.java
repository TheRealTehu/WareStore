package com.codecool.WareStoreProject.model;

import com.codecool.WareStoreProject.model.dto.WorkerDTO;
import com.codecool.WareStoreProject.model.enums.WorkPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="name", length=50, nullable=false)
    private String name;
    @Column(name="position", nullable=false)
    private WorkPosition position;
    @Column(name="salary")
    private double salary;

    public Worker(WorkerDTO workerDTO) {
        this.name = workerDTO.getName();
        this.position = workerDTO.getPosition();
        this.salary = workerDTO.getSalary();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return id == worker.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

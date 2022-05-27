package com.codecool.WareStoreProject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", length=30, nullable=false, unique=true)
    private String name;

    @Column(name="address", length=50, nullable=false, unique=true)
    private String address;

    @JsonProperty("storage_space")
    @Column(name="storage_space", nullable=false, unique=false)
    private int storageSpace;

    @JsonProperty("num_of_workers")
    @Column(name="num_of_workers", nullable=false, unique=false)
    private int numOfWorkers;

    @JsonProperty("req_workers")
    @Column(name="req_workers", nullable=false, unique=false)
    private int reqWorkers;

    @JsonProperty("max_workers")
    @Column(name="max_workers", nullable=false, unique=false)
    private int maxWorkers;
}

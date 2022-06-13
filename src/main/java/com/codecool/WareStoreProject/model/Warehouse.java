package com.codecool.WareStoreProject.model;

import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithoutId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", length=30, nullable=false, unique=true)
    private String name;

    @Column(name="address", length=50, nullable=false, unique=true)
    private String address;

    @JsonProperty("storage_space")
    @Column(name="storage_space", nullable=false)
    private int storageSpace;

    @JsonProperty("num_of_workers")
    @Column(name="num_of_workers", nullable=false)
    private int numOfWorkers;

    @JsonProperty("req_workers")
    @Column(name="req_workers", nullable=false)
    private int reqWorkers;

    @JsonProperty("max_workers")
    @Column(name="max_workers", nullable=false)
    private int maxWorkers;

    public Warehouse(WarehouseDTOWithoutId warehouseDTOWithoutId) {
        this.name = warehouseDTOWithoutId.getName();
        this.address = warehouseDTOWithoutId.getAddress();
        this.storageSpace = warehouseDTOWithoutId.getStorageSpace();
        this.numOfWorkers = warehouseDTOWithoutId.getNumOfWorkers();
        this.reqWorkers = warehouseDTOWithoutId.getReqWorkers();
        this.maxWorkers = warehouseDTOWithoutId.getMaxWorkers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return id == warehouse.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

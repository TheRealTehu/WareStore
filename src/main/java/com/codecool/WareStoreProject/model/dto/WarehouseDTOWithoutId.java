package com.codecool.WareStoreProject.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WarehouseDTOWithoutId {
    private String name;
    private String address;
    @JsonProperty("storage_space")
    private int storageSpace;
    @JsonProperty("num_of_workers")
    private int numOfWorkers;
    @JsonProperty("req_workers")
    private int reqWorkers;
    @JsonProperty("max_workers")
    private int maxWorkers;
}

package com.codecool.WareStoreProject.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WarehouseDTOWithNeededWorkers {
    private int id;
    private String name;
    private String address;
    @JsonProperty("storage_space")
    private int storageSpace;
    @JsonProperty("needed_worker_percentage")
    private double neededWorkerPercentage;
    @JsonProperty("needed_workers")
    private int neededWorkers;
    @JsonProperty("num_of_workers")
    private int numOfWorkers;
    @JsonProperty("req_workers")
    private int reqWorkers;
    @JsonProperty("max_workers")
    private int maxWorkers;
}

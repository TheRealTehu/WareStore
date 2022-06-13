package com.codecool.WareStoreProject.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class WarehouseDTOWithoutId {
    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp = "[1-9][0-9]{3}\\s[A-ZÁÉÚÖÓÜŐŰ][a-záéúőóüűö]+\\s[A-ZÁÉÚÖÓÜŐŰ][a-záéúőóüűö]+.*[a-zA-ZÁÉÚÖÓÜŐŰáéúőóüűö]+\\s[a-zA-ZÁÉÚÖÓÜŐŰáéúőóüűö][a-záéúőóüűö]+\\s[0-9]+",
            message = "Correct address format: 9999 Cityname Street Name street 99")
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

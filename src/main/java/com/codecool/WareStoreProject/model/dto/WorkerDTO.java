package com.codecool.WareStoreProject.model.dto;

import com.codecool.WareStoreProject.model.enums.WorkPosition;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class WorkerDTO {
    @Pattern(regexp = "([A-ZÁÉÚŐÓÜŰÖÍ]([a-záéúőóüűöí.]+\\s?)){2,}", message = "Must be a valid name!")
    private String name;
    private WorkPosition position;
    private double salary;
}

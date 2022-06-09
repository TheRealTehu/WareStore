package com.codecool.WareStoreProject.model.dto;

import com.codecool.WareStoreProject.model.enums.WorkPosition;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class WorkerDTO {
    @Pattern(regexp = "[A-Z][a-z]+\\s[A-Z][a-z]+(\\s[A-Z][a-z]*)*", message = "Must be a valid name!")
    private String name;
    private WorkPosition position;
    private double salary;
}

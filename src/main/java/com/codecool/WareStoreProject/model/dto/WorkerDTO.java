package com.codecool.WareStoreProject.model.dto;

import com.codecool.WareStoreProject.model.enums.WorkPosition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkerDTO {
    private String name;
    private WorkPosition position;
    private double salary;
}

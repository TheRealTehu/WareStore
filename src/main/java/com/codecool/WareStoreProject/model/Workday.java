package com.codecool.WareStoreProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Workday {
    private int id;
    private int workerId;
    private int warehouseId;
    private LocalDateTime date;
    private double hoursWorked;
}

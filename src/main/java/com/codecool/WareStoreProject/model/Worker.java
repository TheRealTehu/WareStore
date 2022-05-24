package com.codecool.WareStoreProject.model;

import com.codecool.WareStoreProject.model.enums.WorkPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Worker {
    private int id;
    private String name;
    private WorkPosition position;
    private double salary;
}

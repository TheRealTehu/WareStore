package com.codecool.WareStoreProject.repositry.dao;

import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithNeededWorkers;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithoutId;

import java.util.List;

public interface WarehouseDAO {
    Warehouse addWarehouse(WarehouseDTOWithoutId warehouseDTOWithoutId);

    List<Warehouse> getAllWarehouses();

    Warehouse getWarehouseById(long id);

    Warehouse getWarehouseByName(String name);

    Warehouse getWarehouseByAddress(String address);

    List<WarehouseDTOWithNeededWorkers> listWarehousesByNeededWorkers();

    void updateWarehouseById(long id, WarehouseDTOWithoutId warehouseDTOWithoutId);

    void deleteWarehouseById(long id);
}

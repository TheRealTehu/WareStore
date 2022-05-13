package com.codecool.WareStoreProject.service;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithNeededWorkers;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithoutId;
import com.codecool.WareStoreProject.repositry.dao.WarehouseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    private WarehouseDAO warehouseDAO;
    private ProductService productService;

    @Autowired
    public WarehouseService(WarehouseDAO warehouseDAO, ProductService productService) {
        this.warehouseDAO = warehouseDAO;
        this.productService = productService;
    }

    public Warehouse addWarehouse(WarehouseDTOWithoutId warehouseDTOWithoutId) {
        return warehouseDAO.addWarehouse(warehouseDTOWithoutId);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseDAO.getAllWarehouses();
    }

    public Warehouse getWarehouseById(int id) {
        return warehouseDAO.getWarehouseById(id);
    }

    public Warehouse getWarehouseByName(String name) {
        return warehouseDAO.getWarehouseByName(name);
    }

    public Warehouse getWarehouseByAddress(String address) {
        return warehouseDAO.getWarehouseByAddress(address);
    }

    public List<WarehouseDTOWithNeededWorkers> listWarehousesByNeededWorkers() {
        return warehouseDAO.listWarehousesByNeededWorkers();
    }

    public List<Product> getAllProductsInWarehouse(int warehouseId) {
        return productService.getAllProductsInWarehouse(warehouseId);
    }

    public void updateWarehouseById(int id, WarehouseDTOWithoutId warehouseDTOWithoutId) {
        warehouseDAO.updateWarehouseById(id, warehouseDTOWithoutId);
    }

    public void deleteWarehouseById(int id) {
        warehouseDAO.deleteWarehouseById(id);
    }
}

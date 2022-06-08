package com.codecool.WareStoreProject.service;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithNeededWorkers;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithoutId;
import com.codecool.WareStoreProject.repositry.jpa.WarehouseJPARepository;
import com.codecool.WareStoreProject.repositry.jpa.WarehouseNeededWorkerJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    private WarehouseJPARepository warehouseRepository;
    private WarehouseNeededWorkerJPARepository warehouseNeededWorkerRepository;
    private ProductService productService;

    @Autowired
    public WarehouseService(WarehouseJPARepository warehouseRepository,
                            WarehouseNeededWorkerJPARepository warehouseNeededWorkerRepository,
                            ProductService productService) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseNeededWorkerRepository = warehouseNeededWorkerRepository;
        this.productService = productService;
    }

    public Warehouse addWarehouse(WarehouseDTOWithoutId warehouseDTOWithoutId) {
        return warehouseRepository.save(new Warehouse(warehouseDTOWithoutId));
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll(Sort.by("id"));
    }

    public Warehouse getWarehouseById(long id) {
        if(warehouseRepository.findById(id).isPresent()){
            return warehouseRepository.findById(id).get();
        } else {
            return null;
        }
    }

    public Warehouse getWarehouseByName(String name) {
        return warehouseRepository.findByName(name);
    }

    public Warehouse getWarehouseByAddress(String address) {
        return warehouseRepository.findByAddress(address);
    }

    public List<WarehouseDTOWithNeededWorkers> listWarehousesByNeededWorkers() {
        return warehouseNeededWorkerRepository.listWarehousesByNeededWorkers();
    }

    public List<Product> getAllProductsInWarehouse(long warehouseId) {
        return productService.getAllProductsInWarehouse(warehouseId);
    }

    public void updateWarehouseById(long id, WarehouseDTOWithoutId warehouseDTOWithoutId) {
       warehouseRepository.updateWarehouse(warehouseDTOWithoutId.getName(), warehouseDTOWithoutId.getAddress(),
               warehouseDTOWithoutId.getStorageSpace(), warehouseDTOWithoutId.getNumOfWorkers(),
               warehouseDTOWithoutId.getReqWorkers(), warehouseDTOWithoutId.getMaxWorkers(), id);
    }

    public void deleteWarehouseById(long id) {
        warehouseRepository.deleteById(id);
    }
}

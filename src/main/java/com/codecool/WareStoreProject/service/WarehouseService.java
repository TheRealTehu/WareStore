package com.codecool.WareStoreProject.service;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithNeededWorkers;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithoutId;
import com.codecool.WareStoreProject.repositry.jpa.WarehouseJPARepository;
import com.codecool.WareStoreProject.repositry.jpa.WarehouseNeededWorkerJPARepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseService {
    private final WarehouseJPARepository warehouseRepository;
    private final WarehouseNeededWorkerJPARepository warehouseNeededWorkerRepository;
    private final ProductService productService;
    private final Logger logger = LogManager.getLogger(WarehouseService.class);

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
        if (warehouseRepository.findById(id).isPresent()) {
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
        if(warehouseRepository.findById(warehouseId).isPresent()){
            return productService.getAllProductsInWarehouse(warehouseId);
        } else {
            logger.error("WAREHOUSE NOT FOUND");
            return new ArrayList<>();
        }
    }

    public void updateWarehouseById(long id, WarehouseDTOWithoutId warehouseDTOWithoutId) {
        if(warehouseRepository.findById(id).isPresent()){
            warehouseRepository.updateWarehouse(warehouseDTOWithoutId.getName(), warehouseDTOWithoutId.getAddress(),
                    warehouseDTOWithoutId.getStorageSpace(), warehouseDTOWithoutId.getNumOfWorkers(),
                    warehouseDTOWithoutId.getReqWorkers(), warehouseDTOWithoutId.getMaxWorkers(), id);
        } else {
            logger.error("WAREHOUSE NOT FOUND");
        }
    }

    public void deleteWarehouseById(long id) {
        warehouseRepository.deleteById(id);
    }
}

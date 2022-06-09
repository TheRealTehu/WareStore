package com.codecool.WareStoreProject.controller;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithNeededWorkers;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithoutId;
import com.codecool.WareStoreProject.service.WarehouseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private WarehouseService service;
    private final Logger logger = LogManager.getLogger(WarehouseController.class);

    @Autowired
    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @PostMapping
    public Warehouse addWarehouse(@Valid @RequestBody WarehouseDTOWithoutId warehouseDTOWithoutId, BindingResult br) {
        if (br.hasErrors()) {
            logger.warn("CANNOT ADD WAREHOUSE");
            for (ObjectError error : br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return null;
        } else {
            return service.addWarehouse(warehouseDTOWithoutId);
        }
    }

    @GetMapping
    public List<Warehouse> getAllWarehouses() {
        return service.getAllWarehouses();
    }

    @GetMapping("/id/{id}")
    public Warehouse getWarehouseById(@PathVariable("id") long id) {
        return service.getWarehouseById(id);
    }

    @GetMapping("/name/{name}")
    public Warehouse getWarehouseByName(@PathVariable("name") String name) {
        return service.getWarehouseByName(name);
    }

    @GetMapping("/address/{address}")
    public Warehouse getWarehouseByAddress(@PathVariable("address") String address) {
        return service.getWarehouseByAddress(address);
    }

    @GetMapping("/warehouse/{id}")
    public List<Product> getAllProductsInWarehouse(@PathVariable("id") long warehouseId) {
        return service.getAllProductsInWarehouse(warehouseId);
    }

    @GetMapping("/workers_needed")
    public List<WarehouseDTOWithNeededWorkers> listWarehousesByNeededWorkers() {
        return service.listWarehousesByNeededWorkers();
    }

    @PutMapping("/id/{id}")
    public void updateWarehouseById(@PathVariable("id") long id,
                                    @Valid @RequestBody WarehouseDTOWithoutId warehouseDTOWithoutId, BindingResult br) {
        if (br.hasErrors()) {
            logger.warn("CANNOT UPDATE WAREHOUSE");
            for (ObjectError error: br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
        } else {
            service.updateWarehouseById(id, warehouseDTOWithoutId);
        }
    }

    @DeleteMapping("/id/{id}")
    public void deleteWarehouseById(@PathVariable long id) {
        service.deleteWarehouseById(id);
    }
}

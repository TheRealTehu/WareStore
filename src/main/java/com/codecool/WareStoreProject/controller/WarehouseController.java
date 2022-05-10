package com.codecool.WareStoreProject.controller;

import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithNeededWorkers;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithoutId;
import com.codecool.WareStoreProject.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private WarehouseService service;

    @Autowired
    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @PostMapping
    public Warehouse addWarehouse(@RequestBody WarehouseDTOWithoutId warehouseDTOWithoutId) {
        return service.addWarehouse(warehouseDTOWithoutId);
    }

    @GetMapping
    public List<Warehouse> getAllWarehouses() {
        return service.getAllWarehouses();
    }

    @GetMapping("/id/{id}")
    public Warehouse getWarehouseById(@PathVariable("id") int id) {
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

    @GetMapping("/workers_needed")
    public List<WarehouseDTOWithNeededWorkers> listWarehousesByNeededWorkers() {
        return service.listWarehousesByNeededWorkers();
    }

    @PutMapping("/{id}")
    public void updateWarehouseById(@PathVariable("id") int id, @RequestBody WarehouseDTOWithoutId warehouseDTOWithoutId) {
        service.updateWarehouseById(id, warehouseDTOWithoutId);
    }

    @DeleteMapping("/{id}")
    public void deleteWarehouseById(@PathVariable int id) {
        service.deleteWarehouseById(id);
    }
}

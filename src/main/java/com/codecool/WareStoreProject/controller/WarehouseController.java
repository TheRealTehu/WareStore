package com.codecool.WareStoreProject.controller;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithNeededWorkers;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithoutId;
import com.codecool.WareStoreProject.service.WarehouseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WarehouseService service;
    private final Logger logger = LogManager.getLogger(WarehouseController.class);

    @Autowired
    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Warehouse> addWarehouse(@Valid @RequestBody WarehouseDTOWithoutId warehouseDTOWithoutId, BindingResult br) {
        if (br.hasErrors()) {
            logger.warn("CANNOT ADD WAREHOUSE");
            for (ObjectError error : br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(service.addWarehouse(warehouseDTOWithoutId), HttpStatus.OK);
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
    public ResponseEntity<Warehouse> getWarehouseByAddress(@PathVariable("address") String address) {
        if (validAddress(address)) {
            return new ResponseEntity<>(service.getWarehouseByAddress(address), HttpStatus.OK);
        } else {
            System.err.println("INVALID ADDRESS");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<String> updateWarehouseById(@PathVariable("id") long id,
                                    @Valid @RequestBody WarehouseDTOWithoutId warehouseDTOWithoutId, BindingResult br) {
        if (br.hasErrors()) {
            logger.warn("CANNOT UPDATE WAREHOUSE");
            for (ObjectError error: br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return new ResponseEntity<>("Update failed", HttpStatus.BAD_REQUEST);
        } else {
            service.updateWarehouseById(id, warehouseDTOWithoutId);
            return new ResponseEntity<>("Warehouse updated", HttpStatus.OK);
        }
    }

    @DeleteMapping("/id/{id}")
    public void deleteWarehouseById(@PathVariable long id) {
        service.deleteWarehouseById(id);
    }

    private boolean validAddress(String address) {
        Pattern addressPattern = Pattern.compile(
                "[1-9][0-9]{3}\\s[A-ZÁÉÚŐÓÜÖÍŰ][a-záéúőóüöíű]+\\s[A-ZÁÉÚŐÓÜÖÍŰ][a-záéúőóüöíű]+.*[a-záéúőóüöíűA-ZÁÉÚŐÓÜÖÍŰ]+\\s[a-záéúőóüöíűA-ZÁÉÚŐÓÜÖÍŰ][a-záéúőóüöíű]+\\s[0-9]+");
        Matcher matcher = addressPattern.matcher(address);
        return matcher.matches();
    }
}

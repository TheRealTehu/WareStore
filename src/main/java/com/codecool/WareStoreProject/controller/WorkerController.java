package com.codecool.WareStoreProject.controller;

import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WorkerDTO;
import com.codecool.WareStoreProject.service.WorkerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    private WorkerService service;
    private final Logger logger = LogManager.getLogger(WorkerController.class);

    @Autowired
    public WorkerController(WorkerService service) {
        this.service = service;
    }

    @PostMapping
    public Worker addWorker(@Valid @RequestBody WorkerDTO workerDTO, BindingResult br) {
        if (br.hasErrors()) {
            logger.warn("CANNOT ADD WORKER");
            for (ObjectError error: br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return null;
        } else {
            return service.addWorker(workerDTO);
        }
    }

    @GetMapping
    public List<Worker> listAllWorkers() {
        return service.listAllWorkers();
    }

    @GetMapping("/id/{id}")
    public Worker getWorkerById(@PathVariable("id") long id) {
        return service.getWorkerById(id);
    }

    @GetMapping("/position/{position}")
    public List<Worker> listWorkersInGivenPosition(@PathVariable("position") String position) {
        return service.listWorkersInGivenPosition(position);
    }

    //TODO: refactor to use requestparam
    @GetMapping("/salary/{id}/month/{month}")
    public Double getWorkersSalaryInMonth(@PathVariable("id") long id, @PathVariable("month") String month) {
        return service.getWorkersSalaryInMonth(id, month);
    }

    //TODO: refactor to use requestparam
    @GetMapping("/salary/{id}/start/{start}/end/{end}")
    public Double getWorkersSalaryBetweenDates(@PathVariable("id") long id, @PathVariable("start") String start, @PathVariable("end") String end) {
        return service.getWorkersSalaryBetweenDates(id, start, end);
    }

    //TODO: refactor to use requestparam
    @PostMapping("/work/id/{id}/warehouse/{warehouse_id}/hours/{hours}")
    public void addWorkToWorker(@PathVariable("id") long workerId, @PathVariable("warehouse_id") int warehouseId, @PathVariable("hours") int hoursWorked) {
        service.addWorkToWorker(workerId, warehouseId, hoursWorked);
    }

    @GetMapping("/work/{id}")
    public List<Workday> listAllWorkdaysForWorker(@PathVariable("id") long workerId) {
        return service.listAllWorkdaysForWorker(workerId);
    }

    @PutMapping("/id/{id}")
    public void updateWorkerById(@PathVariable("id") long id, @Valid @RequestBody WorkerDTO workerDTO, BindingResult br) {
        if (br.hasErrors()) {
            logger.warn("CANNOT UPDATE WORKER");
            for (ObjectError error: br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
        } else {
            service.updateWorkerById(id, workerDTO);
        }
    }

    @DeleteMapping("/id/{id}")
    public void deleteWorkerById(@PathVariable("id") long id) {
        service.deleteWorkerById(id);
    }
}

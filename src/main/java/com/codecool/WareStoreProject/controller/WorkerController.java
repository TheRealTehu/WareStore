package com.codecool.WareStoreProject.controller;

import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WorkerDTO;
import com.codecool.WareStoreProject.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    private WorkerService service;

    @Autowired
    public WorkerController(WorkerService service) {
        this.service = service;
    }

    @PostMapping
    public Worker addWorker(@RequestBody WorkerDTO workerDTO) {
        return service.addWorker(workerDTO);
    }

    @GetMapping
    public List<Worker> listAllWorkers() {
        return service.listAllWorkers();
    }

    @GetMapping("/id/{id}")
    public Worker getWorkerById(@PathVariable("id") int id) {
        return service.getWorkerById(id);
    }

    @GetMapping("/position/{position}")
    public List<Worker> listWorkersInGivenPosition(@PathVariable("position") String position) {
        return service.listWorkersInGivenPosition(position);
    }

    //TODO: refactor to use requestparam
    @GetMapping("/salary/{id}/month/{month}")
    public Double getWorkersSalaryInMonth(@PathVariable("id") int id, @PathVariable("month") String month) {
        return service.getWorkersSalaryInMonth(id, month);
    }

    //TODO: refactor to use requestparam
    @GetMapping("/salary/{id}/start/{start}/end/{end}")
    public Double getWorkersSalaryBetweenDates(@PathVariable("id")int id, @PathVariable("start")String start, @PathVariable("end") String end) {
        return service.getWorkersSalaryBetweenDates(id, start, end);
    }

    //TODO: refactor to use requestparam
    @PostMapping("/work/id/{id}/warehouse/{warehouse_id}/hours/{hours}")
    public void addWorkToWorker(@PathVariable("id") int workerId, @PathVariable("warehouse_id") int warehouseId, @PathVariable("hours") int hoursWorked) {
        service.addWorkToWorker(workerId, warehouseId, hoursWorked);
    }

    @GetMapping("/work/{id}")
    public List<Workday> listAllWorkdaysForWorker(@PathVariable("id") int workerId) {
        return service.listAllWorkdaysForWorker(workerId);
    }

    @PutMapping("/id/{id}")
    public void updateWorkerById(@PathVariable("id") int id, @RequestBody WorkerDTO workerDTO) {
        service.updateWorkerById(id, workerDTO);
    }

    @DeleteMapping("/id/{id}")
    public void deleteWorkerById(@PathVariable("id") int id) {
        service.deleteWorkerById(id);
    }
}

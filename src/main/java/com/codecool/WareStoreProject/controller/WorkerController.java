package com.codecool.WareStoreProject.controller;

import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WorkerDTO;
import com.codecool.WareStoreProject.service.WorkerService;
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
@RequestMapping("/worker")
public class WorkerController {
    private final WorkerService service;
    private final Logger logger = LogManager.getLogger(WorkerController.class);
    private final Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}", Pattern.CASE_INSENSITIVE);

    @Autowired
    public WorkerController(WorkerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Worker> addWorker(@Valid @RequestBody WorkerDTO workerDTO, BindingResult br) {
        if (br.hasErrors()) {
            logger.warn("CANNOT ADD WORKER");
            for (ObjectError error: br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(service.addWorker(workerDTO), HttpStatus.OK);
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
    public ResponseEntity<Double> getWorkersSalaryInMonth(@PathVariable("id") long id, @PathVariable("month") String month) {
        Pattern monthPattern = Pattern.compile("[0-9]{2}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = monthPattern.matcher(month);
        if (matcher.matches()) {
            return new ResponseEntity<>(service.getWorkersSalaryInMonth(id, month), HttpStatus.OK);
        } else {
            logger.error("INVALID MONTH FORMAT");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //TODO: refactor to use requestparam
    @GetMapping("/salary/{id}/start/{start}/end/{end}")
    public ResponseEntity<Double> getWorkersSalaryBetweenDates(@PathVariable("id") long id,
                                                               @PathVariable("start") String start,
                                                               @PathVariable("end") String end) {
        Matcher matcher1 = datePattern.matcher(start);
        Matcher matcher2 = datePattern.matcher(end);
        if (matcher1.matches() && matcher2.matches()) {
            return new ResponseEntity<>(service.getWorkersSalaryBetweenDates(id, start, end), HttpStatus.OK);
        } else {
            logger.error("INVALID DATE FORMAT");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<String> updateWorkerById(@PathVariable("id") long id, @Valid @RequestBody WorkerDTO workerDTO, BindingResult br) {
        if (br.hasErrors()) {
            logger.warn("CANNOT UPDATE WORKER");
            for (ObjectError error: br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return new ResponseEntity<>("Update failed", HttpStatus.BAD_REQUEST);
        } else {
            service.updateWorkerById(id, workerDTO);
            return new ResponseEntity<>("Worker updated", HttpStatus.OK);
        }
    }

    @DeleteMapping("/id/{id}")
    public void deleteWorkerById(@PathVariable("id") long id) {
        service.deleteWorkerById(id);
    }
}

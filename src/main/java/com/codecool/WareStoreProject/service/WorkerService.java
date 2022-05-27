package com.codecool.WareStoreProject.service;

import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WorkerDTO;
import com.codecool.WareStoreProject.repositry.dao.WorkerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {
    private WorkerDAO workerDAO;

    @Autowired
    public WorkerService(WorkerDAO workerDAO) {
        this.workerDAO = workerDAO;
    }

    public Worker addWorker(WorkerDTO workerDTO) {
        return workerDAO.addWorker(workerDTO);
    }

    public List<Worker> listAllWorkers() {
        return workerDAO.listAllWorkers();
    }

    public Worker getWorkerById(long id) {
        return workerDAO.getWorkerById(id);
    }

    public List<Worker> listWorkersInGivenPosition(String position) {
        return workerDAO.listWorkersInGivenPosition(position);
    }

    public Double getWorkersSalaryInMonth(long id, String month) {
        return workerDAO.getWorkersSalaryInMonth(id, month);
    }

    public Double getWorkersSalaryBetweenDates(long id, String start, String end) {
        return workerDAO.getWorkersSalaryBetweenDates(id, start, end);
    }

    public void addWorkToWorker(long workerId, long warehouseId, int hoursWorked) {
        workerDAO.addWorkToWorker(workerId, warehouseId, hoursWorked);
    }

    public List<Workday> listAllWorkdaysForWorker(long workerId) {
        return workerDAO.listAllWorkdaysForWorker(workerId);
    }

    public void updateWorkerById(long id, WorkerDTO workerDTO) {
        workerDAO.updateWorkerById(id, workerDTO);
    }

    public void deleteWorkerById(long id) {
        workerDAO.deleteWorkerById(id);
    }
}

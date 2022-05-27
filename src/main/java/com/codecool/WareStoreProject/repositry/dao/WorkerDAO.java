package com.codecool.WareStoreProject.repositry.dao;

import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WorkerDTO;

import java.util.List;

public interface WorkerDAO {
    Worker addWorker(WorkerDTO workerDTO);

    List<Worker> listAllWorkers();

    Worker getWorkerById(long id);

    List<Worker> listWorkersInGivenPosition(String position);

    Double getWorkersSalaryInMonth(long id, String month);

    Double getWorkersSalaryBetweenDates(long id, String start, String end);

    void addWorkToWorker(long workerId, long warehouseId, int hoursWorked);

    List<Workday> listAllWorkdaysForWorker(long workerId);

    void updateWorkerById(long id, WorkerDTO workerDTO);

    void deleteWorkerById(long id);
}

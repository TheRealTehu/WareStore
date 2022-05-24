package com.codecool.WareStoreProject.repositry.dao;

import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WorkerDTO;

import java.util.List;

public interface WorkerDAO {
    Worker addWorker(WorkerDTO workerDTO);

    List<Worker> listAllWorkers();

    Worker getWorkerById(int id);

    List<Worker> listWorkersInGivenPosition(String position);

    Double getWorkersSalaryInMonth(int id, String month);

    Double getWorkersSalaryBetweenDates(int id, String start, String end);

    void addWorkToWorker(int workerId, int warehouseId, int hoursWorked);

    List<Workday> listAllWorkdaysForWorker(int workerId);

    void updateWorkerById(int id, WorkerDTO workerDTO);

    void deleteWorkerById(int id);
}

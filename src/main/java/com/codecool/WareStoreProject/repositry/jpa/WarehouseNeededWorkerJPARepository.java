package com.codecool.WareStoreProject.repositry.jpa;

import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithNeededWorkers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseNeededWorkerJPARepository extends JpaRepository<WarehouseDTOWithNeededWorkers, Long> {
    @Query(value = "SELECT id, name, address, storage_space, " +
            "(((cast(max_workers as decimal) /100) * req_workers) - ((cast(max_workers as decimal) /100) * num_of_workers)) " +
            "AS needed_worker_percentage, (max_workers - num_of_workers) AS needed_workers, " +
            "num_of_workers, req_workers, max_workers " +
            "FROM warehouse ORDER BY needed_worker_percentage DESC;", nativeQuery = true)
    List<WarehouseDTOWithNeededWorkers> listWarehousesByNeededWorkers();
}
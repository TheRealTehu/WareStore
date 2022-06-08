package com.codecool.WareStoreProject.repositry.jpa;

import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.enums.WorkPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WorkerJPARepository extends JpaRepository<Worker, Long> {

    List<Worker> findByPosition(WorkPosition position);

    @Query(value = "SELECT SUM(workday.hoursWorked * worker.salary) FROM Worker worker " +
            "join Workday workday ON worker.id = workday.worker.id " +
            "WHERE worker.id =:id AND workday.date >=:startDate AND workday.date <=:endDate")
    Double getSalaryBetweenDates(@Param("id") long id, @Param("startDate") Timestamp startDate,
                                 @Param("endDate") Timestamp endDate);

    @Query(value = "INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) " +
            "VALUES(?, ?, CAST(? AS timestamp), ?)", nativeQuery = true)
    void addWorkToWorker(long workerId, long warehouseId, Timestamp date, double hoursWorked);

    @Query(value = "SELECT workday FROM Workday workday join Worker worker ON workday.worker.id = worker.id " +
            "where workday.worker =:worker")
    List<Workday> listWorkdaysForWorker(@Param("worker") Worker worker);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Worker SET name =:name, position =:position, salary =:salary where id =:id")
    void updateWorker(@Param("name") String name, @Param("position") WorkPosition position,
                      @Param("salary") double salary, @Param("id") long id);
}

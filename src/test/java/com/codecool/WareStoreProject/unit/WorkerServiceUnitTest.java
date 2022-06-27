package com.codecool.WareStoreProject.unit;

import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WorkerDTO;
import com.codecool.WareStoreProject.model.enums.WorkPosition;
import com.codecool.WareStoreProject.repositry.jpa.WarehouseJPARepository;
import com.codecool.WareStoreProject.repositry.jpa.WorkdayJPARepository;
import com.codecool.WareStoreProject.repositry.jpa.WorkerJPARepository;
import com.codecool.WareStoreProject.service.WorkerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkerServiceUnitTest {
    @InjectMocks
    private WorkerService workerService;

    @Mock
    private WorkerJPARepository mockWorkerRepository;
    @Mock
    private WorkdayJPARepository mockWorkdayJPARepository;
    @Mock
    private WarehouseJPARepository mockWarehouseJPARepository;

    @Test
    public void unitTestListAllWorkers(){
        List<Worker> testList = List.of(new Worker(), new Worker(), new Worker());

        when(mockWorkerRepository.findAll(Sort.by("id"))).thenReturn(testList);

        Assertions.assertEquals(3, workerService.listAllWorkers().size());

        verify(mockWorkerRepository, times(1)).findAll(Sort.by("id"));
    }

    @Test
    public void unitTestAddWorker(){
        WorkerDTO testWorker = new WorkerDTO("Test Name", WorkPosition.IT_WORKER, 4000d);

        workerService.addWorker(testWorker);

        verify(mockWorkerRepository, times(1)).save(new Worker(testWorker));
    }

    @Test
    public void unitTestGetWorkerById(){
        Worker testWorker = new Worker(1L, "Test Name", WorkPosition.IT_WORKER, 4000d);

        when(mockWorkerRepository.findById(1L)).thenReturn(Optional.of(testWorker));

        Assertions.assertEquals(testWorker, workerService.getWorkerById(1L));
    }

    @Test
    public void unitTestListWorkersInGivenPosition(){
        List<Worker> testList = List.of(
                new Worker(1L, "Test Name", WorkPosition.CLERK, 3000),
                new Worker(2L, "Test Name2", WorkPosition.CLERK, 3000),
                new Worker(3L, "Test Name3", WorkPosition.CLERK, 3000)
        );

        when(mockWorkerRepository.findByPosition(WorkPosition.CLERK)).thenReturn(testList);

        Assertions.assertEquals(3, workerService.listWorkersInGivenPosition("CLERK").size());

        verify(mockWorkerRepository, times(1)).findByPosition(WorkPosition.CLERK);
    }

    @Test
    public void unitTestGetSalaryBetweenDates(){
        double testSalary = 300d;

        when(mockWorkerRepository
                .getSalaryBetweenDates(1L, Timestamp.valueOf("2022-05-10 00:00:00"), Timestamp.valueOf("2022-05-20 23:59:59")))
                .thenReturn(testSalary);

        Assertions.assertEquals(testSalary, workerService.getWorkersSalaryBetweenDates(1L, "2022-05-10", "2022-05-20"));

        verify(mockWorkerRepository, times(1))
                .getSalaryBetweenDates(1L, Timestamp.valueOf("2022-05-10 00:00:00"), Timestamp.valueOf("2022-05-20 23:59:59"));
    }

    @Test
    public void unitTestGetWorkersSalaryInMonth(){
        double testSalary = 300d;

        when(mockWorkerRepository
                .getSalaryBetweenDates(1L, Timestamp.valueOf("2022-05-01 00:00:00"), Timestamp.valueOf("2022-05-31 23:59:59")))
                .thenReturn(testSalary);

        Assertions.assertEquals(testSalary, workerService.getWorkersSalaryInMonth(1L, "05"));

        verify(mockWorkerRepository, times(1))
                .getSalaryBetweenDates(1L, Timestamp.valueOf("2022-05-01 00:00:00"), Timestamp.valueOf("2022-05-31 23:59:59"));
    }

    @Test
    public void unitTestAddWorkToWorker(){
        Worker testWorker = new Worker(1L, "Test Name", WorkPosition.IT_WORKER, 4000d);

        Warehouse testWarehouse = new Warehouse(1L, "Test Warehouse", "1234 Test City Test street 01",
                1000, 10, 100, 100);

        Workday testWorkDay = new Workday();
        testWorkDay.setWorker(testWorker);
        testWorkDay.setWarehouse(testWarehouse);
        testWorkDay.setHoursWorked(8d);
        testWorkDay.setDate(Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));

        when(mockWorkerRepository.findById(1L)).thenReturn(Optional.of(testWorker));
        when(mockWarehouseJPARepository.findById(1L)).thenReturn(Optional.of(testWarehouse));

        workerService.addWorkToWorker(1L, 1L, 8d);

        verify(mockWorkdayJPARepository, times(1)).save(testWorkDay);
    }

    @Test
    public void unitTestListAllWorkdaysForWorker(){
        List<Workday> testList = List.of(
                new Workday(),
                new Workday(),
                new Workday()
        );

        Worker testWorker = new Worker(1L, "Test Name", WorkPosition.IT_WORKER, 4000d);

        when(mockWorkerRepository.findById(1L)).thenReturn(Optional.of(testWorker));

        when(mockWorkerRepository.listWorkdaysForWorker(testWorker)).thenReturn(testList);

        Assertions.assertEquals(3, workerService.listAllWorkdaysForWorker(1L).size());

        verify(mockWorkerRepository, times(1)).listWorkdaysForWorker(testWorker);
    }

    @Test
    public void unitTestUpdateWorkerById(){
        WorkerDTO testWorker = new WorkerDTO("Test Name", WorkPosition.IT_WORKER, 4000d);

        when(mockWorkerRepository.findById(1L)).thenReturn(Optional.of(new Worker(testWorker)));

        workerService.updateWorkerById(1L, testWorker);

        verify(mockWorkerRepository, times(1)).updateWorker("Test Name", WorkPosition.IT_WORKER, 4000d, 1L);
    }

    @Test
    public void unitTestDeleteWorkerById(){
        workerService.deleteWorkerById(1L);

        verify(mockWorkerRepository, times(1)).deleteById(1L);
    }
}

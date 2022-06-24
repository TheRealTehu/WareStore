package com.codecool.WareStoreProject.unit;

import com.codecool.WareStoreProject.model.Worker;
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

import java.util.List;

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
}

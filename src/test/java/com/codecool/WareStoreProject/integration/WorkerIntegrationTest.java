package com.codecool.WareStoreProject.integration;

import com.codecool.WareStoreProject.GenericArrayContentEqual;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WorkerDTO;
import com.codecool.WareStoreProject.model.enums.WorkPosition;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WorkerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String workerBaseUrl = "/worker";

    private final SimpleDateFormat formatForStringInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final Worker boss = new Worker(1L, "John Boss", WorkPosition.BOSS, 5000);

    private final Warehouse testWarehouse = new Warehouse(1L, "Center Location",
            "1011 Budapest Imagine Street 405", 1500, 30, 10, 30);

    private final Worker[] databaseWorkers = new Worker[]{
            boss,
            new Worker(2L, "IT Worker", WorkPosition.IT_WORKER, 3000),
            new Worker(3L, "Carol Center", WorkPosition.CENTER_WORKER, 4300),
            new Worker(4L, "Peter Clerkson", WorkPosition.CLERK, 3500),
            new Worker(5L, "Michael Worker", WorkPosition.WAREHOUSE_WORKER, 2000),
            new Worker(6L, "Gordon Worker", WorkPosition.WAREHOUSE_WORKER, 2000),
            new Worker(7L, "Best Worker", WorkPosition.WAREHOUSE_WORKER, 2500)
    };

    private final Workday[] bossWorkdays = new Workday[]{
        new Workday(1L, boss, testWarehouse, getTimestamp("05-10-2022 08:00:00"), 8),
        new Workday(2L, boss, testWarehouse, getTimestamp("05-11-2022 08:00:00"), 8),
        new Workday(3L, boss, testWarehouse, getTimestamp("05-12-2022 08:00:00"), 8)
    };

    private final WorkerDTO testDTO = new WorkerDTO("Teszt Antal", WorkPosition.IT_WORKER, 3000);
    private final Worker expected = new Worker(8L, "Teszt Antal", WorkPosition.IT_WORKER, 3000);

    @Test
    @Order(1)
    void listAllWorkersTest(){
        ResponseEntity<Worker[]> responseEntity = testRestTemplate.getForEntity(workerBaseUrl, Worker[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Assertions.assertTrue(GenericArrayContentEqual.isEqual(databaseWorkers, responseEntity.getBody()));
    }

    @Test
    @Order(2)
    void addWorkerTest(){
        HttpEntity<WorkerDTO> httpEntity = createWorkerDTOHttpEntity(testDTO);

        ResponseEntity<Worker> responseEntity = testRestTemplate.postForEntity(workerBaseUrl, httpEntity, Worker.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(expected, responseEntity.getBody());
    }

    @Test
    @Order(3)
    void getWorkerByIdTest(){
        ResponseEntity<Worker> responseEntity = testRestTemplate.getForEntity(workerBaseUrl + "/id/8", Worker.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(expected, responseEntity.getBody());
    }

    @Test
    @Order(4)
    void listWorkersInGivenPositionTest(){
        ResponseEntity<Worker[]> responseEntity = testRestTemplate.getForEntity(workerBaseUrl + "/position/boss", Worker[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertTrue(GenericArrayContentEqual.isEqual(new Worker[]{boss}, responseEntity.getBody()));
    }

    @Test
    @Order(5)
    void getWorkersSalaryInMonthTest(){
        ResponseEntity<Double> responseEntity = testRestTemplate.getForEntity(workerBaseUrl + "/salary/1/month/05", Double.class);

        Double actual = Arrays.stream(bossWorkdays).mapToDouble(Workday::getHoursWorked).sum() * boss.getSalary();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(actual, responseEntity.getBody());
    }

    @Test
    @Order(6)
    void getWorkersSalaryBetweenDates(){
        ResponseEntity<Double> responseEntity = testRestTemplate.getForEntity(workerBaseUrl + "/salary/1/start/2022-05-09/end/2022-05-14", Double.class);

        Double actual = Arrays.stream(bossWorkdays).mapToDouble(Workday::getHoursWorked).sum() * boss.getSalary();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(actual, responseEntity.getBody());
    }

    @Test
    @Order(7)
    void listAllWorkdaysForWorkerTest(){
        ResponseEntity<Workday[]> responseEntity = testRestTemplate.getForEntity(workerBaseUrl + "/work/1", Workday[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertTrue(GenericArrayContentEqual.isEqual(bossWorkdays, responseEntity.getBody()));
    }

    @Test
    @Order(8)
    void addWorkToWorkerTest(){
        testRestTemplate.postForEntity(workerBaseUrl + "/work/id/8/warehouse/1/hours/8", null, Void.class);

        Workday[] workdays = testRestTemplate.getForEntity(workerBaseUrl + "/work/8", Workday[].class).getBody();

        assertTrue(workdays.length > 0);
    }

    @Test
    @Order(9)
    void updateWorkerByIdTest(){
        WorkerDTO modifierDTO = new WorkerDTO("Modified Antal", WorkPosition.CLERK, 3456);

        testRestTemplate.put(workerBaseUrl + "/id/8", modifierDTO);

        Worker after = testRestTemplate.getForEntity(workerBaseUrl + "/id/8", Worker.class).getBody();

        assertEquals(modifierDTO.getName(), after.getName());
        assertEquals(modifierDTO.getSalary(), after.getSalary());
        assertEquals(modifierDTO.getPosition(), after.getPosition());
    }

    @Test
    @Order(10)
    void deleteWorkerByIdTest(){
        assertTrue(databaseContainsWorkerWithId(8L));

        testRestTemplate.delete(workerBaseUrl + "/id/8");

        assertFalse(databaseContainsWorkerWithId(8L));
    }

    private Boolean databaseContainsWorkerWithId(long id){
        Worker[] workers = testRestTemplate.getForEntity(workerBaseUrl, Worker[].class).getBody();

        long num = Arrays.stream(workers).filter(p -> p.getId() == id).count();
        return num > 0;
    }

    private HttpEntity<WorkerDTO> createWorkerDTOHttpEntity(WorkerDTO workerDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(workerDTO, httpHeaders);
    }

    private Timestamp getTimestamp(String dateTime) {
        try {
            return new Timestamp(formatForStringInput.parse(dateTime).getTime());
        } catch (Exception e) {
            throw new RuntimeException("Cant convert to timestamp: " + e);
        }
    }
}

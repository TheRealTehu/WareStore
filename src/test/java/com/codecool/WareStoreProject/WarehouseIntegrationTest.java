package com.codecool.WareStoreProject;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithoutId;
import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WarehouseIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String warehouseBaseUrl = "/warehouse";
    private final String productBaseUrl = "/product";
    private final String workerBaseUrl = "/worker";
    private final Warehouse[] databaseData = {
            new Warehouse(1L, "Center Location", "1011 Budapest Imagine Street 405",
                    1500, 30, 10, 30),
            new Warehouse(2L, "Store Budakeszi", "1543 Budakeszi Non Exists Street 10",
                    500, 10, 7, 10),
            new Warehouse(3L, "Store Sárpospatak", "3456 Sárpospatak Never Street 7",
                    765, 5, 7, 14)
    };
    private Warehouse testWarehouse = new Warehouse(4L, "Test Name", "1234 Test City Test street 01",
            1000, 10, 100, 100);

    @Test
    @Order(1)
    void getAllWarehousesTest() {

        ResponseEntity<Warehouse[]> responseEntity = testRestTemplate.getForEntity(warehouseBaseUrl, Warehouse[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertTrue(GenericArrayContentEqual.isEqual(databaseData, responseEntity.getBody()));
    }

    @Test
    @Order(2)
    void addWarehouseTest() {
        HttpEntity<Warehouse> httpEntity = createWarehouseHttpEntity(testWarehouse);
        ResponseEntity<Warehouse> responseEntity = testRestTemplate.postForEntity(warehouseBaseUrl, httpEntity, Warehouse.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(testWarehouse, responseEntity.getBody());
    }

    @Test
    @Order(3)
    void getWarehouseByIdTest() {
        ResponseEntity<Warehouse> responseEntity = testRestTemplate.getForEntity(warehouseBaseUrl + "/id/4", Warehouse.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(testWarehouse, responseEntity.getBody());
    }

    @Test
    @Order(4)
    void getWarehouseByNameTest() {
        ResponseEntity<Warehouse> responseEntity = testRestTemplate.getForEntity(warehouseBaseUrl + "/name/Test Name", Warehouse.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(testWarehouse, responseEntity.getBody());
    }

    @Test
    @Order(5)
    void getWarehouseByAddressTest() {
        ResponseEntity<Warehouse> responseEntity = testRestTemplate.getForEntity(warehouseBaseUrl + "/address/1234 Test City Test street 01", Warehouse.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(testWarehouse, responseEntity.getBody());
    }

    @Test
    @Order(6)
    void getAllProductsInWarehouseTest() {
        Product[] expected = {
                new Product(1L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(2L, "Mario", "Jumping videogame", ProductType.GAME, 24999,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(3L, "Doom", "Shooting videogame", ProductType.GAME, 5000,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(4L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(5L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null)
        };

        ResponseEntity<Product[]> responseEntity = testRestTemplate.getForEntity(warehouseBaseUrl + "/warehouse/1", Product[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertTrue(GenericArrayContentEqual.isEqual(expected, responseEntity.getBody()));
    }

    @Test
    @Order(7)
    void listWarehousesByNeededWorkersTest() {
        ResponseEntity<Warehouse[]> responseEntity = testRestTemplate.getForEntity(warehouseBaseUrl + "/workers_needed", Warehouse[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(4L, responseEntity.getBody()[0].getId());
        assertEquals(3L, responseEntity.getBody()[1].getId());
        assertEquals(2L, responseEntity.getBody()[2].getId());
        assertEquals(1L, responseEntity.getBody()[3].getId());
    }

    @Test
    @Order(8)
    void updateWarehouseByIdTest() {
        WarehouseDTOWithoutId modifierDto = new WarehouseDTOWithoutId("Modified Name", "9999 Cityname Street Name street 99",
                9999, 100, 1500, 2000);

        testRestTemplate.put(warehouseBaseUrl + "/id/4", modifierDto);

        Warehouse after = testRestTemplate.getForEntity(warehouseBaseUrl + "/id/4", Warehouse.class).getBody();

        Warehouse expectedWarehouse = new Warehouse(modifierDto);
        expectedWarehouse.setId(4L);

        assertEquals(expectedWarehouse.getId(), after.getId());
        assertEquals(expectedWarehouse.getName(), after.getName());
        assertEquals(expectedWarehouse.getAddress(), after.getAddress());
        assertEquals(expectedWarehouse.getStorageSpace(), after.getStorageSpace());
        assertEquals(expectedWarehouse.getNumOfWorkers(), after.getNumOfWorkers());
        assertEquals(expectedWarehouse.getMaxWorkers(), after.getMaxWorkers());
        assertEquals(expectedWarehouse.getReqWorkers(), after.getReqWorkers());
    }

    @Test
    void deleteWarehouseByIdTest(){
        assertTrue(databaseContainsWarehouseWithId(4L));

        testRestTemplate.delete(warehouseBaseUrl + "/id/4");

        assertFalse(databaseContainsWarehouseWithId(4L));
    }

    private boolean databaseContainsWarehouseWithId(long id) {
        Warehouse[] warehouses = testRestTemplate.getForEntity(warehouseBaseUrl, Warehouse[].class).getBody();
        long num = Arrays.stream(warehouses).filter(w -> w.getId() == id).count();
        return num > 0;
    }

    private HttpEntity<Warehouse> createWarehouseHttpEntity(Warehouse warehouse) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(warehouse, httpHeaders);
    }

    private HttpEntity<Product> createProductHttpEntity(Product product) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(product, httpHeaders);
    }

    private HttpEntity<Worker> createWorkerHttpEntity(Worker worker) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(worker, httpHeaders);
    }

}

package com.codecool.WareStoreProject.integration;

import com.codecool.WareStoreProject.GenericArrayContentEqual;
import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.ProductDTO;
import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String productBaseUrl = "/product";
    private final DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final Product[] databaseData = {
            new Product(1L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                    ProductStatus.IN_STORAGE, null, null, null),
            new Product(2L, "Mario", "Jumping videogame", ProductType.GAME, 24999,
                    ProductStatus.IN_STORAGE, null, null, null),
            new Product(3L, "Doom", "Shooting videogame", ProductType.GAME, 5000,
                    ProductStatus.IN_STORAGE, null, null, null),
            new Product(4L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                    ProductStatus.IN_STORAGE, null, null, null),
            new Product(5L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                    ProductStatus.IN_STORAGE, null, null, null),

            new Product(6L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                    ProductStatus.IN_STORAGE, null, null, null),
            new Product(7L, "PS Controller", "Controller for console", ProductType.GAME, 22000,
                    ProductStatus.IN_STORAGE, null, null, null),
            new Product(8L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                    ProductStatus.IN_STORAGE, null, null, null),
            new Product(9L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                    ProductStatus.IN_STORAGE, null, null, null)
    };

    private final Warehouse testWarehouse = new Warehouse(1L, "Center Location", "1011 Budapest Imagine Street 405",
            1500, 30, 10, 30);
    private final Warehouse testWarehouse2 = new Warehouse(2L, "Store Budakeszi", "1543 Budakeszi Non Exists Street 10",
            500, 10, 7, 10);


    private final Product expected = new Product(10L, "TEST PRODUCT", "This is only for testing",
            ProductType.PART, 9999, ProductStatus.MOVING, testWarehouse, testWarehouse2, getTimestampNow());

    private final ProductDTO testProductDTO = new ProductDTO("TEST PRODUCT", "This is only for testing",
            ProductType.PART, 9999, ProductStatus.MOVING, 1, 2);

    @Test
    @Order(1)
    void getAllProductsTest() {
        ResponseEntity<Product[]> responseEntity = testRestTemplate.getForEntity(productBaseUrl, Product[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Assertions.assertTrue(GenericArrayContentEqual.isEqual(databaseData, responseEntity.getBody()));
    }

    @Test
    @Order(2)
    void addProductTest() {
        HttpEntity<ProductDTO> httpEntity = createProductDTOHttpEntity(testProductDTO);

        ResponseEntity<Product> responseEntity = testRestTemplate.postForEntity(productBaseUrl, httpEntity, Product.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Assertions.assertEquals(expected, responseEntity.getBody());
    }

    @Test
    @Order(3)
    void getProductsByNameTest() {
        ResponseEntity<Product[]> responseEntity = testRestTemplate.getForEntity(productBaseUrl + "/name/TEST PRODUCT", Product[].class);

        Product[] expected = new Product[]{this.expected};

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Assertions.assertTrue(GenericArrayContentEqual.isEqual(expected, responseEntity.getBody()));
    }

    @Test
    @Order(4)
    void getProductsByTypeTest() {
        ResponseEntity<Product[]> responseEntity = testRestTemplate.getForEntity(productBaseUrl + "/type/PART", Product[].class);

        Product[] expected = new Product[]{this.expected};

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Assertions.assertTrue(GenericArrayContentEqual.isEqual(expected, responseEntity.getBody()));
    }

    @Test
    @Order(5)
    void getProductByStatusTest() {
        ResponseEntity<Product[]> responseEntity = testRestTemplate.getForEntity(productBaseUrl + "/status/MOVING", Product[].class);

        Product[] expected = new Product[]{this.expected};

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Assertions.assertTrue(GenericArrayContentEqual.isEqual(expected, responseEntity.getBody()));
    }

    @Test
    @Order(6)
    void getProductsByLastModificationDateTest() {
        ResponseEntity<Product[]> responseEntity = testRestTemplate.getForEntity(productBaseUrl + "/date/2022-12-31", Product[].class);

        Product[] expected = new Product[]{new Product(5L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                ProductStatus.IN_STORAGE, null, null, null)};

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Assertions.assertTrue(GenericArrayContentEqual.isEqual(expected, responseEntity.getBody()));
    }

    @Test
    @Order(7)
    void getProductByIdTest(){
        ResponseEntity<Product> responseEntity = testRestTemplate.getForEntity(productBaseUrl + "/id/10", Product.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(expected, responseEntity.getBody());
    }

    @Test
    @Order(8)
    void updateProductTest(){
        ProductDTO modifierDTO = new ProductDTO("NEW NAME", "NEW DESCRIPTION", ProductType.GAME, 11111,
                ProductStatus.RESERVED, 1L,2L);

        testRestTemplate.put(productBaseUrl + "/id/10", modifierDTO);

        Product after = testRestTemplate.getForEntity(productBaseUrl + "/id/10", Product.class).getBody();

        assertEquals(modifierDTO.getName(), after.getName());
        assertEquals(modifierDTO.getDescription(), after.getDescription());
        assertEquals(modifierDTO.getProductType(), after.getProductType());
        assertEquals(modifierDTO.getPrice(), after.getPrice());
        assertEquals(modifierDTO.getStatus(), after.getStatus());
        assertEquals(modifierDTO.getWarehouseId(), after.getWarehouse().getId());
        assertEquals(modifierDTO.getDestinationId(), after.getDestination().getId());
    }

    @Test
    @Order(9)
    void deleteProductTest(){
        assertTrue(databaseContainsProductWithId(10L));

        testRestTemplate.delete(productBaseUrl + "/id/10");

        assertFalse(databaseContainsProductWithId(10L));
    }

    private Boolean databaseContainsProductWithId(long id){
        Product[] products = testRestTemplate.getForEntity(productBaseUrl, Product[].class).getBody();

        long num = Arrays.stream(products).filter(p -> p.getId() == id).count();
        return num > 0;
    }

    private HttpEntity<ProductDTO> createProductDTOHttpEntity(ProductDTO productDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(productDTO, httpHeaders);
    }

    private Timestamp getTimestampNow() {
        try {
            return Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(format).replace('T', ' '));
        } catch (Exception e) {
            throw new RuntimeException("Cant get current time: " + e.getMessage());
        }
    }
}

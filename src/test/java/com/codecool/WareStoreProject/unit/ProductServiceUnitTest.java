package com.codecool.WareStoreProject.unit;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.ProductDTO;
import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import com.codecool.WareStoreProject.repositry.jpa.ProductJPARepository;
import com.codecool.WareStoreProject.repositry.jpa.WarehouseJPARepository;
import com.codecool.WareStoreProject.service.ProductService;
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
public class ProductServiceUnitTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductJPARepository mockProductRepository;
    @Mock
    private WarehouseJPARepository mockWarehouseRepository;

    @Test
    public void unitTestGetAllProducts(){
        List<Product> testList = List.of(new Product(), new Product(), new Product());

        when(mockProductRepository.findAll(Sort.by("id"))).thenReturn(testList);

        Assertions.assertEquals(3, productService.getAllProducts().size());

        verify(mockProductRepository, times(1)).findAll(Sort.by("id"));
    }

    @Test
    public void unitTestAddProduct(){
        ProductDTO testProduct = new ProductDTO("Fifa", "Football videogame", ProductType.GAME, 22000,
                ProductStatus.IN_STORAGE, 1L, 1L);

        Warehouse testWarehouse = new Warehouse(1L, "Test Warehouse", "1234 Test City Test street 01",
                1000, 10, 100, 100);

        when(mockWarehouseRepository.findById(1L)).thenReturn(Optional.of(testWarehouse));

        productService.addProduct(testProduct);

        verify(mockProductRepository, times(1)).save(new Product(testProduct, testWarehouse, testWarehouse));
    }

    @Test
    public void unitTestGetAllProductsInWarehouse(){
        List<Product> testList = List.of(new Product(), new Product(), new Product());

        when(mockProductRepository.findByWarehouse(1L)).thenReturn(testList);

        Assertions.assertEquals(3, productService.getAllProductsInWarehouse(1L).size());

        verify(mockProductRepository, times(1)).findByWarehouse(1L);
    }

    @Test
    public void unitTestGetProductsByName(){
        List<Product> testList = List.of(
                new Product(1L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(2L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(3L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null)
        );

        when(mockProductRepository.findByName("Fifa")).thenReturn(testList);

        Assertions.assertEquals(3, productService.getProductsByName("Fifa").size());

        verify(mockProductRepository, times(1)).findByName("Fifa");
    }

    @Test
    public void unitTestGetProductByType(){
        List<Product> testList = List.of(
                new Product(1L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(2L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(3L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null)
        );

        when(mockProductRepository.findByProductType(ProductType.GAME)).thenReturn(testList);

        Assertions.assertEquals(3, productService.getProductByType("GAME").size());

        verify(mockProductRepository, times(1)).findByProductType(ProductType.GAME);
    }

    @Test
    public void unitTestGetProductByStatus(){
        List<Product> testList = List.of(
                new Product(1L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(2L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(3L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null)
        );

        when(mockProductRepository.findByStatus(ProductStatus.IN_STORAGE)).thenReturn(testList);

        Assertions.assertEquals(3, productService.getProductByStatus("IN_STORAGE").size());

        verify(mockProductRepository, times(1)).findByStatus(ProductStatus.IN_STORAGE);
    }

    @Test
    public void unitTestGetProductByModificationDate(){
        List<Product> testList = List.of(
                new Product(1L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(2L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null),
                new Product(3L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                        ProductStatus.IN_STORAGE, null, null, null)
        );

        when(mockProductRepository.findByLastModified(Timestamp.valueOf("2022-05-10 00:00:00"), Timestamp.valueOf("2022-05-10 23:59:59")))
                .thenReturn(testList);

        Assertions.assertEquals(3, productService.getProductByModificationDate("2022-05-10").size());

        verify(mockProductRepository, times(1))
                .findByLastModified(Timestamp.valueOf("2022-05-10 00:00:00"), Timestamp.valueOf("2022-05-10 23:59:59"));
    }

    @Test
    public void unitTestGetProductById(){
        Product testProduct = new Product(1L, "Fifa", "Football videogame", ProductType.GAME, 22000,
                ProductStatus.IN_STORAGE, null, null, null);

        when(mockProductRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        Assertions.assertEquals(testProduct, productService.getProductById(1L));
    }

    @Test
    public void unitTestUpdateProductById(){
        ProductDTO testProductDTO = new ProductDTO("Fifa", "Football videogame", ProductType.GAME, 22000,
                ProductStatus.IN_STORAGE, 1L, 1L);

        Warehouse testWarehouse = new Warehouse(1L, "Test Warehouse", "1234 Test City Test street 01",
                1000, 10, 100, 100);

        when(mockProductRepository.existsById(1L)).thenReturn(true);
        when(mockWarehouseRepository.findById(1L)).thenReturn(Optional.of(testWarehouse));

        productService.updateProductById(1L, testProductDTO);

        verify(mockProductRepository, times(1))
                .updateProduct("Fifa", "Football videogame", ProductType.GAME, 22000, ProductStatus.IN_STORAGE,
                        testWarehouse, testWarehouse, Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)), 1L);
    }

    @Test
    public void unitTestDeleteProductById(){
        productService.deleteProductById(1L);

        verify(mockProductRepository, times(1)).deleteById(1L);
    }
}

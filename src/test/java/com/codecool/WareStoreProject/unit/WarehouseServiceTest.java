package com.codecool.WareStoreProject.unit;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithoutId;
import com.codecool.WareStoreProject.repositry.jpa.WarehouseJPARepository;
import com.codecool.WareStoreProject.service.ProductService;
import com.codecool.WareStoreProject.service.WarehouseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

   @InjectMocks
   WarehouseService warehouseService;

   @Mock
   WarehouseJPARepository mockWarehouseRepository;
   @Mock
   ProductService mockProductService;

   @Test
   public void unitTestGetAllWarehouses(){
      List<Warehouse> testList = List.of(new Warehouse(), new Warehouse(), new Warehouse());

      when(mockWarehouseRepository.findAll(Sort.by("id"))).thenReturn(testList);

      Assertions.assertEquals(3, warehouseService.getAllWarehouses().size());
      verify(mockWarehouseRepository, times(1)).findAll(Sort.by("id"));
   }

   @Test
   public void unitTestAddWarehouse(){
      WarehouseDTOWithoutId testWarehouse = new WarehouseDTOWithoutId("Test Warehouse",
              "1234 Test City Test street 01", 1000, 10, 100, 100);

      warehouseService.addWarehouse(testWarehouse);

      verify(mockWarehouseRepository, times(1)).save(new Warehouse(testWarehouse));
   }

   @Test
   public void unitTestGetWarehouseById(){
      Warehouse testWarehouse = new Warehouse(1L, "Test Warehouse", "1234 Test City Test street 01",
              1000, 10, 100, 100);

      when(mockWarehouseRepository.findById(1L)).thenReturn(Optional.of(testWarehouse));
      //when(mockWarehouseRepository.findById(1L).isPresent()).thenReturn(true);

      Assertions.assertEquals(testWarehouse, warehouseService.getWarehouseById(1L));
   }

   @Test
   public void unitTestGetWarehouseByName(){
      Warehouse testWarehouse = new Warehouse(1L, "Test Warehouse", "1234 Test City Test street 01",
              1000, 10, 100, 100);

      when(mockWarehouseRepository.findByName("Test Warehouse")).thenReturn(testWarehouse);

      Assertions.assertEquals(testWarehouse, warehouseService.getWarehouseByName("Test Warehouse"));

      verify(mockWarehouseRepository, times(1)).findByName("Test Warehouse");
   }

   @Test
   public void unitTestGetWarehouseByAddress(){
      Warehouse testWarehouse = new Warehouse(1L, "Test Warehouse", "1234 Test City Test street 01",
              1000, 10, 100, 100);

      when(mockWarehouseRepository.findByAddress("1234 Test City Test street 01")).thenReturn(testWarehouse);

      Assertions.assertEquals(testWarehouse, warehouseService.getWarehouseByAddress("1234 Test City Test street 01"));

      verify(mockWarehouseRepository, times(1)).findByAddress("1234 Test City Test street 01");
   }

   @Test
   public void unitTestGetAllProductsInWarehouse(){
      Warehouse testWarehouse = new Warehouse(1L, "Test Warehouse", "1234 Test City Test street 01",
              1000, 10, 100, 100);

      List<Product> testList = List.of(new Product(), new Product(), new Product());

      when(mockWarehouseRepository.findById(1L)).thenReturn(Optional.of(testWarehouse));
      when(mockProductService.getAllProductsInWarehouse(1L)).thenReturn(testList);

      Assertions.assertEquals(3, warehouseService.getAllProductsInWarehouse(1L).size());
   }

   @Test
   public void unitTestUpdateWarehouseById(){
      WarehouseDTOWithoutId testWarehouse = new WarehouseDTOWithoutId("Test Warehouse",
              "1234 Test City Test street 01", 1000, 10, 100, 100);

      when(mockWarehouseRepository.findById(1L)).thenReturn(Optional.of(new Warehouse(testWarehouse)));

      warehouseService.updateWarehouseById(1L, testWarehouse);

      verify(mockWarehouseRepository, times(1)).updateWarehouse("Test Warehouse",
              "1234 Test City Test street 01", 1000, 10, 100, 100, 1L);
   }

}

package com.codecool.WareStoreProject.repositry.dao;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.dto.ProductDTO;
import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductDAO {
    Product addProduct(ProductDTO productDTO);

    List<Product> getAllProducts();

    List<Product> getAllProductsInWarehouse(int warehouseId);

    List<Product> getProductsByName(String name);

    List<Product> getProductByType(String type);

    List<Product> getProductByStatus(String status);

    List<Product> getProductByModificationDate(String date);

    Product getProductById(int id);

    void updateProductById(int id, ProductDTO productDTO);

    void deleteProductById(int id);
}

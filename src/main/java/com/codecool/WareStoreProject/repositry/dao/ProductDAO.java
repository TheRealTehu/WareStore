package com.codecool.WareStoreProject.repositry.dao;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.dto.ProductDTO;

import java.util.List;

public interface ProductDAO {
    Product addProduct(ProductDTO productDTO);

    List<Product> getAllProducts();

    List<Product> getAllProductsInWarehouse(long warehouseId);

    List<Product> getProductsByName(String name);

    List<Product> getProductByType(String type);

    List<Product> getProductByStatus(String status);

    List<Product> getProductByModificationDate(String date);

    Product getProductById(long id);

    boolean sendProductToWarehouse(long productId, long warehouseId);

    boolean receiveProduct(long id);

    void updateProductById(long id, ProductDTO productDTO);

    void deleteProductById(long id);
}

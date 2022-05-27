package com.codecool.WareStoreProject.service;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.dto.ProductDTO;
import com.codecool.WareStoreProject.repositry.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductService {
    private ProductDAO productDAO;

    @Autowired
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Product addProduct(ProductDTO productDTO) {
        return productDAO.addProduct(productDTO);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public List<Product> getAllProductsInWarehouse(long warehouseId) {
        return productDAO.getAllProductsInWarehouse(warehouseId);
    }

    public List<Product> getProductsByName(String name) {
        return productDAO.getProductsByName(name);
    }

    public List<Product> getProductByType(String type) {
        return productDAO.getProductByType(type.toLowerCase());
    }

    public List<Product> getProductByStatus(String status) {
        return productDAO.getProductByStatus(status.toLowerCase());
    }

    public List<Product> getProductByModificationDate(String date) {
        final Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(date);

        if(matcher.matches()){
            return productDAO.getProductByModificationDate(date);
        }
        System.err.println("INVALID DATE FORMAT");
        return null;
    }

    public Product getProductById(long id) {
        return productDAO.getProductById(id);
    }

    public void updateProductById(long id, ProductDTO productDTO) {
        productDAO.updateProductById(id, productDTO);
    }

    public void deleteProductById(long id) {
        productDAO.deleteProductById(id);
    }
}

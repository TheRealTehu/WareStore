package com.codecool.WareStoreProject.controller;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.dto.ProductDTO;
import com.codecool.WareStoreProject.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product addProduct(@RequestBody ProductDTO productDTO) {
        return service.addProduct(productDTO);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/name/{name}")
    public List<Product> getProductsByName(@PathVariable("name") String name) {
        return service.getProductsByName(name);
    }

    @GetMapping("/type/{type}")
    public List<Product> getProductByType(@PathVariable("type") String type) {
        return service.getProductByType(type);
    }

    @GetMapping("/status/{status}")
    public List<Product> getProductByStatus(@PathVariable("status") String status) {
        return service.getProductByStatus(status);
    }

    @GetMapping("/date/{date}")
    public List<Product> getProductByModificationDate(@PathVariable("date") String date) {
        return service.getProductByModificationDate(date);
    }

    @GetMapping("/id/{id}")
    public Product getProductById(@PathVariable("id") int id) {
        return service.getProductById(id);
    }

    @PutMapping("/id/{id}")
    public void updateProductById(@PathVariable("id") int id, @RequestBody ProductDTO productDTO) {
        service.updateProductById(id, productDTO);
    }

    @DeleteMapping("/id/{id}")
    public void deleteProductById(@PathVariable("id") int id) {
        service.deleteProductById(id);
    }
}

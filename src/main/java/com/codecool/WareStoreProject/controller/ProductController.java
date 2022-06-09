package com.codecool.WareStoreProject.controller;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.dto.ProductDTO;
import com.codecool.WareStoreProject.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService service;
    private final Logger logger = LogManager.getLogger(ProductController.class);

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product addProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult br) {
        if(br.hasErrors()){
            logger.warn("CANNOT ADD PRODUCT");
            for (ObjectError error: br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return null;
        } else {
            return service.addProduct(productDTO);
        }
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
    public Product getProductById(@PathVariable("id") long id) {
        return service.getProductById(id);
    }

    @PutMapping("/id/{id}")
    public void updateProductById(@PathVariable("id") long id, @Valid @RequestBody ProductDTO productDTO,
                                  BindingResult br) {
        if(br.hasErrors()){
            logger.warn("CANNOT UPDATE PRODUCT");
            for (ObjectError error: br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
        } else {
            service.updateProductById(id, productDTO);
        }
    }

    @DeleteMapping("/id/{id}")
    public void deleteProductById(@PathVariable("id") long id) {
        service.deleteProductById(id);
    }
}

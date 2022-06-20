package com.codecool.WareStoreProject.controller;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.dto.ProductDTO;
import com.codecool.WareStoreProject.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService service;
    private final Logger logger = LogManager.getLogger(ProductController.class);

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult br) {
        if(br.hasErrors()){
            logger.warn("CANNOT ADD PRODUCT");
            for (ObjectError error: br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(service.addProduct(productDTO), HttpStatus.OK);
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
    public ResponseEntity<List<Product>> getProductByModificationDate(@PathVariable("date") String date) {
        final Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(date);

        if (matcher.matches()) {
            return new ResponseEntity<>(service.getProductByModificationDate(date), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/id/{id}")
    public Product getProductById(@PathVariable("id") long id) {
        return service.getProductById(id);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<String> updateProductById(@PathVariable("id") long id, @Valid @RequestBody ProductDTO productDTO,
                                  BindingResult br) {
        if(br.hasErrors()){
            logger.warn("CANNOT UPDATE PRODUCT");
            for (ObjectError error: br.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return new ResponseEntity<>("Update failed", HttpStatus.BAD_REQUEST);
        } else {
            service.updateProductById(id, productDTO);
            return new ResponseEntity<>("Product updated!", HttpStatus.OK);
        }
    }

    @DeleteMapping("/id/{id}")
    public void deleteProductById(@PathVariable("id") long id) {
        service.deleteProductById(id);
    }
}

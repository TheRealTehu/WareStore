package com.codecool.WareStoreProject.service;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.ProductDTO;
import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import com.codecool.WareStoreProject.repositry.jpa.ProductJPARepository;
import com.codecool.WareStoreProject.repositry.jpa.WarehouseJPARepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ProductService {
    private final ProductJPARepository productRepository;
    private final WarehouseJPARepository warehouseRepository;
    private final SimpleDateFormat formatForStringInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter formatForNow = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductJPARepository productRepository, WarehouseJPARepository warehouseRepository) {
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Product addProduct(ProductDTO productDTO) {
        Warehouse warehouse, destination;

        if(warehouseRepository.findById(productDTO.getWarehouseId()).isPresent() &&
                warehouseRepository.findById(productDTO.getDestinationId()).isPresent()){
            warehouse = warehouseRepository.findById(productDTO.getWarehouseId()).get();
            destination = warehouseRepository.findById(productDTO.getDestinationId()).get();
        } else {
            logger.error("WAREHOUSE OR DESTINATION NOT FOUND");
            return null;
        }
        Product product = new Product(productDTO, warehouse, destination);
        product.setLastModified(getTimestampNow());

        return productRepository.save(product);
    }

    private Timestamp getTimestampNow() {
        try {
            return Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(formatForNow)
                    .replace('T', ' '));
        } catch (Exception e) {
            throw new RuntimeException("Cant get current time: " + e.getMessage());
        }
    }

    private Timestamp getTimestamp(String dateTime) {
        try {
            return new Timestamp(formatForStringInput.parse(dateTime).getTime());
        } catch (Exception e) {
            throw new RuntimeException("Cant convert to timestamp: " + e);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by("id"));
    }

    public List<Product> getAllProductsInWarehouse(long warehouseId) {
        return productRepository.findByWarehouse(warehouseId);
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> getProductByType(String type) {
        return productRepository.findByProductType(ProductType.valueOf(type.toUpperCase()));
    }

    public List<Product> getProductByStatus(String status) {
        return productRepository.findByStatus(ProductStatus.valueOf(status.toUpperCase()));
    }

    public List<Product> getProductByModificationDate(String date) {
            String date_start = date + " 00:00:00";
            String date_end = date + " 23:59:59";

            return productRepository.findByLastModified(getTimestamp(date_start), getTimestamp(date_end));
    }

    public Product getProductById(long id) {
        if (productRepository.findById(id).isPresent()) {
            return productRepository.findById(id).get();
        } else
            logger.error("NO PRODUCT FOUND FOR GIVEN ID");
        return null;
    }

    public void updateProductById(long id, ProductDTO productDTO) {
        if (productRepository.existsById(id)
                && warehouseRepository.findById(productDTO.getWarehouseId()).isPresent()
                && warehouseRepository.findById(productDTO.getDestinationId()).isPresent()) {
            productRepository.updateProduct(productDTO.getName(), productDTO.getDescription(), productDTO.getProductType(),
                    productDTO.getPrice(), productDTO.getStatus(), warehouseRepository.findById(productDTO.getWarehouseId()).get(),
                    warehouseRepository.findById(productDTO.getDestinationId()).get(), getTimestampNow(), id);
        } else {
            logger.error("PRODUCT OR WAREHOUSES NOT FOUND");
        }
    }

    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }
}

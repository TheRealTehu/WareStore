package com.codecool.WareStoreProject.model.dto;

import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    private ProductType productType;
    private int price;
    private ProductStatus status;
    private long warehouseId;
    private long destinationId;
}

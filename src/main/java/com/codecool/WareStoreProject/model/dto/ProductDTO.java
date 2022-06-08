package com.codecool.WareStoreProject.model.dto;

import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    @JsonProperty("product_type")
    private ProductType productType;
    private int price;
    private ProductStatus status;
    @JsonProperty("warehouse_id")
    private long warehouseId;
    @JsonProperty("destination_id")
    private long destinationId;
}

package com.codecool.WareStoreProject.model;

import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    @JsonProperty("product_type")
    private ProductType productType;
    private int price;
    private ProductStatus status;
    @JsonProperty("warehouse_id")
    private int warehouseId;
    @JsonProperty("destination_id")
    private int destinationId;
    @JsonProperty("last_modified")
    private LocalDateTime lastModified;
}

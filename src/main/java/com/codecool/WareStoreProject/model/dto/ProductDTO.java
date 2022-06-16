package com.codecool.WareStoreProject.model.dto;

import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ProductDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @JsonProperty("product_type")
    private ProductType productType;
    @Min(value = 0, message = "CANNOT BE NEGATIVE")
    private int price;
    private ProductStatus status;
    @JsonProperty("warehouse_id")
    private long warehouseId;
    @JsonProperty("destination_id")
    private long destinationId;
}
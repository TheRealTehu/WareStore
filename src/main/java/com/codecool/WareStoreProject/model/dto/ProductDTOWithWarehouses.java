package com.codecool.WareStoreProject.model.dto;

import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDTOWithWarehouses {
    private String name;
    private String description;
    @JsonProperty("product_type")
    private ProductType productType;
    private int price;
    private ProductStatus status;
    @JsonProperty("warehouse_id")
    private Warehouse warehouse;
    @JsonProperty("destination_id")
    private Warehouse destination;

    public ProductDTOWithWarehouses(ProductDTO productDTO, Warehouse warehouse, Warehouse destination) {
        this.name = productDTO.getName();
        this.description = productDTO.getDescription();
        this.productType = productDTO.getProductType();
        this.price = productDTO.getPrice();
        this.status = productDTO.getStatus();
        this.warehouse = warehouse;
        this.destination = destination;
    }
}

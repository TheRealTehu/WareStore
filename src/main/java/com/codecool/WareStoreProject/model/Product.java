package com.codecool.WareStoreProject.model;

import com.codecool.WareStoreProject.model.dto.ProductDTO;
import com.codecool.WareStoreProject.model.dto.ProductDTOWithWarehouses;
import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", length=100, nullable=false, unique=false)
    private String name;

    @Column(name="description", length=500, nullable=false, unique=false)
    private String description;

    @JsonProperty("product_type")
    @Column(name="product_type", nullable=false, unique=false)
    private ProductType productType;

    @Column(name="price", nullable=false, unique=false)
    private int price;

    @Column(name="status", nullable=false, unique=false)
    private ProductStatus status;

    @JsonProperty("warehouse_id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id", insertable=true, updatable=true, foreignKey = @ForeignKey(name = "fk_product_warehouse"))
    private Warehouse warehouse;

    @JsonProperty("destination_id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_id", referencedColumnName = "id", insertable=true, updatable=true, foreignKey = @ForeignKey(name = "fk_product_destination"))
    private Warehouse destination;

    @JsonProperty("last_modified")
    @Column(name="last_modified", nullable=true, unique=false)
    private Timestamp lastModified;

    public Product(ProductDTO productDTO, Warehouse warehouse, Warehouse destination) {
        this.name = productDTO.getName();
        this.description = productDTO.getDescription();
        this.productType = productDTO.getProductType();
        this.price = productDTO.getPrice();
        this.status = productDTO.getStatus();
        this.warehouse = warehouse;
        this.destination = destination;
    }
}

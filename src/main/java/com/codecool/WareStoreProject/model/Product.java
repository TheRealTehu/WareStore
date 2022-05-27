package com.codecool.WareStoreProject.model;

import com.codecool.WareStoreProject.model.enums.EnumToPostgresEnum;
import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@TypeDef(
        name = "pgsql_enum",
        typeClass = EnumToPostgresEnum.class
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", length=100, nullable=false, unique=false)
    private String name;

    @Column(name="description", length=500, nullable=false, unique=false)
    private String description;

    @JsonProperty("product_type")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    @Column(name="product_type", nullable=false, unique=false)
    private ProductType productType;

    @Column(name="price", nullable=false, unique=false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false, unique=false)
    @Type(type = "pgsql_enum")
    private ProductStatus status;

    @JsonProperty("warehouse_id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id", insertable=false, updatable=false, foreignKey = @ForeignKey(name = "fk_product_warehouse"))
    private Warehouse warehouse;

    @JsonProperty("destination_id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_id", referencedColumnName = "id", insertable=false, updatable=false, foreignKey = @ForeignKey(name = "fk_product_destination"))
    private Warehouse destination;

    @JsonProperty("last_modified")
    @Column(name="last_modified", nullable=true, unique=false)
    private LocalDateTime lastModified;
}

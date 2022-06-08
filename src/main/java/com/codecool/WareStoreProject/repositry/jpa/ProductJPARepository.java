package com.codecool.WareStoreProject.repositry.jpa;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ProductJPARepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p WHERE p.warehouse.id =:id ORDER BY p.id")
    List<Product> findByWarehouse(@Param("id") long id);

    @Query(value = "SELECT p FROM Product p WHERE p.name =:name ORDER BY p.warehouse.id")
    List<Product> findByName(@Param("name") String name);

    @Query(value = "SELECT p FROM Product p WHERE p.productType =:type ORDER BY p.warehouse.id")
    List<Product> findByProductType(@Param("type") ProductType type);

    @Query(value = "SELECT p FROM Product p WHERE p.status =:status ORDER BY p.warehouse.id")
    List<Product> findByStatus(@Param("status") ProductStatus status);

    @Query(value = "SELECT p FROM Product p WHERE p.lastModified >:startDate AND p.lastModified <:endDate ORDER BY p.lastModified")
    List<Product> findByLastModified(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Product SET name =:name, description =:description, productType =:productType, price =:price, " +
            "status =:status, warehouse =:warehouse, destination =:destination, lastModified =:lastModified WHERE id =:id")
    void updateProduct(@Param("name") String name, @Param("description") String description,
                       @Param("productType") ProductType productType, @Param("price") int price,
                       @Param("status") ProductStatus status, @Param("warehouse") Warehouse warehouse,
                       @Param("destination") Warehouse destination, @Param("lastModified") Timestamp lastModified,
                       @Param("id") long id);
}

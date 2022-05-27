package com.codecool.WareStoreProject.repositry.jpa;

import com.codecool.WareStoreProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJPARepository extends JpaRepository<Product, Long> {
}

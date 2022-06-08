package com.codecool.WareStoreProject.repositry.jpa;

import com.codecool.WareStoreProject.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WarehouseJPARepository extends JpaRepository<Warehouse, Long> {

    Warehouse findByName(String name);

    Warehouse findByAddress(String address);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Warehouse SET name =:name, address =:address, storageSpace =:storageSpace, " +
            "numOfWorkers =:numOfWorkers, reqWorkers =:reqWorkers, maxWorkers =:maxWorkers WHERE id =:id")
    void updateWarehouse(@Param("name") String name, @Param("address") String address,
                         @Param("storageSpace") int storageSpace, @Param("numOfWorkers") int numOfWorkers,
                         @Param("reqWorkers") int reqWorkers, @Param("maxWorkers") int maxWorkers, @Param("id") long id);
}

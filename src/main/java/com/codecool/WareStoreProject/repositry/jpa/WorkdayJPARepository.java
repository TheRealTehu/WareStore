package com.codecool.WareStoreProject.repositry.jpa;

import com.codecool.WareStoreProject.model.Workday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkdayJPARepository extends JpaRepository<Workday, Long>{
}

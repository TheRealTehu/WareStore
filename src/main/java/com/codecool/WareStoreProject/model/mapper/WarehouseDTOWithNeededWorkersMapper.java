package com.codecool.WareStoreProject.model.mapper;

import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithNeededWorkers;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WarehouseDTOWithNeededWorkersMapper implements RowMapper<WarehouseDTOWithNeededWorkers> {
    @Override
    public WarehouseDTOWithNeededWorkers mapRow(ResultSet rs, int rowNum) throws SQLException {
        WarehouseDTOWithNeededWorkers warehouseDTOWithNeededWorkers = new WarehouseDTOWithNeededWorkers();
        warehouseDTOWithNeededWorkers.setId(rs.getLong("id"));
        warehouseDTOWithNeededWorkers.setName(rs.getString("name"));
        warehouseDTOWithNeededWorkers.setAddress(rs.getString("address"));
        warehouseDTOWithNeededWorkers.setStorageSpace(rs.getInt("storage_space"));
        warehouseDTOWithNeededWorkers.setNeededWorkers(rs.getInt("needed_workers"));
        warehouseDTOWithNeededWorkers.setNeededWorkerPercentage(rs.getDouble("needed_worker_percentage"));
        warehouseDTOWithNeededWorkers.setNumOfWorkers(rs.getInt("num_of_workers"));
        warehouseDTOWithNeededWorkers.setReqWorkers(rs.getInt("req_workers"));
        warehouseDTOWithNeededWorkers.setMaxWorkers(rs.getInt("max_workers"));

        return warehouseDTOWithNeededWorkers;
    }
}

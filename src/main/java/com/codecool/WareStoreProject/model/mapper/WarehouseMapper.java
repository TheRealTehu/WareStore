package com.codecool.WareStoreProject.model.mapper;

import com.codecool.WareStoreProject.model.Warehouse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WarehouseMapper implements RowMapper<Warehouse> {
    @Override
    public Warehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(rs.getLong("id"));
        warehouse.setName(rs.getString("name"));
        warehouse.setAddress(rs.getString("address"));
        warehouse.setStorageSpace(rs.getInt("storage_space"));
        warehouse.setNumOfWorkers(rs.getInt("num_of_workers"));
        warehouse.setReqWorkers(rs.getInt("req_workers"));
        warehouse.setMaxWorkers(rs.getInt("max_workers"));

        return warehouse;
    }
}

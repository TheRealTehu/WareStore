package com.codecool.WareStoreProject.model.mapper;

import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.enums.WorkPosition;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WorkerMapper implements RowMapper<Worker> {
    @Override
    public Worker mapRow(ResultSet rs, int rowNum) throws SQLException {
        Worker worker = new Worker();

        worker.setId(rs.getLong("id"));
        worker.setName(rs.getString("name"));
        worker.setPosition(WorkPosition.valueOf(rs.getString("position").toUpperCase()));
        worker.setSalary(rs.getDouble("salary"));

        return worker;
    }
}

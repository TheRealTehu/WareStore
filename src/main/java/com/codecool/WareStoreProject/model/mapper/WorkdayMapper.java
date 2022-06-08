package com.codecool.WareStoreProject.model.mapper;

import com.codecool.WareStoreProject.model.Workday;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WorkdayMapper implements RowMapper<Workday> {
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Workday mapRow(ResultSet rs, int rowNum) throws SQLException {
       /*Workday workday = new Workday();
        workday.setId(rs.getLong("id"));
        //workday.setWorkerId(rs.getLong("worker_id"));
        //workday.setWarehouseId(rs.getLong("warehouse_id"));
        workday.setDate(LocalDateTime.parse(rs.getString("date"), format));
        workday.setHoursWorked(rs.getDouble("hours_worked"));

        return workday;*/
        return null;
    }
}

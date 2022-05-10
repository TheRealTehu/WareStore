package com.codecool.WareStoreProject.repositry.dao;

import com.codecool.WareStoreProject.model.Warehouse;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithNeededWorkers;
import com.codecool.WareStoreProject.model.dto.WarehouseDTOWithoutId;
import com.codecool.WareStoreProject.model.mapper.WarehouseDTOWithNeededWorkersMapper;
import com.codecool.WareStoreProject.model.mapper.WarehouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class WarehouseJdbcDAO implements WarehouseDAO {
    private JdbcTemplate template;
    private WarehouseMapper warehouseMapper;
    private WarehouseDTOWithNeededWorkersMapper warehouseDTOWithNeededWorkersMapper;

    @Autowired
    public WarehouseJdbcDAO(JdbcTemplate template, WarehouseMapper warehouseMapper,
                            WarehouseDTOWithNeededWorkersMapper warehouseDTOWithNeededWorkersMapper) {
        this.template = template;
        this.warehouseMapper = warehouseMapper;
        this.warehouseDTOWithNeededWorkersMapper = warehouseDTOWithNeededWorkersMapper;
    }

    @Override
    public Warehouse addWarehouse(WarehouseDTOWithoutId warehouseDTOWithoutId) {
        final String SQL = "INSERT INTO warehouse(name, address, storage_space, num_of_workers, req_workers, max_workers" +
                "VALUES(?, ?, ?, ?, ?, ?);";

        KeyHolder holder = new GeneratedKeyHolder();

        template.update(getPreparedStatementForAddingWarehouse(SQL, warehouseDTOWithoutId), holder);

        return getWarehouseById((int) holder.getKeys().get("id"));
    }

    private PreparedStatementCreator getPreparedStatementForAddingWarehouse(String sql, WarehouseDTOWithoutId warehouseDTOWithoutId) {
        return conn -> {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, warehouseDTOWithoutId.getName());
            statement.setString(2, warehouseDTOWithoutId.getAddress());
            statement.setInt(3, warehouseDTOWithoutId.getStorageSpace());
            statement.setInt(4, warehouseDTOWithoutId.getNumOfWorkers());
            statement.setInt(5, warehouseDTOWithoutId.getReqWorkers());
            statement.setInt(6, warehouseDTOWithoutId.getMaxWorkers());

            return statement;
        };
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        final String SQL = "SELECT id, name, address, storage_space, num_of_workers, req_workers, max_workers " +
                "FROM warehouse;";

        return template.query(SQL, warehouseMapper);
    }

    @Override
    public Warehouse getWarehouseById(int id) {
        final String SQL = "SELECT id, name, address, storage_space, num_of_workers, req_workers, max_workers " +
                "FROM warehouse WHERE id = ?;";

        return template.queryForObject(SQL, warehouseMapper, id);
    }

    @Override
    public Warehouse getWarehouseByName(String name) {
        final String SQL = "SELECT id, name, address, storage_space, num_of_workers, req_workers, max_workers " +
                "FROM warehouse WHERE name = ?;";

        return template.queryForObject(SQL, warehouseMapper, name);
    }

    @Override
    public Warehouse getWarehouseByAddress(String address) {
        final String SQL = "SELECT id, name, address, storage_space, num_of_workers, req_workers, max_workers " +
                "FROM warehouse WHERE address = ?;";

        return template.queryForObject(SQL, warehouseMapper, address);
    }

    @Override
    public List<WarehouseDTOWithNeededWorkers> listWarehousesByNeededWorkers() {
        final String SQL = "SELECT id, name, address, storage_space, num_of_workers, req_workers, max_workers, " +
                "(max_workers - num_of_workers) AS needed_workers, "+
                "(((max_workers::decimal /100) * req_workers) - ((max_workers::decimal /100) * num_of_workers)) " +
                "AS needed_worker_percentage " +
                "FROM warehouse ORDER BY needed_worker_percentage DESC;"; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        return template.query(SQL, warehouseDTOWithNeededWorkersMapper);
    }

    @Override
    public void updateWarehouseById(int id, WarehouseDTOWithoutId warehouseDTOWithoutId) {
        final String SQL = "UPDATE warehouse " +
                "SET name = ?, address = ?, storage_space = ?, num_of_workers = ?, req_workers = ?, max_workers = ? " +
                "WHERE id = ?;";

        Object[] args = new Object[]{warehouseDTOWithoutId.getName(), warehouseDTOWithoutId.getAddress(), warehouseDTOWithoutId.getStorageSpace(),
        warehouseDTOWithoutId.getNumOfWorkers(), warehouseDTOWithoutId.getReqWorkers(), warehouseDTOWithoutId.getMaxWorkers(), id};

        template.update(SQL, args);
    }

    @Override
    public void deleteWarehouseById(int id) {
        final String SQL = "DELETE FROM warehouse WHERE id = ?;";

        template.update(SQL, id);
    }
}
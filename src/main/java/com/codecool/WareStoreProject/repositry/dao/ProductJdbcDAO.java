package com.codecool.WareStoreProject.repositry.dao;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.dto.ProductDTO;
import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ProductJdbcDAO implements ProductDAO {
    private JdbcTemplate template;
    private ProductMapper mapper;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public ProductJdbcDAO(JdbcTemplate template, ProductMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    @Override
    public Product addProduct(ProductDTO productDTO) {
        final String SQL = "INSERT INTO product(name, description, product_type, price, status, warehouse_id, " +
                "destination_id, last_modified) VALUES (?, ?, ?::product_types, ?, ?::product_status, ?, ?, ?::timestamp);";

        KeyHolder holder = new GeneratedKeyHolder();

        template.update(getPreparedStatementForAddingProduct(SQL, productDTO), holder);

        //return getProductById((int)holder.getKeys().get("id"));

        if (holder.getKeys() == null){
            System.out.println("EJJJJJ");
        }
            //TODO: Figure out why getKeys() is null
        return null;
    }

    private PreparedStatementCreator getPreparedStatementForAddingProduct(String sql, ProductDTO productDTO) {
        return conn -> {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, productDTO.getName());
            statement.setString(2, productDTO.getDescription());
            statement.setString(3, productDTO.getProductType().name().toLowerCase());
            statement.setInt(4, productDTO.getPrice());
            statement.setString(5, productDTO.getStatus().name().toLowerCase());
            statement.setInt(6, productDTO.getWarehouseId());
            statement.setInt(7, productDTO.getDestinationId());
            statement.setString(8, LocalDateTime.now().format(format));

            return statement;
        };
    }

    @Override
    public List<Product> getAllProducts() {
        final String SQL = "SELECT id, name, description, product_type, price, status, warehouse_id, destination_id, " +
                " last_modified FROM product ORDER BY id ASC;";
        return template.query(SQL, mapper);
    }

    @Override
    public List<Product> getAllProductsInWarehouse(int warehouseId) {
        final String SQL = "SELECT id, name, description, product_type, price, status, warehouse_id, destination_id, " +
                " last_modified FROM product WHERE warehouse_id = ? ORDER BY id ASC;";
        return template.query(SQL, mapper, warehouseId);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        final String SQL = "SELECT id, name, description, product_type, price, status, warehouse_id, destination_id, " +
                " last_modified FROM product WHERE name = ? ORDER BY warehouse_id ASC;";
        return template.query(SQL, mapper, name);
    }

    @Override
    public List<Product> getProductByType(String type) {
        final String SQL = "SELECT id, name, description, product_type, price, status, warehouse_id, destination_id, " +
                " last_modified FROM product WHERE product_type = ?::product_types ORDER BY warehouse_id ASC;";
        return template.query(SQL, mapper, type);
    }

    @Override
    public List<Product> getProductByStatus(String status) {
        final String SQL = "SELECT id, name, description, product_type, price, status, warehouse_id, destination_id, " +
                " last_modified FROM product WHERE status = ?::product_status ORDER BY warehouse_id ASC;";
        return template.query(SQL, mapper, status);
    }

    @Override
    public List<Product> getProductByModificationDate(String date) {
        final String SQL = "SELECT id, name, description, product_type, price, status, warehouse_id, destination_id, " +
                " last_modified FROM product WHERE last_modified >= ?::timestamp AND last_modified <= ?::timestamp " +
                "ORDER BY last_modified ASC;";

        String date_start = date + " 00:00:00";
        String date_end = date + " 23:59:59";

        return template.query(SQL, mapper, date_start, date_end);
    }

    @Override
    public Product getProductById(int id) {
        final String SQL = "SELECT id, name, description, product_type, price, status, warehouse_id, destination_id, " +
                " last_modified FROM product WHERE id = ?;";
        return template.queryForObject(SQL, mapper, id);
    }

    @Override
    public boolean sendProductToWarehouse(int productId, int warehouseId) {
        if (notSendable(productId, warehouseId)) return false;

        final String SQL = "UPDATE product SET status = ?, destination_id = ?, last_modified = ? WHERE id = ?;";

        template.update(SQL, ProductStatus.MOVING.name().toLowerCase(), warehouseId,
                LocalDateTime.now().format(format), productId);

        return true;
    }

    private boolean notSendable(int productId, int warehouseId) {
        Product productToSend = getProductById(productId);

        if(productToSend.getWarehouseId() == warehouseId || !(productToSend.getStatus().equals(ProductStatus.IN_STORAGE))){
            return true;
        }
        return false;
    }

    @Override
    public boolean receiveProduct(int id) {
        Product product = getProductById(id);
        if(!product.getStatus().equals(ProductStatus.MOVING)){
            return false;
        }

        final String SQL = "UPDATE product SET status = ?, warehouse_id = ?, last_modified = ? WHERE id = ?;";

        template.update(SQL, ProductStatus.IN_STORAGE.name().toLowerCase(), product.getDestinationId(),
                LocalDateTime.now().format(format), id);

        return true;
    }

    @Override
    public void updateProductById(int id, ProductDTO productDTO) {
        final String SQL = "UPDATE product SET name = ?, description = ?, product_type = ?::product_types, price = ?, " +
                "status = ?::product_status, warehouse_id = ?, destination_id = ?, last_modified = ?::timestamp WHERE id = ?;";

        Object[] args = new Object[]{productDTO.getName(), productDTO.getDescription(), productDTO.getProductType().name().toLowerCase(),
        productDTO.getPrice(), productDTO.getStatus().name().toLowerCase(), productDTO.getWarehouseId(), productDTO.getDestinationId(),
        LocalDateTime.now().format(format), id};

        template.update(SQL, args);
    }

    @Override
    public void deleteProductById(int id) {
        final String SQL = "DELETE FROM product WHERE id = ?;";

        template.update(SQL, id);
    }
}

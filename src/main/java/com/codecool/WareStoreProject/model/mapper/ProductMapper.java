package com.codecool.WareStoreProject.model.mapper;

import com.codecool.WareStoreProject.model.Product;
import com.codecool.WareStoreProject.model.enums.ProductStatus;
import com.codecool.WareStoreProject.model.enums.ProductType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@Component
public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setProductType(ProductType.valueOf(rs.getString("product_type").toUpperCase()));
        product.setPrice(rs.getInt("price"));
        product.setStatus(ProductStatus.valueOf(rs.getString("status").toUpperCase()));
        product.setWarehouseId(rs.getInt("warehouse_id"));
        product.setDestinationId((rs.getInt("destination_id")));

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        product.setLastModified(LocalDateTime.parse(rs.getString("last_modified"), format));

        return product;
    }
}

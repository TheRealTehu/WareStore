package com.codecool.WareStoreProject.model.enums.enumconverter;

import com.codecool.WareStoreProject.model.enums.ProductStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProductStatusConverter implements AttributeConverter<ProductStatus, String> {
    @Override
    public String convertToDatabaseColumn(ProductStatus status) {
        return status.getName();
    }

    @Override
    public ProductStatus convertToEntityAttribute(String name) {
        return ProductStatus.productStatusFromName(name);
    }
}

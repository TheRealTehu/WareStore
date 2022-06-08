package com.codecool.WareStoreProject.model.enums.enumconverter;

import com.codecool.WareStoreProject.model.enums.ProductType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProductTypeConverter implements AttributeConverter<ProductType, String> {
    @Override
    public String convertToDatabaseColumn(ProductType type) {
        return type.getName();
    }

    @Override
    public ProductType convertToEntityAttribute(String name) {
        return ProductType.productTypeFromName(name);
    }
}

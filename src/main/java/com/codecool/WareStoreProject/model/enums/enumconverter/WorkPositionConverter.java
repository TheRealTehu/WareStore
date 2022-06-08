package com.codecool.WareStoreProject.model.enums.enumconverter;

import com.codecool.WareStoreProject.model.enums.WorkPosition;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class WorkPositionConverter implements AttributeConverter<WorkPosition, String> {
    @Override
    public String convertToDatabaseColumn(WorkPosition position) {
        return position.getName();
    }

    @Override
    public WorkPosition convertToEntityAttribute(String name) {
        return WorkPosition.workPositionFromName(name);
    }
}

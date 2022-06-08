package com.codecool.WareStoreProject.model.enums;

public enum ProductStatus {
    IN_STORAGE("in_storage"), RESERVED("reserved"), MOVING("moving"), SOLD("sold");

    private String name;

    ProductStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ProductStatus productStatusFromName(String name){
        switch (name){
            case "in_storage": return ProductStatus.IN_STORAGE;
            case "reserved": return ProductStatus.RESERVED;
            case "moving": return ProductStatus.MOVING;
            case "sold": return ProductStatus.SOLD;
            default: throw new IllegalArgumentException("No product status found");
        }
    }
}

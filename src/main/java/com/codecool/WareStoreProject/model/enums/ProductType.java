package com.codecool.WareStoreProject.model.enums;

public enum ProductType {
    GAME("game"), CONSOLE("console"), ACCESSORY("accessory"), PART("part"), OTHER("other");

    private String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static ProductType productTypeFromName(String name){
        switch (name){
            case "game": return ProductType.GAME;
            case "console": return ProductType.CONSOLE;
            case "accessory": return ProductType.ACCESSORY;
            case "part": return ProductType.PART;
            default: return ProductType.OTHER;
        }
    }
}
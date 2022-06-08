package com.codecool.WareStoreProject.model.enums;

public enum WorkPosition {
    WAREHOUSE_WORKER("warehouse_worker"), CLERK("clerk"), CENTER_WORKER("center_worker"),
    IT_WORKER("it_worker"), BOSS("boss");

    private String name;

    WorkPosition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static WorkPosition workPositionFromName(String name){
        switch (name){
            case "warehouse_worker": return WorkPosition.WAREHOUSE_WORKER;
            case "clerk": return WorkPosition.CLERK;
            case "center_worker": return WorkPosition.CENTER_WORKER;
            case "it_worker": return WorkPosition.IT_WORKER;
            case "boss": return WorkPosition.BOSS;
            default: throw new IllegalArgumentException("No work position found");
        }
    }
}

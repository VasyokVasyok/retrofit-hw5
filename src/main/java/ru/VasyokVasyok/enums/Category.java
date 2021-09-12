package ru.VasyokVasyok.enums;

public enum Category {
    FOOD(1, "Food"),
    ELECTRONIC(2, "Electronic"),
    FURNITURE(3, "Furniture");

    private Integer id;
    private String name;

    Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

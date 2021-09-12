package ru.VasyokVasyok.enums;

public enum Product {
    PRODUCT_100PRICE("One", 1000, "Food"),
    PRODUCT_MAXINT_PRICE("two", 2147483647, "Furniture");

    private String title;
    private Integer price;
    private String categoryTitle;

    Product(String title, Integer price, String categoryTitle) {
        this.title = title;
        this.price = price;
        this.categoryTitle = categoryTitle;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }
}

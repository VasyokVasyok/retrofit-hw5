package ru.VasyokVasyok.dto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Category {
    private Integer id;
    private String title;
    private List<Product> products = new ArrayList<>();
    private Integer status;
    private String message;

    public Category(Integer id, String title, List<Product> products, Integer status, String message) {
        this.id = id;
        this.title = title;
        this.products = products;
        this.status = status;
        this.message = message;
    }
}

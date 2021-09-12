package ru.VasyokVasyok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer Id;
    private String title;
    private Integer price;
    private String categoryTitle;
}
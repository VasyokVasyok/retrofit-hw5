package ru.VasyokVasyok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class InvalidRequest {
    private Integer status;
    private String message;
}

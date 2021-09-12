package ru.VasyokVasyok.enums;

public enum InvalidData {
    ZERO_NUMBER(0, 404, "Unable to find category with id: 0"),
    NONEXISTENT_CATEGORY(4, 404, "Unable to find category with id: 4"),
    NONEXISTENT_PRODUCT(1, 500, ""),
    ZERO_NUMBER_PR(0, 500, "");


    private Integer invalidId;
    private Integer status;
    private String message;

    InvalidData(Integer invalidId, Integer status, String message) {
        this.invalidId = invalidId;
        this.status = status;
        this.message = message;
    }

    public Integer getInvalidId() {
        return invalidId;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

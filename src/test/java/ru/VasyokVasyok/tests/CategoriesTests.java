package ru.VasyokVasyok.tests;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import retrofit2.Response;
import ru.VasyokVasyok.dto.Category;
import ru.VasyokVasyok.dto.InvalidRequest;
import ru.VasyokVasyok.enums.InvalidData;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesTests extends BaseTest {

    //Позитивная проверка. Запрос разных существующих категорий.
    @ParameterizedTest
    @EnumSource(value = ru.VasyokVasyok.enums.Category.class)
    void getFoodCategoryTest(ru.VasyokVasyok.enums.Category category) throws IOException {
        Response<Category> response = categoryService
                .getCategory(category.getId())
                .execute();
        assertThat(response.body().getTitle()).isEqualTo(category.getName());
        response
                .body()
                .getProducts()
                .forEach(e -> assertThat(e.getCategoryTitle()).isEqualTo(category.getName()));
    }

    private void makeRequest(InvalidData invalidCategory) throws IOException {
        Response<InvalidRequest> response = invalidRequestsService
                .getInvalidRequests(invalidCategory.getInvalidId())
                .execute();
        String errBody = response.errorBody().string();
        System.out.println(errBody);
        String statusStr = errBody.substring(10, 13);
        String messageStr = errBody.substring(25, 59);
        assertThat(statusStr).isEqualTo(invalidCategory.getStatus().toString());
        assertThat(messageStr).isEqualTo(invalidCategory.getMessage());
    }

    //Негативная проверка. Запрос несуществующей категории.
    @ParameterizedTest
    @EnumSource(value = ru.VasyokVasyok.enums.InvalidData.class, names = {"NONEXISTENT_CATEGORY"})
    void getNonexistentCategory(ru.VasyokVasyok.enums.InvalidData invalidCategory) throws IOException {
        makeRequest(invalidCategory);
    }

    //Негативная проверка. Запрос категории с id = 0.
    @ParameterizedTest
    @EnumSource(value = ru.VasyokVasyok.enums.InvalidData.class, names = {"ZERO_NUMBER"})
    void getZeroIdCategory(ru.VasyokVasyok.enums.InvalidData invalidCategory) throws IOException {
        makeRequest(invalidCategory);
    }

    //Негативная проверка. Запрос категории с id = null.
    @ParameterizedTest
    @NullSource
    void getNullIdCategory(ru.VasyokVasyok.enums.InvalidData invalidCategory) throws IOException {
        try {
            makeRequest(invalidCategory);
        } catch (NullPointerException e) {
            System.out.println("That's good.");
        }
    }

    //Негативная проверка. Запрос категории с пустым id.
    //В ответе написано, что неподдерживаемый тип.
    //Как проверить пустой id??
    /*@ParameterizedTest
    @EmptySource
    void getEmptyIdCategory(ru.VasyokVasyok.enums.InvalidData invalidCategory) {
        try {
            makeRequest(invalidCategory);
        } catch (NullPointerException | IOException e) {
            System.out.println("That's true.");
        }
    }*/
}

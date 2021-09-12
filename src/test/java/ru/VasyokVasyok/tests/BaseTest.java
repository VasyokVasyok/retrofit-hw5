package ru.VasyokVasyok.tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import retrofit2.Retrofit;
import ru.VasyokVasyok.service.CategoryService;
import ru.VasyokVasyok.service.InvalidRequestsService;
import ru.VasyokVasyok.service.ProductService;
import ru.VasyokVasyok.utils.RetrofitUtils;

public class BaseTest {

    static Retrofit retrofit;
    static InvalidRequestsService invalidRequestsService;
    static CategoryService categoryService;
    static ProductService productService;
    static Faker faker;

    @BeforeAll
    static void beforeAll() {
        retrofit = RetrofitUtils.getRetrofit();
        invalidRequestsService = retrofit.create(InvalidRequestsService.class);
        categoryService = retrofit.create(CategoryService.class);
        productService = retrofit.create(ProductService.class);
        faker = new Faker();
    }
}

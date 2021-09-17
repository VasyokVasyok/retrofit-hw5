package ru.VasyokVasyok.tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import retrofit2.Retrofit;
import ru.VasyokVasyok.db.dao.CategoriesMapper;
import ru.VasyokVasyok.db.dao.ProductsMapper;
import ru.VasyokVasyok.db.model.Categories;
import ru.VasyokVasyok.db.model.CategoriesExample;
import ru.VasyokVasyok.db.model.ProductsExample;
import ru.VasyokVasyok.service.CategoryService;
import ru.VasyokVasyok.service.InvalidRequestsService;
import ru.VasyokVasyok.service.ProductService;
import ru.VasyokVasyok.utils.DbUtils;
import ru.VasyokVasyok.utils.RetrofitUtils;

public class BaseTest {

    static Retrofit retrofit;
    static InvalidRequestsService invalidRequestsService;
    static CategoryService categoryService;
    static ProductService productService;
    static Faker faker;
    static ProductsMapper productsMapper;
    static CategoriesMapper categoriesMapper;
    static Categories testCategory;

    @BeforeAll
    static void beforeAll() {
        retrofit = RetrofitUtils.getRetrofit();
        invalidRequestsService = retrofit.create(InvalidRequestsService.class);
        categoryService = retrofit.create(CategoryService.class);
        productService = retrofit.create(ProductService.class);
        faker = new Faker();
        productsMapper = DbUtils.getProductsMapper();
        categoriesMapper = DbUtils.getCategoryMapper();
        String quote = faker.backToTheFuture().quote();
        testCategory = DbUtils.getNewTestCategory(quote);

    }

    @AfterAll
    static void afterAll() {
        ProductsExample example = new ProductsExample();
        example.createCriteria().andCategory_idEqualTo(Long.valueOf(testCategory.getId()));
        productsMapper.deleteByExample(example);
        categoriesMapper.deleteByPrimaryKey(testCategory.getId());
    }
}

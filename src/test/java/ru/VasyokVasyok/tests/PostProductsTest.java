package ru.VasyokVasyok.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import retrofit2.Response;
import ru.VasyokVasyok.db.model.Products;
import ru.VasyokVasyok.db.model.ProductsExample;
import ru.VasyokVasyok.dto.Product;
import ru.VasyokVasyok.enums.Category;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostProductsTest extends BaseTest {
    Product product;
    Integer id;

    void setUp() {
        product = new Product()
                .withTitle(faker.food().dish())
                .withCategoryTitle(testCategory.getTitle())
                .withPrice(1000);
    }

    @ParameterizedTest
    @EnumSource(value = ru.VasyokVasyok.enums.Product.class)
    void createProductWithDifferentData(ru.VasyokVasyok.enums.Product prod) throws IOException {
        product = new Product()
                .withTitle(prod.getTitle())
                .withPrice(prod.getPrice())
                .withCategoryTitle(prod.getCategoryTitle());
        Response<Product> response = productService
                .createProduct(product)
                .execute();
        id = response.body().getId();
        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getId()).isNotNull();
        ProductsExample example = new ProductsExample();
        example.createCriteria().andCategory_idEqualTo(Long.valueOf(testCategory.getId())).andPriceEqualTo(product.getPrice());
        Products productFromDb = productsMapper.selectByExample(example).get(0);
        assertThat(productFromDb.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    void createProductWithPriceTest() throws IOException {
        Response<Product> response = productService
                .createProduct(product)
                .execute();
        id = response.body().getId();
        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getId()).isNotNull();
        ProductsExample example = new ProductsExample();
        example.createCriteria().andCategory_idEqualTo(Long.valueOf(testCategory.getId())).andTitleEqualTo(product.getTitle());
        Products productFromDb = productsMapper.selectByExample(example).get(0);
        assertThat(productFromDb.getTitle()).isEqualTo(product.getTitle());
    }

    @AfterEach
    void tearDown() throws IOException {
        productService.deleteProduct(id).execute();
    }
}

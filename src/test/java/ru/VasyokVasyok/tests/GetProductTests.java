package ru.VasyokVasyok.tests;

import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.VasyokVasyok.db.model.Products;
import ru.VasyokVasyok.db.model.ProductsExample;
import ru.VasyokVasyok.dto.Product;
import ru.VasyokVasyok.enums.Category;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GetProductTests extends BaseTest {
    Integer id;
    Product product;

    Integer createProd() throws IOException {
        product = new Product()
                .withTitle(faker.food().dish())
                .withCategoryTitle(Category.FOOD.getName())
                .withPrice(1000);
        Response<Product> response = productService
                .createProduct(product)
                .execute();
        id = response.body().getId();
        return id;
    }

    //Позитивный кейс. Запрос существующего продукта.
    @Test
    void getExistProduct() throws IOException {
        Integer id = createProd();
        Response<Product> response = productService
                .getProduct(id)
                .execute();
        assertThat(response.body().getId()).isEqualTo(id);
        ProductsExample example = new ProductsExample();
        example.createCriteria().andCategory_idEqualTo(Long.valueOf(testCategory.getId()));
        Products productFromDb = productsMapper.selectByExample(example).get(0);
        assertThat(productFromDb.getId()).isEqualTo(product.getId());
    }
}

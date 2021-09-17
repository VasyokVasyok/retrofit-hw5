package ru.VasyokVasyok.tests;

import okhttp3.ResponseBody;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import retrofit2.Response;
import ru.VasyokVasyok.db.model.Products;
import ru.VasyokVasyok.db.model.ProductsExample;
import ru.VasyokVasyok.dto.Product;
import ru.VasyokVasyok.enums.Category;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteProductTests extends BaseTest {
    Integer id;
    Product product;

    Integer createProduct() throws IOException {
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

    //Позитивный кейс. Удаление существующего продукта.
    @Test
    void deleteExistentProduct() throws IOException {
        try {
            productService.deleteProduct(createProduct()).execute();
        } catch (IOException e) {
            System.out.println("Error occurred.");
        }
        ProductsExample example = new ProductsExample();
        example.createCriteria().andCategory_idEqualTo(Long.valueOf(testCategory.getId())).andTitleEqualTo(product.getTitle());
        Products productFromDb = productsMapper.selectByExample(example).get(0);
        Assertions.assertThat(productFromDb.getId()).isNull();
    }

    //Негативный кейс. Удаление несуществующего продукта. Удаление продукта с id = 0.
    @ParameterizedTest
    @EnumSource(value = ru.VasyokVasyok.enums.InvalidData.class, names = {"NONEXISTENT_PRODUCT", "ZERO_NUMBER_PR"})
    void deleteInvalidIdProduct(ru.VasyokVasyok.enums.InvalidData invalidProduct) throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(invalidProduct.getInvalidId()).execute();
        String errBody = response.errorBody().string();
        String status = errBody.substring(54, 57);
        assertThat(status).isEqualTo(invalidProduct.getStatus().toString());

        ProductsExample example = new ProductsExample();
        example.createCriteria().andCategory_idEqualTo(Long.valueOf(testCategory.getId()));
        Products productFromDb = productsMapper.selectByExample(example).get(0);
        Assertions.assertThat(productFromDb.getTitle()).isNullOrEmpty();
    }

    //Негативный кейс. Удаление уже удаленного продукта. (почему-то проверить с assertThat не выходит)
    @Test
    void deleteDeletedProduct() throws IOException {
        Integer id = createProduct();
        productService.deleteProduct(id).execute();
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        String errBody = response.errorBody().string();
        String status = errBody.substring(54, 57);
        assertThat(status).isEqualTo("500");
        ProductsExample example = new ProductsExample();
        example.createCriteria().andCategory_idEqualTo(Long.valueOf(testCategory.getId()));
        Products productFromDb = productsMapper.selectByExample(example).get(0);
        Assertions.assertThat(productFromDb.getTitle()).isNullOrEmpty();
    }
}
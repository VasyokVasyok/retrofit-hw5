package ru.VasyokVasyok.utils;

import lombok.experimental.UtilityClass;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.VasyokVasyok.db.dao.CategoriesMapper;
import ru.VasyokVasyok.db.dao.ProductsMapper;
import ru.VasyokVasyok.db.model.Categories;
import ru.VasyokVasyok.db.model.CategoriesExample;

import java.io.IOException;

@UtilityClass
public class DbUtils {
    private String resource = "mybatisConfig.xml";

    public SqlSession getSqlSession() {
        SqlSessionFactory sqlSessionFactory = null;
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactory.openSession(true);
    }

    public ProductsMapper getProductsMapper() {
        return getSqlSession().getMapper(ProductsMapper.class);
    }

    public CategoriesMapper getCategoryMapper() {
        return getSqlSession().getMapper(CategoriesMapper.class);
    }

    public Categories getNewTestCategory(String quote) {
        getCategoryMapper().insert(new Categories(quote));
        CategoriesExample categoriesExample = new CategoriesExample();
        categoriesExample.createCriteria().andTitleEqualTo(quote);
        return getCategoryMapper().selectByExample(categoriesExample).get(0);
    }
}

package lesson6;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;



public class ExampleMain {

    public static void main( String[] args ) throws IOException {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        db.dao.CategoriesMapper categoriesMapper = session.getMapper(db.dao.CategoriesMapper.class);
        db.model.CategoriesExample example = new db.model.CategoriesExample();
        example.createCriteria().andIdEqualTo(100);
        List<db.model.Categories> list = categoriesMapper.selectByExample(example);

        db.model.Categories categories = new db.model.Categories();
        categories.setId(100);
        categories.setTitle("Clothes1");
        categoriesMapper.updateByExample(categories, example);


        System.out.println(categoriesMapper.countByExample(example));

        System.out.println(list);
    }
}

package BuyProducts;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class ProductResultSet {
    public List<Product> ProductsInfo(ResultSet resultSet) {
        List<Product> array = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                array.add(product);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
}
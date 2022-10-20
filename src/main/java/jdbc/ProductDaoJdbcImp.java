package jdbc;

import buyProducts.ConnectToDB;
import buyProducts.Product;
import java.sql.*;
import java.util.*;

public class ProductDaoJdbcImp implements ProductDao{
    @Override
    public List<Product> findAllProducts() {
        List<Product> array = new ArrayList<>();
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement("select * from products")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                array.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return array;
    }
    @Override
    public Product findProductById(int productInfoById) {
        Product product = new Product();
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement("select * from products where id = ?")) {
            preparedStatement.setInt(1, productInfoById);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public void listOfUsersThatBoughtProductByProductId(int productId) {

        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement("select users.firstname, users.lastname" +
                " from users join infopurchase on infopurchase.user_id = users.id where infopurchase.product_id = ? GROUP BY users.firstname, users.lastname")) {
            preparedStatement.setInt(1,productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("Firstname") != null) {
                    System.out.println(resultSet.getString("Firstname") + " " + resultSet.getString("Lastname"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

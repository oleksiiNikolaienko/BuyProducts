package jdbc;

import entity.Product;
import entity.User;
import exception.JdbcDaoException;
import service.ConnectToDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbcImp implements ProductDao {
    @Override
    public List<Product> findAllProducts() {
        List<Product> array = new ArrayList<>();
        try (var connection = ConnectToDB.getConnection();
             var statement = connection.prepareStatement("select * from products")) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                array.add(product);
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
        return array;
    }

    @Override
    public Product findProductById(int productInfoById) {
        Product product = new Product();
        try (var connection = ConnectToDB.getConnection();
             var preparedStatement = connection.prepareStatement("select * from products where id = ?")) {
            preparedStatement.setInt(1, productInfoById);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
        return product;
    }

    @Override
    public List<String> listOfUsersThatBoughtProductByProductId(int productId) {
        List<String> array = new ArrayList<>();
        try (var connection = ConnectToDB.getConnection();
             var preparedStatement = connection.prepareStatement("select users.firstname, users.lastname" +
                     " from users join infopurchase on infopurchase.user_id = users.id where infopurchase.product_id = ? GROUP BY users.firstname, users.lastname")) {
            preparedStatement.setInt(1, productId);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                if (resultSet.getString("Firstname") != null) {
                    user.setFirstName(resultSet.getString("Firstname"));
                    user.setLastName(resultSet.getString("Lastname"));
                    array.add(user.getFirstName() + " " + user.getLastName());
                }
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
        return array;
    }
}

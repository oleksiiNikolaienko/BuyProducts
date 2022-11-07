package jdbc;

import entity.InfoPurchase;
import entity.Product;
import entity.User;
import exception.JdbcDaoException;
import service.ConnectToDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImp implements UserDao {

    @Override
    public List<User> findAllUsers() {
        List<User> array = new ArrayList<>();
        try (var connection = ConnectToDB.getConnection();
             var statement = connection.prepareStatement("select * from users")) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("Firstname"));
                user.setLastName(resultSet.getString("Lastname"));
                user.setMoney(resultSet.getInt("money"));
                array.add(user);
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
        return array;
    }

    @Override
    public User findUserById(int userInfoById) {
        User user = new User();
        try (var connection = ConnectToDB.getConnection();
             var preparedStatement = connection.prepareStatement("select * from users where id = ?")) {
            preparedStatement.setInt(1, userInfoById);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("Firstname"));
                user.setLastName(resultSet.getString("Lastname"));
                user.setMoney(resultSet.getInt("money"));
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
        return user;
    }

    @Override
    public void updateUserMoney(int userMoney, int userid) {
        try (var connection = ConnectToDB.getConnection();
             var preparedStatement = connection.prepareStatement("update users set money = ? where id = ?")) {
            preparedStatement.setInt(1, userMoney);
            preparedStatement.setInt(2, userid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
    }

    @Override
    public List<String> listOfUserProductsByUserId(int userid) {
        List<String> array = new ArrayList<>();
        try (var connection = ConnectToDB.getConnection();
             var preparedStatement = connection.prepareStatement("select products.name, count(infopurchase.product_id) AS count" +
                     " from products join infopurchase on infopurchase.product_id = products.id where infopurchase.user_id = ? GROUP BY products.name")) {
            preparedStatement.setInt(1, userid);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                InfoPurchase infoPurchase = new InfoPurchase();
                if (resultSet.getString("name") != null) {
                    product.setName(resultSet.getString("name"));
                    infoPurchase.setProduct_id(resultSet.getInt("count"));
                    array.add(product.getName() + " - " + infoPurchase.getProduct_id());
                }
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
        return array;
    }


}

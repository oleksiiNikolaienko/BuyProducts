package jdbc;

import buyProducts.ConnectToDB;
import buyProducts.User;
import java.sql.*;
import java.util.*;

public class UserDaoJdbcImp implements UserDao{

    @Override
    public List<User> findAllUsers() {
        List<User> array = new ArrayList<>();
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement("select * from users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("Firstname"));
                user.setLastName(resultSet.getString("Lastname"));
                user.setMoney(resultSet.getInt("money"));
                array.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return array;
    }

    @Override
    public User findUserById(int userInfoById) {
        User user = new User();
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement("select * from users where id = ?")) {
            preparedStatement.setInt(1, userInfoById);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("Firstname"));
                user.setLastName(resultSet.getString("Lastname"));
                user.setMoney(resultSet.getInt("money"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void updateUserMoney(int userMoney, int userid) {
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement("update users set money = ? where id = ?")) {
            preparedStatement.setInt(1, userMoney);
            preparedStatement.setInt(2, userid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void listOfUserProductsByUserId(int userid) {
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement("select products.name, count(infopurchase.product_id) AS count from products" +
                " join infopurchase on infopurchase.product_id = products.id where infopurchase.user_id = ? GROUP BY products.name")) {
            preparedStatement.setInt(1,userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("name") != null) {
                    System.out.println(resultSet.getString("name") + " - " + resultSet.getInt("count") + " pcs");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}

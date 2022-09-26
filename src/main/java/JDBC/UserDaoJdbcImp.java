package JDBC;

import BuyProducts.ConnectToDB;
import BuyProducts.User;
import java.sql.*;
import java.util.*;

public class UserDaoJdbcImp implements UserDao<User>{

    @Override
    public List<User> findAllUsers() {
        List<User> array = new ArrayList<>();
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement(SQLUser.FIND_ALL_USERS.QUERY)) {
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
    public User findUserById() {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement(SQLUser.SELECT_USER_ID.QUERY)) {
            System.out.println("Enter user id:");
            var userId = scanner.nextInt();
            preparedStatement.setInt(1, userId);
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
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement(SQLUser.UPDATE_USER_MONEY.QUERY)) {
            preparedStatement.setInt(1, userMoney);
            preparedStatement.setInt(2, userid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void listOfUserProductsByUserId() {
        Scanner scanner = new Scanner(System.in);
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement(SQLUser.JOIN_USER_AND_INFOPURCHASE.QUERY)) {
            System.out.println("Enter user id:");
            var userId = scanner.nextInt();
            preparedStatement.setInt(1,userId);
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

    enum SQLUser {
        FIND_ALL_USERS("select * from users"),
        SELECT_USER_ID("select * from users where id = ?"),
        UPDATE_USER_MONEY("update users set money = ? where id = ?"),
        JOIN_USER_AND_INFOPURCHASE("select products.name, count(infopurchase.product_id) AS count from products" +
                " join infopurchase on infopurchase.product_id = products.id where infopurchase.user_id = ? GROUP BY products.name");

        String QUERY;
        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }

}

package jdbc;

import entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAllUsers();

    User findUserById(int userInfoById);

    void updateUserMoney(int userMoney, int userid);

    List<String> listOfUserProductsByUserId(int userid);
}
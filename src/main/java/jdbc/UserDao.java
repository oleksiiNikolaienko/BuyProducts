package jdbc;

import java.util.List;
public interface UserDao<User> {
    List<User> findAllUsers();
    User findUserById();
    void updateUserMoney(int userMoney, int userid);
    void listOfUserProductsByUserId();
}

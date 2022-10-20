package jdbc;

import buyProducts.User;
import java.util.List;
public interface UserDao {
    List<User> findAllUsers();
    User findUserById(int userInfoById);
    void updateUserMoney(int userMoney, int userid);
    void listOfUserProductsByUserId(int userid);
}

package BuyProducts;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserResultSet {
    public List<User> UsersInfo(ResultSet resultSet) {
        List<User> array = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("Firstname"));
                user.setLastName(resultSet.getString("Lastname"));
                user.setMoney(resultSet.getInt("money"));
                array.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
}

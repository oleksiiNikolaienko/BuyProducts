package jdbc;

import exception.JdbcDaoException;
import service.ConnectToDB;

import java.sql.SQLException;

public class InfoPurchaseDaoJdbcImp implements InfoPurchaseDao {
    @Override
    public void insertUserIdAndProductIdWhichUserBought(int userid, int productid) {
        try (var connection = ConnectToDB.getConnection();
             var preparedStatement = connection.prepareStatement("insert into infopurchase (user_id, product_id) values (?, ?)")) {
            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, productid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
    }
}

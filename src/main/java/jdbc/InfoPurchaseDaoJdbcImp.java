package jdbc;

import buyProducts.ConnectToDB;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InfoPurchaseDaoJdbcImp  implements  InfoPurchaseDao{
    @Override
    public void insertUserIdAndProductIdWhichUserBought(int userid, int productid) {
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement("insert into infopurchase (user_id, product_id) values (?, ?)")) {
            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, productid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

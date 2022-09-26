package JDBC;

import BuyProducts.ConnectToDB;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InfoPurchaseDaoJdbcImp  implements  InfoPurchaseDao{
    @Override
    public void insert(int userid, int productid) {
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement(SQLInfoPurchase.INSERT_INTO_INFOPURCHASE.QUERY)) {
            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, productid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    enum SQLInfoPurchase {
        INSERT_INTO_INFOPURCHASE("insert into infopurchase (user_id, product_id) values (?, ?)");
        String QUERY;

        SQLInfoPurchase(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

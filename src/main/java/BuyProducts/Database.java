package BuyProducts;

public class Database {
    public String selectAllUsers() {
        return "select * from users";
    }
    public String selectAllProducts() {
        return "select * from products";
    }
    public String selectProductId() {
        return "select * from products where id = ?";
    }
    public String selectUserId() {
        return "select * from users where id = ?";
    }
    public String updateUserMoney() {
        return "update users set money = ? where id = ?";
    }
    public String insertIntoInfopurchase() {
        return "insert into infopurchase (idusers, idproducts) values (?, ?)";
    }
    public String productJoinInfopurchase() {
        return "select products.name, count(infopurchase.idproducts) AS count from products join infopurchase on infopurchase.idproducts = products.id where infopurchase.idusers = ? GROUP BY products.name";
    }
    public String userJoinInfopurchase() {
        return "select users.firstname, users.lastname from users join infopurchase on infopurchase.idusers = users.id where infopurchase.idproducts = ? GROUP BY users.firstname, users.lastname";
    }
}

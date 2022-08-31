package BuyProducts;

import java.sql.*;
import java.util.*;
public class BuyProduct {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/java_db";

    private static final String TEXT_BLOCKS = """
                1. Display list of all users
                2. Display list of all products
                3. User should be able to buy product
                4. Display list of user products by user id
                5. Display list of users that bought product by product id
                6. Exit""";

    private void chooseOne() {
        Database database = new Database();
        UserResultSet userResultSet = new UserResultSet();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_PASSWORD, DB_USERNAME); Statement statement = connection.createStatement();) {
            List<User> array = userResultSet.UsersInfo(statement.executeQuery(database.selectAllUsers()));
            for (User arrays : array) {
                System.out.println(arrays.getId() + " " + arrays.getFirstName() + " " + arrays.getLastName() + " " + arrays.getMoney());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chooseTwo() {
        Database database = new Database();
        ProductResultSet productResultSet = new ProductResultSet();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_PASSWORD, DB_USERNAME); Statement statement = connection.createStatement();) {
            List<Product> array = productResultSet.ProductsInfo(statement.executeQuery(database.selectAllProducts()));
            for (Product arrays : array) {
                System.out.println(arrays.getId() + " " + arrays.getName() + " " + arrays.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void chooseThree() {
        Scanner scanner = new Scanner(System.in);
        Database database = new Database();
        Map<String, List<Product>> map = new HashMap<String, List<Product>>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_PASSWORD, DB_USERNAME);Statement statement = connection.createStatement();) {
            System.out.println("Enter user id:");
            int userId = scanner.nextInt();
            System.out.println("Enter product id:");
            int productId = scanner.nextInt();
            PreparedStatement preparedStatement = connection.prepareStatement(database.selectProductId());
            PreparedStatement preparedStatementUsers = connection.prepareStatement(database.selectUserId());
            preparedStatement.setInt(1, productId);
            preparedStatementUsers.setInt(1, userId);
            ResultSet resultSetProducts = preparedStatement.executeQuery();
            ResultSet resultSetUsers = preparedStatementUsers.executeQuery();
            while (resultSetProducts.next() && resultSetUsers.next()) {
                if (resultSetUsers.getDouble("money") < resultSetProducts.getDouble("price")) {
                    System.out.println("The user does not have enough funds");
                } else {
                    System.out.println("You have successfully purchased the product");
                    double dicrement = resultSetUsers.getDouble("money") - resultSetProducts.getDouble("price");
                    PreparedStatement changeField = connection.prepareStatement(database.updateUserMoney());
                    changeField.setInt(1, (int) dicrement);
                    changeField.setInt(2, userId);
                    changeField.executeUpdate();
                    PreparedStatement newTable = connection.prepareStatement(database.insertIntoInfopurchase());
                    newTable.setInt(1, userId);
                    newTable.setInt(2, productId);
                    newTable.executeUpdate();
                    String nameLastname = resultSetUsers.getString("Firstname") + " " + resultSetUsers.getString("Lastname");
                    String nameProduct = resultSetProducts.getString("name");
                    Product product = new Product(nameProduct);
                    map.compute(nameLastname, (key, value) -> {
                        value = value != null ? value : new ArrayList<>();
                        value.add(product);
                        return value;
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void chooseFour() {
        Scanner scanner = new Scanner(System.in);
        Database database = new Database();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_PASSWORD, DB_USERNAME);Statement statement = connection.createStatement();){
            System.out.println("Enter user id:");
            int userId = scanner.nextInt();
            PreparedStatement preparedStatement = connection.prepareStatement(database.productJoinInfopurchase());
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("name") != null) {
                    System.out.println(resultSet.getString("name") + " - " + resultSet.getInt("count") + " pcs");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void chooseFive() {
        Scanner scanner = new Scanner(System.in);
        Database database = new Database();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_PASSWORD, DB_USERNAME);Statement statement = connection.createStatement();){
            System.out.println("Enter product id:");
            int productId = scanner.nextInt();
            PreparedStatement preparedStatement = connection.prepareStatement(database.userJoinInfopurchase());
            preparedStatement.setInt(1,productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("Firstname") != null) {
                    System.out.println(resultSet.getString("Firstname") + " " + resultSet.getString("Lastname"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void chooseSix() {
        Map<String, List<Product>> map = new HashMap<String, List<Product>>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_PASSWORD, DB_USERNAME);Statement statement = connection.createStatement();){
            map.forEach((k, v) -> System.out.println((k + ":" + v.toString())));
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        BuyProduct buyProduct = new BuyProduct();
        while (true) {
            System.out.println(TEXT_BLOCKS);
            int command = scanner.nextInt();
            switch (command) {
                case 1 -> buyProduct.chooseOne();
                case 2 -> buyProduct.chooseTwo();
                case 3 -> buyProduct.chooseThree();
                case 4 -> buyProduct.chooseFour();
                case 5 -> buyProduct.chooseFive();
                case 6 -> buyProduct.chooseSix();
                default -> System.err.println("The command is not find");
            }
        }
    }
}

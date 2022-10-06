package jdbc;

import buyProducts.ConnectToDB;
import buyProducts.Product;
import java.sql.*;
import java.util.*;

public class ProductDaoJdbcImp implements ProductDao<Product>{
    @Override
    public List<Product> findAllProducts() {
        List<Product> array = new ArrayList<>();
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement(ProductDaoJdbcImp.SQLProduct.FIND_ALL_PRODUCTS.QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                array.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return array;
    }
    @Override
    public Product findProductById() {
        Product product = new Product();
        Scanner scanner = new Scanner(System.in);
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement(ProductDaoJdbcImp.SQLProduct.SELECT_ID_PRODUCT.QUERY)) {
            System.out.println("Enter product id:");
            var productId = scanner.nextInt();
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public void listOfUsersThatBoughtProductByProductId() {
        Scanner scanner = new Scanner(System.in);
        try (PreparedStatement preparedStatement = new ConnectToDB().getConnection().prepareStatement(ProductDaoJdbcImp.SQLProduct.JOIN_PRODUCT_AND_INFOPURCHASE.QUERY)) {
            System.out.println("Enter product id:");
            var productId = scanner.nextInt();
            preparedStatement.setInt(1,productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("Firstname") != null) {
                    System.out.println(resultSet.getString("Firstname") + " " + resultSet.getString("Lastname"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    enum SQLProduct {
        FIND_ALL_PRODUCTS("select * from products"),
        SELECT_ID_PRODUCT("select * from products where id = ?"),
        JOIN_PRODUCT_AND_INFOPURCHASE("select users.firstname, users.lastname from users join infopurchase on infopurchase.user_id = users.id" +
                " where infopurchase.product_id = ? GROUP BY users.firstname, users.lastname");

        String QUERY;

        SQLProduct(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

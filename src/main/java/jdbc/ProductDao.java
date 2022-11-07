package jdbc;

import entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAllProducts();

    Product findProductById(int productInfoById);

    List<String> listOfUsersThatBoughtProductByProductId(int productId);
}

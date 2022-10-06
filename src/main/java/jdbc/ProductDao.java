package jdbc;

import java.util.List;
public interface ProductDao<Product> {
    List<Product> findAllProducts();
    Product findProductById();
    void listOfUsersThatBoughtProductByProductId();
}

package jdbc;

import buyProducts.Product;
import java.util.List;
public interface ProductDao{
    List<Product> findAllProducts();
    Product findProductById(int productInfoById);
    void listOfUsersThatBoughtProductByProductId(int productId);
}

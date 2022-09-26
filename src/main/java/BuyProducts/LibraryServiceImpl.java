package BuyProducts;

import JDBC.InfoPurchaseDaoJdbcImp;
import JDBC.ProductDaoJdbcImp;
import JDBC.UserDaoJdbcImp;
import java.util.*;

public class LibraryServiceImpl implements LibraryService{
    private static final String TEXT_BLOCKS = """
                1. Display list of all users
                2. Display list of all products
                3. User should be able to buy product
                4. Display list of user products by user id
                5. Display list of users that bought product by product id
                6. Exit""";


    @Override
    public void chooseOne() {
        List<User> array = new UserDaoJdbcImp().findAllUsers();
        for (User arrays : array) {
            System.out.println(arrays.getId() + " " + arrays.getFirstName() + " " + arrays.getLastName() + " " + arrays.getMoney());
        }
    }

    @Override
    public void chooseTwo() {
        List<Product> array = new ProductDaoJdbcImp().findAllProducts();
        for (Product arrays : array) {
            System.out.println(arrays.getId() + " " + arrays.getName() + " " + arrays.getPrice());
        }
    }

    @Override
    public void chooseThree() {
        Map<String, List<Product>> map = new HashMap<String, List<Product>>();
        User user = new UserDaoJdbcImp().findUserById();
        Product product = new ProductDaoJdbcImp().findProductById();
        if (user.getMoney() < product.getPrice()) {
            System.out.println("The user does not have enough funds");
        } else {
            System.out.println("You have successfully purchased the product");
            var decrement = user.getMoney() - product.getPrice();
            new UserDaoJdbcImp().updateUserMoney(decrement, user.getId());
            new InfoPurchaseDaoJdbcImp().insert(user.getId(), product.getId());
            String nameLastname = user.getFirstName() + " " + user.getLastName();
            String nameProduct = product.getName();
            Product pr = new Product(nameProduct);
            map.compute(nameLastname, (key, value) -> {
                value = value != null ? value : new ArrayList<>();
                value.add(pr);
                return value;
            });
        }
    }

    @Override
    public void chooseFour() {
        new UserDaoJdbcImp().listOfUserProductsByUserId();
    }

    @Override
    public void chooseFive() {
        new ProductDaoJdbcImp().listOfUsersThatBoughtProductByProductId();
    }

    @Override
    public void chooseSix() {
        System.exit(0);
    }

    public void ResultCommand() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(TEXT_BLOCKS);
            int command = scanner.nextInt();
            switch (command) {
                case 1 -> new LibraryServiceImpl().chooseOne();
                case 2 -> new LibraryServiceImpl().chooseTwo();
                case 3 -> new LibraryServiceImpl().chooseThree();
                case 4 -> new LibraryServiceImpl().chooseFour();
                case 5 -> new LibraryServiceImpl().chooseFive();
                case 6 -> new LibraryServiceImpl().chooseSix();
                default -> System.err.println("The command is not find");
            }
        }
    }
}

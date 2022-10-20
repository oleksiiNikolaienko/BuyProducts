package buyProducts;

import jdbc.*;

import java.util.*;
import java.util.stream.Stream;

public class PurсhaseServiceImp implements PurсhaseService {

    private UserDao userDao = new UserDaoJdbcImp();
    private ProductDao productDao = new ProductDaoJdbcImp();
    private InfoPurchaseDao infoPurchaseDao = new InfoPurchaseDaoJdbcImp();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String MAIN_MENU_TEXT = """
                1. Display list of all users
                2. Display list of all products
                3. User should be able to buy product
                4. Display list of user products by user id
                5. Display list of users that bought product by product id
                6. Exit""";

    @Override
    public void displayListAllUsers() {
        List<User> array = userDao.findAllUsers();
        Stream stream = array.stream();
        stream.forEach(x -> System.out.println(x));
        stream.close();
    }

    @Override
    public void displayListAllProducts() {
        List<Product> array = productDao.findAllProducts();
        Stream stream = array.stream();
        stream.forEach(x -> System.out.println(x));
        stream.close();
    }

    @Override
    public void userBuyProduct() {
        System.out.println("Enter user id:");
        User user = userDao.findUserById(scanner.nextInt());
        System.out.println("Enter product id:");
        Product product = productDao.findProductById(scanner.nextInt());
        var userId = user.getId();
        var userMoney = user.getMoney();
        var productPrice = product.getPrice();
        if (userMoney < productPrice) {
            System.out.println("The user does not have enough funds");
        } else {
            var decrement = userMoney - productPrice;
            userDao.updateUserMoney(decrement, userId);
            infoPurchaseDao.insertUserIdAndProductIdWhichUserBought(userId, product.getId());
            System.out.println("You have successfully purchased the product");
        }
    }

    @Override
    public void displayListUserProductByUserId() {
        System.out.println("Enter user id:");
        userDao.listOfUserProductsByUserId(scanner.nextInt());
    }

    @Override
    public void displayListUsersThatBoughtProductByProductId() {
        System.out.println("Enter product id:");
        productDao.listOfUsersThatBoughtProductByProductId(scanner.nextInt());
    }


    public void executeWhatUserSelectedInMenu() {
        while (true) {
            System.out.println(MAIN_MENU_TEXT);
            switch (scanner.nextInt()) {
                case 1 -> displayListAllUsers();
                case 2 -> displayListAllProducts();
                case 3 -> userBuyProduct();
                case 4 -> displayListUserProductByUserId();
                case 5 -> displayListUsersThatBoughtProductByProductId();
                case 6 -> System.exit(0);
                default -> System.err.println("The command is not find");
            }
        }
    }
}

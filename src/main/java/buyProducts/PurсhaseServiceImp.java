package buyProducts;

import jdbc.*;

import java.util.*;

public class PurсhaseServiceImp implements PurсhaseService {

    private UserDao userDao = new UserDaoJdbcImp();
    private ProductDao productDao = new ProductDaoJdbcImp();
    private InfoPurchaseDao infoPurchaseDao = new InfoPurchaseDaoJdbcImp();
    private User user;
    private Product product;



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
        for (User arrays : array) {
            System.out.println(arrays.getId() + " " + arrays.getFirstName() + " " + arrays.getLastName() + " " + arrays.getMoney());
        }
    }

    @Override
    public void displayListAllProducts() {
        List<Product> array = productDao.findAllProducts();
        for (Product arrays : array) {
            System.out.println(arrays.getId() + " " + arrays.getName() + " " + arrays.getPrice());
        }
    }

    @Override
    public void userBuyProduct() {
        user = (User) userDao.findUserById();
        product = (Product) productDao.findProductById();
        var userId = user.getId();
        var userMoney = user.getMoney();
        var getProductPrice = product.getPrice();
        if (userMoney < getProductPrice) {
            System.out.println("The user does not have enough funds");
        } else {
            System.out.println("You have successfully purchased the product");
            var decrement = userMoney - getProductPrice;
            userDao.updateUserMoney(decrement, userId);
            infoPurchaseDao.insertUserIdAndProductIdWhichUserBought(userId, product.getId());
        }
    }

    @Override
    public void displayListUserProductByUserId() {
        userDao.listOfUserProductsByUserId();
    }

    @Override
    public void displayListUsersThatBoughtProductByProductId() {
        new ProductDaoJdbcImp().listOfUsersThatBoughtProductByProductId();
    }

    @Override
    public void exitProgram() {
        System.exit(0);
    }

    public void executeWhatUserSelectedInMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(MAIN_MENU_TEXT);
            int command = scanner.nextInt();
            switch (command) {
                case 1 -> displayListAllUsers();
                case 2 -> displayListAllProducts();
                case 3 -> userBuyProduct();
                case 4 -> displayListUserProductByUserId();
                case 5 -> displayListUsersThatBoughtProductByProductId();
                case 6 -> exitProgram();
                default -> System.err.println("The command is not find");
            }
        }
    }
}

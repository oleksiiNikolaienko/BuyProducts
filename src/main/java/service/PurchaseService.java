package service;

import entity.Product;
import entity.User;
import exception.JdbcDaoException;
import exception.PurchaseServiceException;
import jdbc.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PurchaseService {

    private static final Scanner scanner = new Scanner(System.in);
    private final UserDao userDao = new UserDaoJdbcImp();
    private final ProductDao productDao = new ProductDaoJdbcImp();
    private final InfoPurchaseDao infoPurchaseDao = new InfoPurchaseDaoJdbcImp();

    public void displayListAllUsers() {
        userDao.findAllUsers().forEach(System.out::println);
    }


    public void displayListAllProducts() {
        productDao.findAllProducts().forEach(System.out::println);
    }


    public void userBuyProduct() {
        try {
            System.out.println("Enter user id:");
            var userInput = scanner.nextInt();
            User user = userDao.findUserById(userInput);
            System.out.println("Enter product id:");
            var productInput = scanner.nextInt();
            Product product = productDao.findProductById(productInput);
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
        } catch (InputMismatchException e) {
            System.out.println("You must enter whole numbers " + e.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println("fail" + e.getLocalizedMessage());
        }
    }


    public void displayListUserProductByUserId() {
        System.out.println("Enter user id:");
        var input = scanner.nextInt();
        userDao.listOfUserProductsByUserId(input).forEach(System.out::println);
    }


    public void displayListUsersThatBoughtProductByProductId() {
        System.out.println("Enter product id:");
        try {
            var input = scanner.nextInt();
            validateNewBookUserInput(input);
            var listUsers = productDao.listOfUsersThatBoughtProductByProductId(input);
            if (!listUsers.isEmpty()) {
                listUsers.forEach(System.out::println);
            } else {
                System.out.println("Product with ID " + input + " the user did not buy.");
            }

        } catch (PurchaseServiceException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        } catch (InputMismatchException e) {
            System.out.println("You need to enter a number. Restart the program!");
        } catch (JdbcDaoException e) {
            System.out.println("Sorry, due to DB error: " + e.getLocalizedMessage());
        }
    }

    private void validateNewBookUserInput(int input) {
        if (input < 1) {
            throw new PurchaseServiceException("You must enter a positive number!!!");
        }
    }


}

package service;

import java.util.Scanner;

public class BuyProductApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PurсhaseService purchaseService = new PurсhaseService();
    private static final String HEADER = """
            Welcome to our store!
            """;
    private static final String MAIN_MENU_TEXT = """
            Choose one of the options and pressing Enter:
                           
            1. Display list of all users
            2. Display list of all products
            3. User should be able to buy product
            4. Display list of user products by user id
            5. Display list of users that bought product by product id 
                            
            Type "exit" for finish program
            """;

    public static void main(String[] args) {
        System.out.println(HEADER);
        System.out.println(MAIN_MENU_TEXT);
        while (true) {

            var input = scanner.nextLine().toLowerCase();
            switch (input) {
                case "1" -> purchaseService.displayListAllUsers();
                case "2" -> purchaseService.displayListAllProducts();
                case "3" -> purchaseService.userBuyProduct();
                case "4" -> purchaseService.displayListUserProductByUserId();
                case "5" -> purchaseService.displayListUsersThatBoughtProductByProductId();
                case "exit" -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.err.println("Unknown menu option. Pick another one!");
            }
        }
    }
}

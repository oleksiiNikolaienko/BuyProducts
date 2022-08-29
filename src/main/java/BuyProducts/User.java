package BuyProducts;

public class User {
    private int id;
    private int money;
    private String firstName;
    private String lastName;

    public void setId(int id) {
        this.id = id;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

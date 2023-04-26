import java.util.Random;
import java.util.Scanner;

public class customer {
    Scanner scan = new Scanner(System.in);

    public static Integer id;
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String email;
    public String address;
    public String phone;
    static Random rand         = new Random();
    int    randomNumber = rand.nextInt(9999999);


    public customer() {
        this.id = 0;
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.address = "";
        this.phone = "";
    }


    public static Integer getId() {
        return id;
    }
    public Integer getID(){
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static int generateRandomNumber() {
        // Generate a random integer between 1,000,000 and 9,999,999 (inclusive)
        int randomNumber = rand.nextInt(8999999) + 1000000;

        return randomNumber;
    }

    public void createNewUser(customerDao dao){
        this.id = generateRandomNumber();
        System.out.println("Please enter username below: ");
        this.username = scan.nextLine();
        System.out.println("Please enter password below: ");
        this.password = scan.nextLine();
        System.out.println("Please enter First Name below: ");
        this.firstName = scan.nextLine();
        System.out.println("Please enter Last Name below: ");
        this.lastName = scan.nextLine();
        System.out.println("Please enter Email information: ");
        this.email = scan.nextLine();
        System.out.println("Please enter Address Information: ");
        this.address = scan.nextLine();
        System.out.println("Please enter Phone Number Infromation: ");
        this.phone = scan.nextLine();

        dao.addCustomer(this);
    }

    public String getCostumerInformation(){
        String costumer = username + password + firstName + lastName + email + address + phone;
        return costumer;
    }

}

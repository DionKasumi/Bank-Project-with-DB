import java.util.Scanner;

public class firstPage {
    Scanner scan = new Scanner(System.in);
    logIn newlogIn = new logIn();
    customer customer = new customer();
    customerDao cs = new customerDao();

    public boolean exit = false;

    public void firstPageLoop(){
        while (!exit){
            firstPageOptions();
        }
    }

    public void firstPageOptions(){
        System.out.println("To Log In, enter '1'\nTo Create New User, enter '2'\nTo Exit Program, enter 'exit' or 'e'");
        String logInOrNewAcc = scan.next();

        if (logInOrNewAcc.equals("1")){
            newlogIn.logInAttempt();
        } else if (logInOrNewAcc.equals("2")) {
            customer.createNewUser(cs);
        } else if (logInOrNewAcc.equalsIgnoreCase("exit") || logInOrNewAcc.equalsIgnoreCase("e") ) {
            exit = true;
        }
    }
}

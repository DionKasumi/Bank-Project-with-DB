import java.util.Scanner;

public class logIn {
    Scanner scan = new Scanner(System.in);

    customerDao customerdao = new customerDao();
    accountsDao adao = new accountsDao();
    accounts acc = new accounts();
    boolean stayHere;
    public customer logInAttempt(){
        System.out.println("Please enter username below: ");
        String usernameAttempt = scan.nextLine();
        if (customerdao.checkUsername(usernameAttempt)){
            passwordTry(usernameAttempt);
        }else System.out.println("Username Incorrect");

        return null;
    }

    public void homepageOptions(String usernameAttempt, String passwordAttempt){
        System.out.println("To create new Account, Enter '1'\nTo Show List of your Account, Enter '2'\nTo Access an Account, Enter '3'\nTo Go Back, Enter '4'\n");
        String accountDecision = scan.nextLine();
        if (accountDecision.equalsIgnoreCase("1")) {
            acc.createNewAccount(adao, usernameAttempt, passwordAttempt);
        } else if (accountDecision.equalsIgnoreCase("2")) {
            adao.getAllAccountsOfUserAndShow(customerdao.getLoggedInUser(usernameAttempt, passwordAttempt));
        } else if (accountDecision.equalsIgnoreCase("3")) {
            adao.accessingAccountForWithdrawOrDeposit(customerdao.getLoggedInUser(usernameAttempt, passwordAttempt));
        } else if (accountDecision.equals("4")) {
            this.stayHere = false;
        }
    }

    public void passwordTry(String usernameAttempt){
        System.out.println("Enter password: ");
        String passwordAttempt = scan.nextLine();
        if(customerdao.checkPassword(usernameAttempt, passwordAttempt)){
            System.out.println("*********************************\n    Successfully logged in!\n*********************************");
            this.stayHere = true;
            while(stayHere) {
                homepageOptions(usernameAttempt, passwordAttempt);
            }

        }else System.out.println("Password Incorrect");
    }

}


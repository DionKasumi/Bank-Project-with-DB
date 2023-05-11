import java.util.Scanner;

public class logIn {
    Scanner scan = new Scanner(System.in);

    customerDao customerdao = new customerDao();
    accountsDao adao = new accountsDao();
    accounts acc = new accounts();

    transactionsDao transactionsdao = new transactionsDao();
    boolean stayHere;
    public customer logInAttempt(){
        System.out.println("\nPlease enter username below: ");
        String usernameAttempt = scan.nextLine();
        if (customerdao.checkUsername(usernameAttempt)){
            passwordTry(usernameAttempt);
        }else System.out.println("Username Incorrect");

        return null;
    }

    public void homepageOptions(String usernameAttempt, String passwordAttempt){
        System.out.println("Enter '1' - Create New Account\nEnter '2' - Show a List of Your Accounts\nEnter '3' - Access an Account For Withdrawal or Deposit\nEnter '4' - Load Transactions Of An Account\nEnter '5' -  Issue a Transfer\nEnter '6' - Back To Home Page");
        String accountDecision = scan.nextLine();
        if (accountDecision.equalsIgnoreCase("1")) {
            System.out.println("\n*CREATING NEW ACCOUNT*");
            acc.createNewAccount(adao, usernameAttempt, passwordAttempt);
        } else if (accountDecision.equalsIgnoreCase("2")) {
            System.out.println("\n*LIST OF ACCOUNTS*");
            adao.getAllAccountsOfUserAndShow(customerdao.getLoggedInUser(usernameAttempt, passwordAttempt));
        } else if (accountDecision.equalsIgnoreCase("3")) {
            System.out.println("\n*DEPOSIT / WITHDRAW*");
            adao.accessingAccountForWithdrawOrDeposit(customerdao.getLoggedInUser(usernameAttempt, passwordAttempt));
        } else if (accountDecision.equals("4")) {
            System.out.println("\n*TRANSACTIONS HISTORY*");
            transactionsdao.getTransactionsForAccount();
        }else if(accountDecision.equals("5")){
            System.out.println("\n*TRANSFER MENU*");
            adao.getInfoForTransfer();
        }else if(accountDecision.equals("6")){
            System.out.println("See you soon!");
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


import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class accountsDao {

    static Random rand = new Random();

    Scanner scan = new Scanner(System.in);

    public static int generateRandomNumber() {
        // Generate a random integer between 1,000,000 and 9,999,999 (inclusive)

        return rand.nextInt(8999999) + 1000000;
    }

    transactionsDao transactionsdao = new transactionsDao();

    //adding acc
    public void addAccount(customer c) {
        System.out.println("Type of Account: \n1. Savings\n2. Investment");
        generateNewAccount(c, accountType());
    }

    //method to choose Account type: Investment or Savings
    public String accountType() {
        String typeOfAccount = scan.nextLine();
        if (typeOfAccount.equalsIgnoreCase("1")) {
            typeOfAccount = "Savings";
            return typeOfAccount;
        } else if (typeOfAccount.equalsIgnoreCase("2")) {
            typeOfAccount = "Investment";
            return typeOfAccount;
        } else {
            System.out.println("Value not recognized.");
            return null;
        }
    }

    //making first deposit
    public void generateNewAccount(customer c, String typeOfAccount) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        System.out.println("How much money would you like to deposit to your new account?");        //maybe add min value
        double firstDeposit = scan.nextDouble();
        String sql = "INSERT INTO " + IDatabaseInformation.accountsTable + "(account_id, costumer_id, account_type, balance) VALUES (?, ?, ?, ?)";
        try {
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, generateRandomNumber());
            pstmt.setInt(2, c.getId()); //here need to add link to customer
            pstmt.setString(3, typeOfAccount);
            pstmt.setDouble(4, firstDeposit);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeAll(conn, stmt, pstmt, rs);
        }
    }

    //getting a list of accounts based on customer ID
    public void getAllAccountsOfUserAndShow(customer c) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        System.out.println("Loading Account");
        try {
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(IDatabaseInformation.getAccountsSql + c.getID());
            rsGettingAllAccountsOfUserFromDatabase(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(conn, stmt, pstmt, rs);
        }
    }

    //method to get the info of accounts
    public void rsGettingAllAccountsOfUserFromDatabase(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            System.out.println("No accounts found for customer");
        } else {
            do {
                int account_id = rs.getInt(IDatabaseInformation.accountsId);
                String account_type = rs.getString(IDatabaseInformation.accountType);
                double balance = rs.getDouble(IDatabaseInformation.balance);
                String account = "Account Number: " + account_id + "\nAccount Type: " + account_type + "\nBalance: " + balance;
                System.out.println(account);
                System.out.println("\n");
            } while (rs.next());
        }
        System.out.println("Enter anything to exit Account List Page");
        String exit = scan.next();
    }

    //accessing one of the accounts to withdraw or deposit
    public void accessingAccountForWithdrawOrDeposit(customer c) {

        System.out.println("Please enter account number below: ");
        Integer accountNumByUser = scan.nextInt();
        Integer customerId = c.getID();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String accessAccountSql = "SELECT * FROM " + IDatabaseInformation.accountsTable + " WHERE " + IDatabaseInformation.accountsId + " = " + accountNumByUser + " AND " + customerId + " = " + IDatabaseInformation.accountTable_customerId;
        try {
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(accessAccountSql);
            rsAccessingAnAccountForDepositOrWithdraw(rs, accountNumByUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(conn, stmt, pstmt, rs);
        }
    }

    //rs process to get and set the new funds based on withdraw or deposit
    public void rsAccessingAnAccountForDepositOrWithdraw(ResultSet rs, Integer accountNumByUser) throws SQLException {
        if (!rs.next()) {
            System.out.println("No accounts found with this Number: " + accountNumByUser);
        } else {
            do {
                Integer account_id = rs.getInt(IDatabaseInformation.accountsId);
                Double balance = rs.getDouble(IDatabaseInformation.balance);
                String account = "Account Number: " + account_id + "\nBalance: " + balance;
                System.out.println(account);
                System.out.println("\n");
                System.out.println("To Withdraw, Enter '1'\nTo Deposit, Enter '2'");
                String actionDecision = scan.next();
                withdrawOrDeposit(actionDecision, balance, account_id);
            } while (rs.next());
        }
    }

    //User input (withdraw or deposit) and methods that it calls
    public void withdrawOrDeposit(String actionDecision, Double balance, Integer account_id) {
        if (actionDecision.equals("1")) {
            withdrawMoney(balance, account_id);
        } else if (actionDecision.equals("2")) {
            depositMoney(balance, account_id);
        }
    }

    //method to withdraw money
    public void withdrawMoney(Double balance, Integer accountId) {
        String type = "Withdraw";
        System.out.println("Write amount you want to withdraw: ");
        Double amountForWithdraw = scan.nextDouble();
        balance = balance - amountForWithdraw;
        String sql = "UPDATE " + IDatabaseInformation.accountsTable + " SET " + IDatabaseInformation.balance + " = ? WHERE " + IDatabaseInformation.accountsId + " = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            ((org.sqlite.SQLiteConnection) conn).setBusyTimeout(5000); // Timeout in milliseconds
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, balance);
            pstmt.setInt(2, accountId);
            pstmt.executeUpdate();
            System.out.println("Changes Saved, Account Number: " + accountId + "\nBalance: " + balance);
            transactionsdao.generateNewTransaction(type, accountId, amountForWithdraw);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(conn, stmt, pstmt, rs);
            System.out.println("\nEnter anything to exit Withdraw Page");
            String exit = scan.next();
            System.out.println("\n");
        }
    }

    //method to deposit money
    public void depositMoney(Double balance, Integer accountId) {
        String type = "Deposit";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmtDelete;
        PreparedStatement pstmtInsert = null;
        System.out.println("Write amount you want to deposit: ");
        double amountForDeposit = scan.nextDouble();
        balance += amountForDeposit;
//        String customer_id_on_account = access_a_particularFieldInUsersAccount(IDatabaseInformation.accountTable_customerId, accountId);
//        String account_type = access_a_particularFieldInUsersAccount(IDatabaseInformation.accountType, accountId);
        String updateSQL = "UPDATE " + IDatabaseInformation.accountsTable + " SET " + IDatabaseInformation.balance + " = " + balance + " WHERE " + IDatabaseInformation.accountsId + " = " + accountId;
        try {
            access_a_particularFieldInUsersAccount(IDatabaseInformation.accountType, accountId);
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            PreparedStatement pstmtUpdate = conn.prepareStatement(updateSQL);
            pstmtUpdate.execute();
            System.out.println("Changes Saved, Account Number: " + accountId + "\nBalance: " + balance);
            transactionsdao.generateNewTransaction(type, accountId, amountForDeposit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(conn, stmt, pstmtInsert, rs);
            System.out.println("\nEnter anything to exit Deposit Page");
            String exit = scan.next();
            System.out.println("\n");
        }

    }

    //method to access a specific value from the account based on account ID
    public String access_a_particularFieldInUsersAccount(String getValue, Integer account_id) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String accessAccountTypeSql = "SELECT " + getValue + " FROM " + IDatabaseInformation.accountsTable + " WHERE " + IDatabaseInformation.accountsId + " = " + account_id;
        try {
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(accessAccountTypeSql);
            if (!rs.next()) {
                System.out.println("No accounts found with this criteria: " + account_id);
            } else {
                do {
                    return rs.getString(String.valueOf(getValue));
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(conn, stmt, pstmt, rs);
        }
        return "Value not found!";
    }

    public void closeAll(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Exception on closing result set");
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Exception on closing prepared statement");
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // Handle exception here
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // Handle exception here
            }
        }
    }
}
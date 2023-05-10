import java.sql.*;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class transactionsDao {

    static Random rand = new Random();
    Scanner scan = new Scanner(System.in);

    LocalDateTime currentDateTime = LocalDateTime.now();
    transactions transaction = new transactions();

    public static int generateRandomNumber() {
        return rand.nextInt(8999999) + 1000000;
    }

    public void generateNewTransaction(String type, Integer accountId, Double amount) {
        String sql = "INSERT INTO " + IDatabaseInformation.transactionsTable + " VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, generateRandomNumber());
            pstmt.setString(2, currentDateTime.toString());
            pstmt.setInt(3, accountId);
            pstmt.setDouble(4, amount);
            pstmt.setString(5, type);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getTransactionsForAccount(){
        System.out.println("Please enter account number below: ");
        int accountNumByUser = scan.nextInt();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sqlGetTransactions = "SELECT * FROM " + IDatabaseInformation.transactionsTable + " WHERE " + IDatabaseInformation.transactionsAccount + " = " +  accountNumByUser;
        try {
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlGetTransactions);
            printingTransactionsOfAccount(rs, accountNumByUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(conn, stmt, pstmt, rs);
        }
    }

    public void printingTransactionsOfAccount(ResultSet rs, Integer accountNumByUser) throws SQLException {
        if (!rs.next()) {
            System.out.println("No transactions found with this Number: " + accountNumByUser);
        } else {
            do {
                int transactionId = rs.getInt(IDatabaseInformation.transactionsID);
                String transactionTime = rs.getString(IDatabaseInformation.transactionsTime);
                int transactionAccount = rs.getInt(IDatabaseInformation.transactionsAccount);
                double amount = rs.getDouble(IDatabaseInformation.transactionsAmount);
                String transactionType = rs.getString(IDatabaseInformation.transactionsType);
                String transaction = "ID: " + transactionId + "  Time: " + transactionTime + "  Amount: " + amount + "  Type: " + transactionType + "  Account Number: " + transactionAccount;
                System.out.println(transaction);
            } while (rs.next());
        }  System.out.println("\nEnter anything to exit transactions table!");
           String exit = scan.next();
           System.out.println("\n");
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


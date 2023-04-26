public interface IDatabaseInformation {

    //database path
    String databasePath = "jdbc:sqlite:C:\\Users\\dionk\\Desktop\\sqlite\\bank.db";

    //customers Table
    String customersTable = "customer_table";
    String customerID = "ID";
    String customersUsername = "USERNAME";
    String customerPassword = "PASSWORD";
    String customerFirstName = "FIRST_NAME";
    String customerLastName = "LAST_NAME";
    String customerEmail = "EMAIL";
    String customerAddress = "ADDRESS";
    String customerPhoneNumber = "PHONE";

    //accounts table
    String accountsTable = "accounts";
    String accountsId = "account_id";
    String accountTable_customerId = "costumer_id";
    String accountType = "account_type";
    String balance = "balance";

    //SQL CODE CUSTOMER
    String checkPasswordSql = "SELECT " + IDatabaseInformation.customerPassword + " FROM " + IDatabaseInformation.customersTable + " WHERE " + IDatabaseInformation.customersUsername + " = ?";
    String getLoggedInUserSql = "SELECT * FROM " + IDatabaseInformation.customersTable + " WHERE " + IDatabaseInformation.customersUsername + " = ? AND " + IDatabaseInformation.customerPassword + " = ?";
    String getUsernameSql = "SELECT " + IDatabaseInformation.customersUsername + " FROM " + IDatabaseInformation.customersTable;
    String addCustomerSql = "INSERT INTO customer_table(id, username, password, first_name, last_name, email, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    //SQL CODE ACCOUNTS
    String getAccountsSql = "SELECT * FROM " + IDatabaseInformation.accountsTable + " WHERE " + IDatabaseInformation.accountTable_customerId + " = ";
}

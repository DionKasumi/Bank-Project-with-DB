import java.sql.*;

public class customerDao {

ResultSet rs = null;
Statement stmt = null;
Connection conn = null;
PreparedStatement pstmt = null;


    public void addCustomer(customer customer) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try{
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            pstmt = conn.prepareStatement(IDatabaseInformation.addCustomerSql);
            pstmt.setInt(1, customer.getId());
            pstmt.setString(2, customer.getUsername() != null ? customer.getUsername() : "");
            pstmt.setString(3, customer.getPassword() != null ? customer.getPassword() : "");
            pstmt.setString(4, customer.getFirstName() != null ? customer.getFirstName() : "");
            pstmt.setString(5, customer.getLastName() != null ? customer.getLastName() : "");
            pstmt.setString(6, customer.getEmail() != null ? customer.getEmail() : "");
            pstmt.setString(7, customer.getAddress() != null ? customer.getAddress() : "");
            pstmt.setString(8, customer.getPhone() != null ? customer.getPhone() : "");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            closeAll(conn, stmt, pstmt, rs);
        }
    }

    public boolean checkUsername(String username) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(IDatabaseInformation.getUsernameSql);
            while (rs.next()) {
                String value = rs.getString(IDatabaseInformation.customersUsername);
                if (value.equals(username)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(conn, stmt, pstmt, rs);
        }
        return false;
    }


    public boolean checkPassword(String username, String password) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            pstmt = conn.prepareStatement(IDatabaseInformation.checkPasswordSql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString(IDatabaseInformation.customerPassword);
                return storedPassword.equals(password);
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
           closeAll(conn, stmt, pstmt, rs);
        }
    }


    public customer getLoggedInUser(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(IDatabaseInformation.databasePath);
            pstmt = conn.prepareStatement(IDatabaseInformation.getLoggedInUserSql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                customer c = new customer();
                // create a new customer object with the retrieved data
                c.setId(rs.getInt(IDatabaseInformation.customerID));
                c.setUsername(username);
                c.setPassword(password);
                c.setFirstName(rs.getString(IDatabaseInformation.customerFirstName));
                c.setLastName(rs.getString(IDatabaseInformation.customerLastName));
                c.setEmail(rs.getString(IDatabaseInformation.customerEmail));
                c.setAddress(rs.getString(IDatabaseInformation.customerAddress));
                c.setPhone(rs.getString(IDatabaseInformation.customerPhoneNumber));

                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, null, pstmt, rs);
        }
        return null;
    }


    public void closeAll(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // Handle exception here
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                // Handle exception here
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



public class accounts {

    public Integer accountId;
    public String accountType;
    public Double balance;
    public Integer customerForeignKey;

    accountsDao adao = new accountsDao();
    customerDao cdao = new customerDao();

    public accounts() {
        this.accountId = 0;
        this.accountType = "";
        this.balance = 0.0;
        this.customerForeignKey = 0;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getCustomerForeignKey() {
        return customerForeignKey;
    }

    public void setCustomerForeignKey(Integer customerForeignKey) {
        this.customerForeignKey = customerForeignKey;
    }

    public void createNewAccount(accountsDao adao, String username, String password){
        adao.addAccount(cdao.getLoggedInUser(username, password));

    }

}

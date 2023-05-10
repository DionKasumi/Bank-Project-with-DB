public class transactions {
    Integer transactionsId;
    String transactionsTime;
    Integer transactions_accountId;
    Double amount;
    String type;


    public transactions() {
        this.transactionsId = 0;
        this.transactionsTime = "";
        this.transactions_accountId = 0;
        this.amount = 0.0;
        this.type = "";
    }

    public Integer getTransactionsId() {
        return transactionsId;
    }

    public void setTransactionsId(Integer transactionsId) {
        this.transactionsId = transactionsId;
    }

    public String getTransactionsTime() {
        return transactionsTime;
    }

    public void setTransactionsTime(String transactionsTime) {
        this.transactionsTime = transactionsTime;
    }

    public Integer getTransactions_accountId() {
        return transactions_accountId;
    }

    public void setTransactions_accountId(Integer transactions_accountId) {
        this.transactions_accountId = transactions_accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

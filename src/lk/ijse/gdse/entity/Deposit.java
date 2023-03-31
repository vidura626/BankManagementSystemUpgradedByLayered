package lk.ijse.gdse.entity;

public class Deposit implements SuperEntity{
    private String depositId;
    private String transactionId;
    private String depositTypeAccountId;
    private Double amount;

    public Deposit() {
    }

    public Deposit(String depositId, String transactionId, String depositTypeAccountId, Double amount) {
        this.depositId = depositId;
        this.transactionId = transactionId;
        this.depositTypeAccountId = depositTypeAccountId;
        this.amount = amount;
    }

    public String getDepositId() {
        return depositId;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDepositTypeAccountId() {
        return depositTypeAccountId;
    }

    public void setDepositTypeAccountId(String depositTypeAccountId) {
        this.depositTypeAccountId = depositTypeAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

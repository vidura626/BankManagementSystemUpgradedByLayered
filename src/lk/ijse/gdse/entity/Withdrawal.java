package lk.ijse.gdse.entity;

public class Withdrawal implements SuperEntity{
    private String withdrawalId;
    private String transactionId;
    private String depositTypeAccountId;
    private double amount;

    public Withdrawal() {
    }

    public Withdrawal(String withdrawalId, String transactionId, String depositTypeAccountId, double amount) {
        this.withdrawalId = withdrawalId;
        this.transactionId = transactionId;
        this.depositTypeAccountId = depositTypeAccountId;
        this.amount = amount;
    }

    public String getWithdrawalId() {
        return withdrawalId;
    }

    public void setWithdrawalId(String withdrawalId) {
        this.withdrawalId = withdrawalId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

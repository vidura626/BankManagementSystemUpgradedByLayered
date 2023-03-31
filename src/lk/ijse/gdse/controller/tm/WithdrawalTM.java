package lk.ijse.gdse.controller.tm;

import java.sql.Date;
import java.sql.Time;

public class WithdrawalTM {
    private String withdrawalId;
    private String transactionId;
    private String depositType;
    private double amount;
    private String accountId;
    private Date date;
    private Time time;
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

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public WithdrawalTM() {
    }

    public WithdrawalTM(String withdrawalId, String transactionId, String depositType, double amount, String accountId, Date date, Time time) {
        this.withdrawalId = withdrawalId;
        this.transactionId = transactionId;
        this.depositType = depositType;
        this.amount = amount;
        this.accountId = accountId;
        this.date = date;
        this.time = time;
    }

}

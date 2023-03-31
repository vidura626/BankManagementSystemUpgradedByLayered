package lk.ijse.gdse.controller.tm;

import java.sql.Date;
import java.sql.Time;

public class DepositViewTM {
    String depositId;
    String depositTypeAccountId;
    String transactionId;
    double amount;
    String accountId;
    Date date;
    Time time;

    public String getDepositId() {
        return depositId;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public String getDepositTypeAccountId() {
        return depositTypeAccountId;
    }

    public void setDepositTypeAccountId(String depositTypeAccountId) {
        this.depositTypeAccountId = depositTypeAccountId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public DepositViewTM(String depositId, String depositTypeAccountId, String transactionId, double amount, String accountId, Date date, Time time) {
        this.depositId = depositId;
        this.depositTypeAccountId = depositTypeAccountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.accountId = accountId;
        this.date = date;
        this.time = time;
    }

    public DepositViewTM() {
    }
}

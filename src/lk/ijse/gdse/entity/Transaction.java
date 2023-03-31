package lk.ijse.gdse.entity;

import java.sql.Date;
import java.sql.Time;

public class Transaction implements SuperEntity{
    private String transactionId;
    private String accountId;
    private double amount;
    private Date date;
    private Time time;
    private String type;

    public Transaction() {
    }

    public Transaction(String transactionId, String accountId, double amount, Date date, Time time, String type) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

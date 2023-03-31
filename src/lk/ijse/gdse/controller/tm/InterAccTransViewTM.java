package lk.ijse.gdse.controller.tm;

import lk.ijse.gdse.dto.TransactionDTO;

import java.sql.Date;
import java.sql.Time;

public class InterAccTransViewTM {
    String interAccountTransactionId;
    String transactionId;
    String account01Id;
    String account02Id;
    double amount;
    Date date;
    Time time;

    public String getInterAccountTransactionId() {
        return interAccountTransactionId;
    }

    public void setInterAccountTransactionId(String interAccountTransactionId) {
        this.interAccountTransactionId = interAccountTransactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccount01Id() {
        return account01Id;
    }

    public void setAccount01Id(String account01Id) {
        this.account01Id = account01Id;
    }

    public String getAccount02Id() {
        return account02Id;
    }

    public void setAccount02Id(String account02Id) {
        this.account02Id = account02Id;
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

    public InterAccTransViewTM(String interAccountTransactionId, String transactionId, String account01Id, String account02Id, double amount, Date date, Time time) {
        this.interAccountTransactionId = interAccountTransactionId;
        this.transactionId = transactionId;
        this.account01Id = account01Id;
        this.account02Id = account02Id;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public InterAccTransViewTM() {
    }
}

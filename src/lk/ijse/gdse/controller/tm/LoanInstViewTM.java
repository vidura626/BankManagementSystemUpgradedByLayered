package lk.ijse.gdse.controller.tm;

import lk.ijse.gdse.dto.TransactionDTO;

import java.sql.Date;
import java.sql.Time;

public class LoanInstViewTM {
    String loanInstallmentId;
    String transactionId;
    String loanId;
    double amount;
    String accountId;
    Date date;
    Time time;

    public String getLoanInstallmentId() {
        return loanInstallmentId;
    }

    public void setLoanInstallmentId(String loanInstallmentId) {
        this.loanInstallmentId = loanInstallmentId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
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

    public LoanInstViewTM() {
    }

    public LoanInstViewTM(String loanInstallmentId, String transactionId, String loanId, double amount, String accountId, Date date, Time time) {
        this.loanInstallmentId = loanInstallmentId;
        this.transactionId = transactionId;
        this.loanId = loanId;
        this.amount = amount;
        this.accountId = accountId;
        this.date = date;
        this.time = time;
    }
}

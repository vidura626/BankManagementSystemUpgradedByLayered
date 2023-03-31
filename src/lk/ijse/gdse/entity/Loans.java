package lk.ijse.gdse.entity;

import java.sql.Date;

public class Loans implements SuperEntity{
    private String loanId;
    private double amount;
    private String loanTypeId;
    private String accountId;
    private int installments;
    private double installmentAmount;
    private Date issuedDate;
    private int remainingInstallments;
    private double remainingLoanAmount;
    private int monthlyInstallmentDate;
    private String settledOrNot;
    private double interestAmount;

    public Loans(String loanId, double amount, String loanTypeId, String accountId, int installments, double installmentAmount, Date issuedDate, int remainingInstallments, double remainingLoanAmount, int monthlyInstallmentDate, String settledOrNot, double interestAmount) {
        this.loanId = loanId;
        this.amount = amount;
        this.loanTypeId = loanTypeId;
        this.accountId = accountId;
        this.installments = installments;
        this.installmentAmount = installmentAmount;
        this.issuedDate = issuedDate;
        this.remainingInstallments = remainingInstallments;
        this.remainingLoanAmount = remainingLoanAmount;
        this.monthlyInstallmentDate = monthlyInstallmentDate;
        this.settledOrNot = settledOrNot;
        this.interestAmount = interestAmount;
    }

    public Loans() {
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

    public String getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(String loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public int getRemainingInstallments() {
        return remainingInstallments;
    }

    public void setRemainingInstallments(int remainingInstallments) {
        this.remainingInstallments = remainingInstallments;
    }

    public double getRemainingLoanAmount() {
        return remainingLoanAmount;
    }

    public void setRemainingLoanAmount(double remainingLoanAmount) {
        this.remainingLoanAmount = remainingLoanAmount;
    }

    public int getMonthlyInstallmentDate() {
        return monthlyInstallmentDate;
    }

    public void setMonthlyInstallmentDate(int monthlyInstallmentDate) {
        this.monthlyInstallmentDate = monthlyInstallmentDate;
    }

    public String getSettledOrNot() {
        return settledOrNot;
    }

    public void setSettledOrNot(String settledOrNot) {
        this.settledOrNot = settledOrNot;
    }

    public double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(double interestAmount) {
        this.interestAmount = interestAmount;
    }
}

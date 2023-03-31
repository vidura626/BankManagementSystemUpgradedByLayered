package lk.ijse.gdse.dto;

import lk.ijse.gdse.entity.SuperEntity;

public class PendingLoansDTO implements SuperEntity {
    private String pendingLoanId;
    private double amount;
    private String loanTypeId;
    private String accountId;
    private int installments;
    private double installmentAmount;

    public PendingLoansDTO() {
    }

    public PendingLoansDTO(String pendingLoanId, double amount, String loanTypeId, String accountId, int installments, double installmentAmount) {
        this.pendingLoanId = pendingLoanId;
        this.amount = amount;
        this.loanTypeId = loanTypeId;
        this.accountId = accountId;
        this.installments = installments;
        this.installmentAmount = installmentAmount;
    }

    public String getPendingLoanId() {
        return pendingLoanId;
    }

    public void setPendingLoanId(String pendingLoanId) {
        this.pendingLoanId = pendingLoanId;
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

    @Override
    public String toString() {
        return pendingLoanId;
    }
}

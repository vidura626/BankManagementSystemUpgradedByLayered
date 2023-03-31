package lk.ijse.gdse.dto;

import lk.ijse.gdse.entity.SuperEntity;

public class RejectedLoansDTO implements SuperEntity {
    private String rejLoanId;
    private double amount;
    private String loanTypeId;
    private String accountId;
    private String reason;

    public RejectedLoansDTO() {
    }

    public RejectedLoansDTO(String rejLoanId, double amount, String loanTypeId, String accountId, String reason) {
        this.rejLoanId = rejLoanId;
        this.amount = amount;
        this.loanTypeId = loanTypeId;
        this.accountId = accountId;
        this.reason = reason;
    }

    public String getRejLoanId() {
        return rejLoanId;
    }

    public void setRejLoanId(String rejLoanId) {
        this.rejLoanId = rejLoanId;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

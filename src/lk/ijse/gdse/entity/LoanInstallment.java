package lk.ijse.gdse.entity;

public class LoanInstallment implements SuperEntity{
    private String loanInstallmentId;
    private String transactionId;
    private String loanId;

    public LoanInstallment() {
    }

    public LoanInstallment(String loanInstallmentId, String transactionId, String loanId) {
        this.loanInstallmentId = loanInstallmentId;
        this.transactionId = transactionId;
        this.loanId = loanId;
    }

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
}

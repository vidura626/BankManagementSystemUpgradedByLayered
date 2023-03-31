package lk.ijse.gdse.dto;

import lk.ijse.gdse.entity.SuperEntity;

public class LoanInstallmentDTO implements SuperEntity {
    private String loanInstallmentId;
    private String transactionId;
    private String loanId;

    public LoanInstallmentDTO() {
    }

    public LoanInstallmentDTO(String loanInstallmentId, String transactionId, String loanId) {
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

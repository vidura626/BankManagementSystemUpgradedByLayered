package lk.ijse.gdse.dto;

import lk.ijse.gdse.entity.Deposit;
import lk.ijse.gdse.entity.SuperEntity;

public class DepositDTO implements SuperEntity {
    private String depositId;
    private String transactionId;
    private String depositTypeAccountId;
    private Double amount;

    public DepositDTO() {
    }

    public DepositDTO(String depositId, String transactionId, String depositTypeAccountId, Double amount) {
        this.depositId = depositId;
        this.transactionId = transactionId;
        this.depositTypeAccountId = depositTypeAccountId;
        this.amount = amount;
    }

    public String getDepositId() {
        return depositId;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDepositTypeAccountId() {
        return depositTypeAccountId;
    }

    public void setDepositTypeAccountId(String depositTypeAccountId) {
        this.depositTypeAccountId = depositTypeAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

package lk.ijse.gdse.dto;

import lk.ijse.gdse.entity.SuperEntity;

public class InterAccountTransactionDTO implements SuperEntity {
    private String interAccountTransactionId;
    private String transactionId;
    private String account01Id;
    private String account02Id;
    private double amount;

    public InterAccountTransactionDTO() {
    }

    public InterAccountTransactionDTO(String interAccountTransactionId, String transactionId, String account01Id, String account02Id, double amount) {
        this.interAccountTransactionId = interAccountTransactionId;
        this.transactionId = transactionId;
        this.account01Id = account01Id;
        this.account02Id = account02Id;
        this.amount = amount;
    }

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
}

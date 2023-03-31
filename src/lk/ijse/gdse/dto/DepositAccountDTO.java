package lk.ijse.gdse.dto;

import lk.ijse.gdse.entity.SuperEntity;

import java.sql.Date;

public class DepositAccountDTO implements SuperEntity {
    private String depositTypeAccountId;
    private String depositTypeId;
    private String accountId;
    private Date createdDate;
    private double balance;

    public DepositAccountDTO(String depositTypeAccountId, String depositTypeId, String accountId, Date createdDate, double balance) {
        this.depositTypeAccountId = depositTypeAccountId;
        this.depositTypeId = depositTypeId;
        this.accountId = accountId;
        this.createdDate = createdDate;
        this.balance = balance;
    }

    public DepositAccountDTO() {
    }

    public String getDepositTypeAccountId() {
        return depositTypeAccountId;
    }

    public void setDepositTypeAccountId(String depositTypeAccountId) {
        this.depositTypeAccountId = depositTypeAccountId;
    }

    public String getDepositTypeId() {
        return depositTypeId;
    }

    public void setDepositTypeId(String depositTypeId) {
        this.depositTypeId = depositTypeId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

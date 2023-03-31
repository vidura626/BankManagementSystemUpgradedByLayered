package lk.ijse.gdse.dto;

import lk.ijse.gdse.entity.SuperEntity;

public class DepositDetailsDTO implements SuperEntity {
    private String depositTypeId;
    private String description;
    private double interest;

    @Override
    public String toString() {
        return description;
    }

    public DepositDetailsDTO() {
    }

    public DepositDetailsDTO(String depositTypeId, String description, double interest) {
        this.depositTypeId = depositTypeId;
        this.description = description;
        this.interest = interest;
    }

    public String getDepositTypeId() {
        return depositTypeId;
    }

    public void setDepositTypeId(String depositTypeId) {
        this.depositTypeId = depositTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
}

package lk.ijse.gdse.dto;

import lk.ijse.gdse.entity.SuperEntity;

public class LoanDetailsDTO implements SuperEntity {
    private String loanTypeId;
    private String description;
    private double interest;
    private String amounts;

    public LoanDetailsDTO() {
    }

    public LoanDetailsDTO(String loanTypeId, String description, double interest, String amounts) {
        this.loanTypeId = loanTypeId;
        this.description = description;
        this.interest = interest;
        this.amounts = amounts;
    }

    public String getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(String loanTypeId) {
        this.loanTypeId = loanTypeId;
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

    public String getAmounts() {
        return amounts;
    }

    public void setAmounts(String amounts) {
        this.amounts = amounts;
    }

    @Override
    public String toString() {
        return description;
    }
}

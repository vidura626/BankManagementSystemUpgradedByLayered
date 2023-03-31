package lk.ijse.gdse.entity;

public class DepositDetails implements SuperEntity{
    private String depositTypeId;
    private String description;
    private double interest;

    public DepositDetails() {
    }

    public DepositDetails(String depositTypeId, String description, double interest) {
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

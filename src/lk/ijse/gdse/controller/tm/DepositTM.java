package lk.ijse.gdse.controller.tm;

public class DepositTM {
    String depositType;
    double amount;
    double balance;

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public DepositTM(String depositType, double amount, double balance) {
        this.depositType = depositType;
        this.amount = amount;
        this.balance = balance;
    }

    public DepositTM() {
    }
}

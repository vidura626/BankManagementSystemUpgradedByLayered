package lk.ijse.gdse.controller.tm;

import javafx.scene.control.Button;


public class RejectLoansTM {
    String loanID;
    String reason;
    Button button;
    String pendingLoanID;

    public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getPendingLoanID() {
        return pendingLoanID;
    }

    public void setPendingLoanID(String pendingLoanID) {
        this.pendingLoanID = pendingLoanID;
    }

    @Override
    public String toString() {
        return loanID;
    }

    public RejectLoansTM(String loanID, String reason, Button button, String pendingLoanID) {
        this.loanID = loanID;
        this.reason = reason;
        this.button = button;
        this.pendingLoanID = pendingLoanID;
    }

    public RejectLoansTM() {
    }
}

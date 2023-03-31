package lk.ijse.gdse.controller.util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Navigation {
    private static AnchorPane pane;

    public static void setPane(AnchorPane pane){
        Navigation.pane=pane;
    }

    public static void navigate(Routes route) throws IOException {

        pane.getChildren().clear();
        Stage window= (Stage) pane.getScene().getWindow();
        window.setMinWidth(1500.00);
        window.setMinHeight(900.00);
        window.setWidth(1500.00);
        window.setHeight(900.00);
        window.setMaximized(true);
        switch (route) {
            case CREATE_ACCOUNT:
                window.setTitle("Create Account");
                pane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/gdse/view/CreateAccount1.fxml")));
                break;
            case APPROVE_lOANS:
                window.setTitle("Approve Loans");
                initUI("ApproveLoans1.fxml");
                break;
            case DEPOSIT:
                window.setTitle("Deposit");
                initUI("Deposit1.fxml");
                break;
            case INTER_ACCOUNT_TRANSACTION:
                window.setTitle("Inter Account Transaction");
                initUI("InterAccountTransaction1.fxml");
                break;
            case LOAN_INSTALLMENT:
                window.setTitle("Loan Installment");
                initUI("LoanInstallment1.fxml");
                break;
            case MAIN_DASHBOARD:
                window.setTitle("Main Dashboard");
                initUI("MainDashboardForm.fxml");
                break;
            case REJECTED_LOANS:
                window.setTitle("Rejected Loans");
                initUI("RejectLoans1.fxml");
                break;
            case REQUEST_LOANS:
                window.setTitle("Request Loans");
                initUI("RequestLoan1.fxml");
                break;
            case WITHDRAWAL:
                window.setTitle("Withdrawal");
                initUI("Withdraw1.fxml");
                break;
            case REPORT_DASHBOARD:
                window.setTitle("Report_Dashboard");
                initUI("ReportDashboardForm1.fxml");
                break;
            case VIEW_ACCOUNT:
                window.setTitle("View Account");
                initUI("reports/ViewAccountForm.fxml");
                break;
            case VIEW_DAILY_TRANSACTIONS:
                window.setTitle("View_Daily_Transactions");
                initUI("reports/ViewDailyTransactionForm.fxml");
                break;
            case VIEW_DEPOSITS:
                window.setTitle("View_Deposits");
                initUI("reports/ViewDepositForm.fxml");
                break;
            case VIEW_WITHDRAWALS:
                window.setTitle("View_Withdrawals");
                initUI("reports/ViewWithdrawalForm.fxml");
                break;
            case VIEW_LOAN_INSTALLMENTS:
                window.setTitle("View_Loan_Installments");
                initUI("reports/ViewLoanInstallmentForm.fxml");
                break;
            case LOGIN:
                window.setTitle("Login_form");
                initUI("LoginForm.fxml");
                break;
            case VIEW_PENDING_LOANS:
                window.setTitle("View_Pending_Loans");
                initUI("reports/ViewPendingLoansForm.fxml");
                break;
            case VIEW_APPROVED_LOANS:
                window.setTitle("View_Approved_Loans");
                initUI("reports/ViewApprovedLoansForm.fxml");
                break;
            case VIEW_SETTLED_LOANS:
                window.setTitle("View_Settled_Loans");
                initUI("reports/ViewSettledLoansForm.fxml");
                break;
            case LOAN_TYPES_DASHBOARD:
                window.setTitle("Loan_Types_Dashboard");
                initUI("AddLoanDetailsForm.fxml");
                break;
                default:
                new Alert(Alert.AlertType.ERROR, "UI Not Found!").show();
        }
        window.centerOnScreen();
    }
    public static void initUI(String location) throws IOException {
        pane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/gdse/view/"+location)));
    }
}

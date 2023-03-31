package lk.ijse.gdse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.controller.util.Navigation;
import lk.ijse.gdse.controller.util.Routes;

import java.io.IOException;

public class ReportDashboardFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    void btnApprovedLoansOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEW_APPROVED_LOANS);
    }

    @FXML
    void btnDailyTransactionsOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEW_DAILY_TRANSACTIONS);
    }

    @FXML
    void btnDepositsOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEW_DEPOSITS);
    }

    @FXML
    void btnLoanInstallmentsOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEW_LOAN_INSTALLMENTS);
    }

    @FXML
    void btnPendingLoansOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEW_PENDING_LOANS);
    }

    @FXML
    void btnSettledLoansOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEW_SETTLED_LOANS);
    }

    @FXML
    void btnViewAccountsOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEW_ACCOUNT);
    }

    @FXML
    void btnWithdrawalsOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.VIEW_WITHDRAWALS);
    }

}

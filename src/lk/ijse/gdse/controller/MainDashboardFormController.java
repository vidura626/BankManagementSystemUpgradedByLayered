package lk.ijse.gdse.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.controller.util.Navigation;
import lk.ijse.gdse.controller.util.Routes;

import java.io.IOException;

public class MainDashboardFormController {

    @FXML
    private FontAwesomeIconView drg;

    @FXML
    private AnchorPane pane;

    @FXML
    void btnAccountOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.CREATE_ACCOUNT);
    }

    @FXML
    void btnDepositOnAction(ActionEvent event) throws IOException {
        Navigation.navigate((Routes.DEPOSIT));
    }

    public void btnWithdrawalOnAction() throws IOException {
        Navigation.navigate(Routes.WITHDRAWAL);
    }

    public void btnInterAccountTransaction() throws IOException {
        Navigation.navigate(Routes.INTER_ACCOUNT_TRANSACTION);
    }

    public void btynLoanOnAction() throws IOException {
        Navigation.navigate(Routes.REQUEST_LOANS);
    }

    public void btynApproveLoansOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.APPROVE_lOANS);
    }

    public void btnRejectLoansOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.REJECTED_LOANS);
    }

    public void btnReportsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.REPORT_DASHBOARD);
    }

    public void btnLoanInstallmentOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOAN_INSTALLMENT);
    }

    public void btnLoanTypesDashboardOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOAN_TYPES_DASHBOARD);
    }

}

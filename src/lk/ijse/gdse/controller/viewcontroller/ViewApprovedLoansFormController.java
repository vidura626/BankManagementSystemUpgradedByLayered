package lk.ijse.gdse.controller.viewcontroller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.dto.AccountDetailsDTO;
import lk.ijse.gdse.dto.LoansDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.LoansBO;

import java.sql.SQLException;
import java.util.List;

public class ViewApprovedLoansFormController {

    @FXML
    private TableView<LoansDTO> tblLoans;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colLoanTypeID;

    @FXML
    private TableColumn<?, ?> colAccountID;

    @FXML
    private TableColumn<?, ?> colINstallment;

    @FXML
    private TableColumn<?, ?> colIssuedDate;

    @FXML
    private TableColumn<?, ?> colInsDate;

    @FXML
    private TableColumn<?, ?> colRemLoanAmount;

    private final LoansBO loansBO= (LoansBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOANS_BO);

    public void initialize(){
        loanAccountDetails();
    }

    private void loanAccountDetails() {
        setCellValueFactory();
        tblLoans.getItems().clear();
        try {
            List<LoansDTO> allAccounts = loansBO.getAllLoans();
            tblLoans.getItems().addAll(FXCollections.observableArrayList(allAccounts));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colLoanTypeID.setCellValueFactory(new PropertyValueFactory<>("loanTypeId"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        colINstallment.setCellValueFactory(new PropertyValueFactory<>("installments"));
        colIssuedDate.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        colRemLoanAmount.setCellValueFactory(new PropertyValueFactory<>("remainingLoanAmount"));
        colInsDate.setCellValueFactory(new PropertyValueFactory<>("monthlyInstallmentDate"));
    }
}

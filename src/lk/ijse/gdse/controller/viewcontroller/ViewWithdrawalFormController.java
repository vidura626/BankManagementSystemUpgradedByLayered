package lk.ijse.gdse.controller.viewcontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.controller.tm.WithdrawalTM;
import lk.ijse.gdse.dto.DepositAccountDTO;
import lk.ijse.gdse.dto.DepositDetailsDTO;
import lk.ijse.gdse.dto.TransactionDTO;
import lk.ijse.gdse.dto.WithdrawalDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.DepositAccountBO;
import lk.ijse.gdse.service.bo.custom.DepositDetailsBO;
import lk.ijse.gdse.service.bo.custom.TransactionBO;
import lk.ijse.gdse.service.bo.custom.WithdrawalBO;
import java.sql.SQLException;
import java.util.List;

public class ViewWithdrawalFormController {

    @FXML
    private TableView<WithdrawalTM> tblWithdrawal;

    @FXML
    private TableColumn<?, ?> colWithdrawalID;

    @FXML
    private TableColumn<?, ?> colTransactionID;

    @FXML
    private TableColumn<?, ?> colDepositTypeAccountID;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colAccountID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    private final WithdrawalBO withdrawalBO = (WithdrawalBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.WITHDRAWAL_BO);
    private final TransactionBO transactionBO = (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);
    private final DepositDetailsBO depositDetailsBO = (DepositDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_DETAILS_BO);
    private final DepositAccountBO depositAccountBO = (DepositAccountBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_ACCOUNT_BO);

    public void initialize() {
        loanAccountDetails();
    }

    private void loanAccountDetails() {
        setCellValueFactory();
        tblWithdrawal.getItems().clear();
        ObservableList<WithdrawalTM> items = tblWithdrawal.getItems();
        try {
            List<WithdrawalDTO> allWithdraws = withdrawalBO.getAllWithdraws();
            for (WithdrawalDTO withdrawal : allWithdraws) {
                DepositAccountDTO depositAccountById = depositAccountBO.getDepositAccountById(withdrawal.getDepositTypeAccountId());
                TransactionDTO transaction = transactionBO.getTransaction(withdrawal.getTransactionId());
                items.add(new WithdrawalTM(withdrawal.getWithdrawalId(), withdrawal.getTransactionId(), withdrawal.getDepositTypeAccountId(), withdrawal.getAmount(), transaction.getAccountId(), transaction.getDate(), transaction.getTime()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colWithdrawalID.setCellValueFactory(new PropertyValueFactory<>("withdrawalId"));
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colDepositTypeAccountID.setCellValueFactory(new PropertyValueFactory<>("depositType"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }
}

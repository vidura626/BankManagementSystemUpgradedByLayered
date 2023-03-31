package lk.ijse.gdse.controller.viewcontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.dto.DepositDTO;
import lk.ijse.gdse.dto.TransactionDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewDailyTransactionFormController {

    @FXML
    private TableView<TransactionDTO> tblDailyTransaction;

    @FXML
    private TableColumn<?, ?> colTransactionID;

    @FXML
    private TableColumn<?, ?> colAccountID;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private DatePicker datePicker;

    private final TransactionBO transactionBO= (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);
    private final LoanInstalmentBO loanInstalmentBO= (LoanInstalmentBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOAN_INSTALLMENT_BO);
    private final WithdrawalBO withdrawalBO= (WithdrawalBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.WITHDRAWAL_BO);
    private final InterAccountTransactionBO interAccountTransactionBO= (InterAccountTransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.INTER_ACCOUNT_TRANSACTION_BO);
    private final DepositBO depositBO= (DepositBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_BO);

    public void initialize(){
        datePicker.setValue(LocalDate.now());
        setDailyTransactions();
    }

    private void setDailyTransactions() {
        tblDailyTransaction.getItems().clear();
        List<TransactionDTO> allTransactions = null;
        try {
            allTransactions = transactionBO.getAllTransactions();

            List<TransactionDTO> transactionDTOS = setState(allTransactions);
            if (transactionDTOS == null) return;
            List<TransactionDTO> transactionByDate = filterByDate(transactionDTOS);
            setCellValueFactory();
            ObservableList<TransactionDTO> objects = FXCollections.observableArrayList(transactionByDate);
            tblDailyTransaction.setItems(objects);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            setDailyTransactions();
        }

    }

    private List<TransactionDTO> filterByDate(List<TransactionDTO> transactionDTOS) {
        if(datePicker.getValue()==null){
            return transactionDTOS;
        }
        Date date = Date.valueOf(datePicker.getValue());
        return transactionDTOS.stream().filter(transactionDTO -> transactionDTO.getDate().equals(date)).collect(Collectors.toCollection(ArrayList::new));
    }

    private void setCellValueFactory() {
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    private List<TransactionDTO> setState(List<TransactionDTO> allTransactions) {
        for (TransactionDTO transaction : allTransactions) {
            String type = transaction.getType();
            String state="";
            if (type.equals("MONEY IN")) {
                DepositDTO depositsByTransactionId;
                try {
                    depositBO.getDepositsByTransactionId(transaction.getTransactionId());
                    state = "DEPOSIT";
                } catch (NullPointerException e) {
                    state = "LOAN INSTALLMENT";
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (type.equals("MONEY OUT")) {
                state = "WITHDRAW";
            } else if (type.equals("MONEY TRANSFER")) {
                state = "MONEY TRANSFER";
            } else {
                return null;
            }
            transaction.setType(state);
        }
        return allTransactions;
    }

    @FXML
    void datePickerOnAction(ActionEvent event) {
        setDailyTransactions();
    }

    @FXML
    void btnSeeAllOnAction(){
        datePicker.setValue(null);
        setDailyTransactions();
    }
}

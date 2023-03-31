package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse.controller.tm.DepositTM;
import lk.ijse.gdse.controller.util.FormManage;
import lk.ijse.gdse.controller.util.RegexTypes;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.DepositAccountDTO;
import lk.ijse.gdse.dto.DepositDetailsDTO;
import lk.ijse.gdse.dto.TransactionDTO;
import lk.ijse.gdse.dto.WithdrawalDTO;
import lk.ijse.gdse.entity.Deposit;
import lk.ijse.gdse.entity.DepositDetails;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.*;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Withdraw1Controller {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField txtAccNo;

    @FXML
    private Label lblNotifyAccNo;

    @FXML
    private JFXComboBox<DepositDetailsDTO> cmbDepositType;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private Label lblNotifyWithdraw;

    @FXML
    private Label lblTransactionID;

    @FXML
    private TableView<DepositTM> tblWithdraw;

    @FXML
    private TableColumn<?, ?> colWithdrawType;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colBalance;

    private ArrayList<JFXTextField> arrayList=new ArrayList<>();

    private final TransactionBO transactionBO= (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);
    private final WithdrawalBO withdrawalBO= (WithdrawalBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.WITHDRAWAL_BO);
    private final DepositAccountBO depositAccountBO= (DepositAccountBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_ACCOUNT_BO);
    private final DepositDetailsBO depositDetailsBO= (DepositDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_DETAILS_BO);
    private final AccountDetailsBO accountDetailsBO= (AccountDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ACCOUNT_DETAILS_BO);

    public void initialize(){
        //Set transaction ID
        loadTransactionID();
        Collections.addAll(arrayList,txtAccNo,txtAmount);
    }

    private void loadTransactionID() {
        try {
            lblTransactionID.setText(transactionBO.generateNextId());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            boolean validate = FormManage.validate(arrayList, RegexTypes.ACCOUNT_NUMBER, RegexTypes.AMOUNT);
            if (!validate) {
                new Alert(Alert.AlertType.ERROR, "Fill the form correctly").show();
                return;
            }
            //Account filed
            txtAccNo.setEditable(false);
            txtAccNo.setAlignment(Pos.BASELINE_CENTER);
            txtAccNo.setStyle("-fx-background-color: #9a9a9a");

            //Prepare data to add row
            //get account_number and depositTypeID
            String accountNo = txtAccNo.getText();
            String depositTypeID = cmbDepositType.getValue().getDepositTypeId();

            //get and set balance
            double amount = Double.parseDouble(txtAmount.getText());
            DepositAccountDTO depositAccount = depositAccountBO.getDepositAccountsByAccountNumber(accountNo).stream().filter(depositAccountDTO -> depositAccountDTO.getDepositTypeId().equals(depositTypeID)).collect(Collectors.toCollection(ArrayList::new)).get(0);
            double balance = depositAccount.getBalance();
            if(balance<amount){
                new Alert(Alert.AlertType.WARNING,"Account balance is not enough").show();
                txtAmount.setFocusColor(Paint.valueOf("RED"));
                txtAmount.requestFocus();
                return;
            }
            balance -= amount;


            //Set visible table
            colWithdrawType.setCellValueFactory(new PropertyValueFactory<>("depositType"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

            //Add or update rows
            ObservableList<DepositTM> items = tblWithdraw.getItems();
            int index = isNewRow(items, depositTypeID);
            if (index == -1) {
                items.add(new DepositTM(depositTypeID, amount, balance));
            } else {
                items.get(index).setAmount(items.get(index).getAmount() + amount);
                items.get(index).setBalance(items.get(index).getBalance() - amount);
            }
            tblWithdraw.setItems(items);
            tblWithdraw.refresh();
            txtAmount.clear();
            cmbDepositType.getSelectionModel().clearSelection();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private int isNewRow(ObservableList<DepositTM> items, String depositType) {
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getDepositType().equals(depositType)){
                return i;
            }
        }
        return -1;
    }

    private double calculateTotal(TableView<DepositTM> tblDeposit) {
        double sum=0.0;
        for (int i = 0; i < tblDeposit.getItems().size(); i++) {
            sum+=tblDeposit.getItems().get(i).getAmount();
        }
        return sum;
    }

    @FXML
    void btnClearAllOnAction(ActionEvent event) {
        txtAccNo.setEditable(true);
        txtAccNo.setAlignment(Pos.CENTER_LEFT);
        txtAccNo.setStyle(null);
        txtAccNo.clear();
        txtAmount.clear();
        cmbDepositType.getItems().clear();
        tblWithdraw.setItems(FXCollections.observableArrayList());
    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        if(!tblWithdraw.getSelectionModel().isEmpty()){
            tblWithdraw.getItems().remove(tblWithdraw.getSelectionModel().getFocusedIndex());
        }else {
            new Alert(Alert.AlertType.ERROR,"Select a row").show();
        }
    }
    @FXML
    void btnWithdrawOnAction(ActionEvent event) throws SQLException {
        try {
            DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            //Get account number
            String accountNumber = txtAccNo.getText();
            //Make transaction
            String transactionId = transactionBO.generateNextId();
            boolean isTransactionAdded = transactionBO.makeTransaction(new TransactionDTO(
                    transactionId,
                    accountNumber,
                    calculateTotal(tblWithdraw),
                    Date.valueOf(LocalDate.now()),
                    Time.valueOf(LocalTime.now()),
                    TransactionBO.TransactionTypeState.MONEY_OUT
            ));
            if (isTransactionAdded) {

                for (int i = 0; i < tblWithdraw.getItems().size(); i++) {
                    //make withdrawal
                    String depositTypeId = tblWithdraw.getItems().get(i).getDepositType();
                    DepositAccountDTO depositAccount = depositAccountBO.getDepositAccountsByAccountNumber(accountNumber).stream().filter(depositAccountDTO -> depositAccountDTO.getDepositTypeId().equals(depositTypeId)).collect(Collectors.toCollection(ArrayList::new)).get(0);
                    String depositTypeAccountId = depositAccount.getDepositTypeAccountId();
                    boolean isWithdrawalAdded = withdrawalBO.makeWithdraw(new WithdrawalDTO(
                            withdrawalBO.generateNextId(),
                            transactionId,
                            depositTypeAccountId,
                            tblWithdraw.getItems().get(i).getAmount()
                    ));
                    if (isWithdrawalAdded) {
                        //Set account balance
                        double balance = tblWithdraw.getItems().get(i).getBalance();
                        boolean isUpdated = depositAccountBO.setBalanceById(depositTypeAccountId, balance);
                        if (isUpdated) {
                            if (i == tblWithdraw.getItems().size() - 1) {
                                ButtonType ok=new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                                ButtonType no=new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?", ok, no).showAndWait();
                                if(result.orElse(no)==ok){
                                    DbConnection.getDbConnection().getConnection().commit();
                                    new Alert(Alert.AlertType.CONFIRMATION,"Withdrawal Success !").show();
                                    showReport();
                                }else {
                                    DbConnection.getDbConnection().getConnection().rollback();
                                    new Alert(Alert.AlertType.WARNING,"Canceled !").show();
                                }
                                initialize();
                                btnClearAllOnAction(new ActionEvent());
                            }
                        } else {
                            DbConnection.getDbConnection().getConnection().rollback();
                        }
                    }else {
                        DbConnection.getDbConnection().getConnection().rollback();
                    }
                }
            }else {
                DbConnection.getDbConnection().getConnection().rollback();
            }
        } catch (SQLException e) {
            DbConnection.getDbConnection().getConnection().rollback();
            System.out.println(e);
        }finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
    }

    private void showReport() {

    }

    @FXML
    void txtAmountOnAction(ActionEvent event) {

    }

    @FXML
    void txtAmountOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtIdOnKeyReleased(KeyEvent event) {
        try {
            boolean validate = FormManage.check(RegexTypes.ACCOUNT_NUMBER,txtAccNo.getText());
            if (!validate){
                cmbDepositType.setItems(null);
                return;
            }

            loadAccTypes();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
    private void loadAccTypes() throws SQLException, ClassNotFoundException {
        String accountNumber = txtAccNo.getText();
        ObservableList<DepositDetailsDTO> list = FXCollections.observableArrayList();
        List<DepositAccountDTO> depositAccountsByAccountNumber = depositAccountBO.getDepositAccountsByAccountNumber(accountNumber);
        for (DepositAccountDTO depositAccountDTO : depositAccountsByAccountNumber) list.add(depositDetailsBO.getDepositTypeById(depositAccountDTO.getDepositTypeId()));
        list.addAll();
        cmbDepositType.setItems(list);
    }

}

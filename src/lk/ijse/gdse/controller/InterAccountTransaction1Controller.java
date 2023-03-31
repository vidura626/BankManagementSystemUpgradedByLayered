package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse.controller.util.FormManage;
import lk.ijse.gdse.controller.util.RegexTypes;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.*;
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

public class InterAccountTransaction1Controller {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblNotifyTransferMoney;

    @FXML
    private Label lblTransactionID;

    @FXML
    private JFXTextField txtAcc1;

    @FXML
    private Label lblNotifyAcc1;

    @FXML
    private JFXComboBox<DepositDetailsDTO> cmbDepositType1;

    @FXML
    private Label lblNotifyDepositType1;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private Label lblNotifyAmount;

    @FXML
    private JFXTextField txtAcc2;

    @FXML
    private Label lblNotifyAcc2;

    @FXML
    private JFXComboBox<DepositDetailsDTO> cmbDepositType2;

    @FXML
    private Label lblNotifyDepositType2;

    private ArrayList<JFXTextField> arrayList = new ArrayList<>();

    private final DepositBO depositBO= (DepositBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_BO);
    private final TransactionBO transactionBO= (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);
    private final AccountDetailsBO accountDetailsBO= (AccountDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ACCOUNT_DETAILS_BO);
    private final DepositAccountBO depositAccountBO= (DepositAccountBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_ACCOUNT_BO);
    private final DepositDetailsBO depositDetailsBO= (DepositDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_DETAILS_BO);
    private final WithdrawalBO withdrawalBO= (WithdrawalBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.WITHDRAWAL_BO);
    private final InterAccountTransactionBO interAccountTransactionBO= (InterAccountTransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.INTER_ACCOUNT_TRANSACTION_BO);


    public void initialize(){
        Collections.addAll(arrayList,txtAcc1, txtAcc2, txtAmount);
        loadTransactionID();
    }

    private void loadTransactionID() {
        try {
            lblTransactionID.setText(transactionBO.generateNextId());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    void btnClearFormOnAction(ActionEvent event) {
        try {
            for (JFXTextField field : arrayList) field.clear();
            cmbDepositType1.getItems().clear();
            cmbDepositType2.getItems().clear();
        }catch (NullPointerException e){

        }
    }

    @FXML
    void btnTransferOnAction(ActionEvent event) throws SQLException {

        try {

            boolean isValidate = FormManage.validate(arrayList, RegexTypes.ACCOUNT_NUMBER, RegexTypes.ACCOUNT_NUMBER, RegexTypes.AMOUNT);
            boolean isEmpty1 = cmbDepositType1.getSelectionModel().isEmpty();
            boolean isEmpty2 = cmbDepositType2.getSelectionModel().isEmpty();
            if (isEmpty1 || isEmpty2 || !isValidate) {
                new Alert(Alert.AlertType.ERROR,"Check the form again !").show();
                return;
            }
            System.out.println("1");

            //Set auto commit false
            DbConnection.getDbConnection().getConnection().setAutoCommit(false);

            //Get account number
            String accountNumber1 = txtAcc1.getText();
            String accountNumber2 = txtAcc2.getText();

            //Get deposit type and withdrawal type
            String withdrawalTypeId = cmbDepositType1.getSelectionModel().getSelectedItem().getDepositTypeId();
            String depositTypeId = cmbDepositType2.getSelectionModel().getSelectedItem().getDepositTypeId();
            //Make transaction
            String transactionId = transactionBO.generateNextId();
            boolean isTransactionAdded = transactionBO.makeTransaction(new TransactionDTO(
                    transactionId,
                    accountNumber1,
                    Double.parseDouble(txtAmount.getText()),
                    Date.valueOf(LocalDate.now()),
                    Time.valueOf(LocalTime.now()),
                    TransactionBO.TransactionTypeState.MONEY_TRANSFER
            ));
            System.out.println("2");
            if (isTransactionAdded) {
                //Make inter account transaction
                double amount = Double.parseDouble(txtAmount.getText());
                boolean isInterAccountTransactionAdded = interAccountTransactionBO.makeInterAccountTransaction(new InterAccountTransactionDTO(
                        interAccountTransactionBO.generateNextId(),
                        transactionId,
                        accountNumber1,
                        accountNumber2,
                        amount
                ));

                System.out.println("3");
                if (isInterAccountTransactionAdded) {

                    //Make first account withdrawal
                    DepositAccountDTO withdrawAccount = depositAccountBO.getDepositAccountsByAccountNumber(accountNumber1).stream().filter(depositAccountDTO -> depositAccountDTO.getDepositTypeId().equals(withdrawalTypeId)).collect(Collectors.toCollection(ArrayList::new)).get(0);
                    DepositAccountDTO depositAccount = depositAccountBO.getDepositAccountsByAccountNumber(accountNumber2).stream().filter(depositAccountDTO -> depositAccountDTO.getDepositTypeId().equals(depositTypeId)).collect(Collectors.toCollection(ArrayList::new)).get(0);

                    double withdrawAccountBalance = depositAccountBO.getBalanceById(withdrawAccount.getDepositTypeAccountId());
                    if(withdrawAccountBalance<amount){
                        new Alert(Alert.AlertType.ERROR,"Account balance is not enough ! ").show();
                        txtAmount.setFocusColor(Paint.valueOf("RED"));
                        txtAmount.requestFocus();
                        return;
                    }
                    boolean isWithdraw = withdrawalBO.makeWithdraw(new WithdrawalDTO(
                            withdrawalBO.generateNextId(),
                            transactionId,
                            withdrawAccount.getDepositTypeAccountId(),
                            amount
                    ));
                    System.out.println("4");

                    if (isWithdraw) {
                        boolean isUpdatedWithdrawBalance = depositAccountBO.setBalanceById(withdrawAccount.getDepositTypeAccountId(), withdrawAccountBalance-amount);
                        if (isUpdatedWithdrawBalance) {
                            boolean isDepositAdded = depositBO.makeDeposit(new DepositDTO(
                                    depositBO.generateNextId(),
                                    transactionId,
                                    depositAccount.getDepositTypeAccountId(),
                                    amount
                            ));
                            if (isDepositAdded) {
                                System.out.println(isDepositAdded);
                                System.out.println("isDepositAdded");
                                boolean isUpdatedDepositBalance = depositAccountBO.setBalanceById(depositAccount.getDepositTypeAccountId(), depositAccount.getBalance() + amount);
                                System.out.println(isUpdatedDepositBalance);
                                if (isUpdatedDepositBalance) {
                                    System.out.println("isUpdatedDepositBalance");
                                    ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure ? ", ok, cancel);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.orElse(cancel) == ok) {
                                        DbConnection.getDbConnection().getConnection().commit();
                                        new Alert(Alert.AlertType.CONFIRMATION,"Inter account transaction successfully ! ").show();

                                    } else {
                                        DbConnection.getDbConnection().getConnection().rollback();
                                        new Alert(Alert.AlertType.ERROR,"Canceled ! ").show();
                                    }
                                    btnClearFormOnAction(new ActionEvent());
                                    initialize();
                                } else {
                                    DbConnection.getDbConnection().getConnection().rollback();
                                }
                            } else {
                                DbConnection.getDbConnection().getConnection().rollback();
                            }
                        } else {
                            DbConnection.getDbConnection().getConnection().rollback();
                        }
                    } else {
                        DbConnection.getDbConnection().getConnection().rollback();
                    }
                } else {
                    DbConnection.getDbConnection().getConnection().rollback();
                }
            }else {
                DbConnection.getDbConnection().getConnection().rollback();
            }

        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }

    }

    @FXML
    void txtAcc1OnKeyReleased(KeyEvent event) {
        try {
            boolean validate = FormManage.check(RegexTypes.ACCOUNT_NUMBER, txtAcc1.getText());
            if(!validate){
                txtAcc1.setFocusColor(Paint.valueOf("red"));
                txtAcc1.requestFocus();
                cmbDepositType1.setItems(null);
                return;
            }

            loadAccTypes(txtAcc1, cmbDepositType1);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private void loadAccTypes(JFXTextField txtAcc, JFXComboBox<DepositDetailsDTO> cmbDepositType) throws SQLException, ClassNotFoundException {
        String accountNumber = txtAcc.getText();
        ObservableList<DepositDetailsDTO> accTypes = FXCollections.observableArrayList();
        List<DepositAccountDTO> depositAccountsByAccountNumber = depositAccountBO.getDepositAccountsByAccountNumber(accountNumber);
        for (DepositAccountDTO depositAccountDTO : depositAccountsByAccountNumber) accTypes.add(depositDetailsBO.getDepositTypeById(depositAccountDTO.getDepositTypeId()));
        cmbDepositType.setItems(accTypes);
    }
    @FXML
    void txtAcc2OnKeyReleased(KeyEvent event) {
        try {
            boolean validate = FormManage.check(RegexTypes.ACCOUNT_NUMBER, txtAcc2.getText());
            if(!validate){
                txtAcc2.setFocusColor(Paint.valueOf("red"));
                txtAcc2.requestFocus();
                cmbDepositType2.setItems(null);
                return;
            }

            loadAccTypes(txtAcc2, cmbDepositType2);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @FXML
    void txtAmountOnAction(ActionEvent event) {

    }

    @FXML
    void txtAmountOnKeyReleased(KeyEvent event) {

    }

}

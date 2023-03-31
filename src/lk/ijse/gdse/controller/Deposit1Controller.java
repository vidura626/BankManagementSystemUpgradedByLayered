package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.gdse.controller.tm.DepositTM;
import lk.ijse.gdse.controller.util.FormManage;
import lk.ijse.gdse.controller.util.RegexTypes;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.DepositAccountDTO;
import lk.ijse.gdse.dto.DepositDTO;
import lk.ijse.gdse.dto.DepositDetailsDTO;
import lk.ijse.gdse.dto.TransactionDTO;
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

public class Deposit1Controller {

    @FXML
    private JFXButton btnDeposit;

    @FXML
    private JFXTextField txtAccNo;

    @FXML
    private Label lblNotifyAccNo;

    @FXML
    private JFXComboBox<DepositDetailsDTO> cmbDepositType;

    @FXML
    private Label lblNotifyAccType;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private Label lblNotifyAmount;

    @FXML
    private Label lblNotifyAdd;

    @FXML
    private Label lblNotifyDeposit;

    @FXML
    private Label lblTransactionID;

    @FXML
    private TableView<DepositTM> tblDeposit;

    @FXML
    private TableColumn<DepositTM, DepositAccountDTO> colDepositType;

    @FXML
    private TableColumn<DepositTM, Double> colAmount;

    @FXML
    private TableColumn<DepositTM, Double> colBalance;

    private final DepositBO depositBO= (DepositBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_BO);
    private final TransactionBO transactionBO= (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);
    private final AccountDetailsBO accountDetailsBO= (AccountDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ACCOUNT_DETAILS_BO);
    private final DepositAccountBO depositAccountBO= (DepositAccountBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_ACCOUNT_BO);
    private final DepositDetailsBO depositDetailsBO= (DepositDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_DETAILS_BO);

    public void initialize(){
        loadTransactionId();
        txtAccNo.setEditable(true);

    }

    private void loadTransactionId() {
        try {
            lblTransactionID.setText(transactionBO.generateNextId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        ArrayList<JFXTextField> textFields = new ArrayList<>();
        Collections.addAll(textFields,txtAccNo,txtAmount);
        boolean validate = FormManage.validate(textFields, RegexTypes.ACCOUNT_NUMBER, RegexTypes.AMOUNT);
        if (!(validate && !cmbDepositType.getSelectionModel().isEmpty())) {
            new Alert(Alert.AlertType.WARNING, "Please check the form again !").show();
            return;
        }
        try{

                //Account filed
                txtAccNo.setEditable(false);
                txtAccNo.setAlignment(Pos.BASELINE_CENTER);
                txtAccNo.setStyle("-fx-background-color: #9a9a9a");

                //Prepare data to add row
                //get account_number and depositTypeID
            String accountNo = txtAccNo.getText();
            String depositTypeID = cmbDepositType.getValue().getDepositTypeId();
            DepositAccountDTO depositAccount = depositAccountBO.getDepositAccountsByAccountNumber(accountNo).stream().filter(depositAccountDTO -> depositAccountDTO.getDepositTypeId().equals(depositTypeID)).collect(Collectors.toCollection(ArrayList::new)).get(0);
            //get and set balance
                double balance = depositAccountBO.getBalanceById(depositAccount.getDepositTypeAccountId());
                double amount = Double.parseDouble(txtAmount.getText());
                balance+=amount;
                String depositType = cmbDepositType.getValue().getDepositTypeId();

                //Set visible table
                setCellValueFactory();

                //Add or update rows
                ObservableList<DepositTM> items = tblDeposit.getItems();
                int index = isNewRow(items, depositType);
                if (index==-1) {
                    items.add(new DepositTM(depositType, amount, balance));
                } else {
                    items.get(index).setAmount(items.get(index).getAmount() + amount);
                    items.get(index).setBalance(items.get(index).getBalance() + amount);
                }
                tblDeposit.setItems(items);
                tblDeposit.refresh();
                refreshForm();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void refreshForm() {
        cmbDepositType.getSelectionModel().clearSelection();
        txtAmount.clear();
    }

    private int isNewRow(ObservableList<DepositTM> items, String depositType) {
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getDepositType().equals(depositType)){
                return i;
            }
        }
        return -1;
    }

    private void showReport() {
        System.out.println("Show Report");
    }

    @FXML
    void btnClearAllOnAction(ActionEvent event) {
        txtAccNo.setEditable(true);
        txtAccNo.setAlignment(Pos.CENTER_LEFT);
        txtAccNo.setStyle(null);
        txtAccNo.clear();
        txtAmount.clear();
        cmbDepositType.getItems().clear();
        tblDeposit.setItems(FXCollections.observableArrayList());
    }

    @FXML
    void btnClearFormOnAction(ActionEvent event) {

    }

    private double calculateTotal(TableView<DepositTM> tblDeposit) {
        double sum=0.0;
        for (int i = 0; i < tblDeposit.getItems().size(); i++) {
            sum+=tblDeposit.getItems().get(i).getAmount();
        }
        return sum;
    }

    @FXML
    void btnDepositOnAction(ActionEvent event) throws SQLException {
        try {
            DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            //Get account number
            String accountNumber = txtAccNo.getText();

            //Make transaction
            String transactionId = transactionBO.generateNextId();
            boolean isTransactionAdded = transactionBO.makeTransaction(new TransactionDTO(
                    transactionId,
                    accountNumber,
                    calculateTotal(tblDeposit),
                    Date.valueOf(LocalDate.now()),
                    Time.valueOf(LocalTime.now()),
                    TransactionBO.TransactionTypeState.MONEY_IN
            ));

            if (isTransactionAdded) {

                for (int i = 0; i < tblDeposit.getItems().size(); i++) {
                    //make deposit
                    String depositType = tblDeposit.getItems().get(i).getDepositType();
                    double amount = tblDeposit.getItems().get(i).getAmount();
                    double balance = tblDeposit.getItems().get(i).getBalance();

                    DepositAccountDTO depositAccount = depositAccountBO.getDepositAccountsByAccountNumber(accountNumber).stream().filter(depositAccountDTO -> depositAccountDTO.getDepositTypeId().equals(depositType)).collect(Collectors.toCollection(ArrayList::new)).get(0);
                    String depositTypeAccountId = depositAccount.getDepositTypeAccountId();
                    boolean isAddedDeposit = depositBO.makeDeposit(new DepositDTO(
                            depositBO.generateNextId(),
                            transactionId,
                            depositTypeAccountId,
                            amount
                    ));
                    if (isAddedDeposit) {
                        //Set account balance
                        boolean isUpdatedAccountBalance = depositAccountBO.setBalanceById(depositTypeAccountId, balance);
                        if (isUpdatedAccountBalance) {
                            if (i == tblDeposit.getItems().size() - 1) {
                                ButtonType ok=new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                                ButtonType no=new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?", ok, no).showAndWait();
                                if(result.orElse(no)==ok){
                                    DbConnection.getDbConnection().getConnection().commit();
                                    new Alert(Alert.AlertType.CONFIRMATION,"Deposit Success !").show();
                                    showReport();
                                }else {
                                    DbConnection.getDbConnection().getConnection().rollback();
                                    new Alert(Alert.AlertType.WARNING,"Canceled !").show();
                                }
                                btnClearAllOnAction(event);
                                initialize();
                            }
                        } else {
                            DbConnection.getDbConnection().getConnection().rollback();
                            return;
                        }
                    }else {
                        DbConnection.getDbConnection().getConnection().rollback();
                        return;
                    }
                }
            }else {
                DbConnection.getDbConnection().getConnection().rollback();
            }
        } catch (SQLException e) {
            DbConnection.getDbConnection().getConnection().rollback();
            System.out.println(e);
        } finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
    }

    @FXML
    void btnPrintlOnAction(ActionEvent event) {

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        if(!tblDeposit.getSelectionModel().isEmpty()){
            tblDeposit.getItems().remove(tblDeposit.getSelectionModel().getFocusedIndex());
        }else {
            new Alert(Alert.AlertType.ERROR,"Select a row").show();
        }
    }

    @FXML
    void txtAmountOnAction(ActionEvent event) {

    }

    @FXML
    void txtAmountOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtIdOnKeyReleased(KeyEvent event) {
        if (txtAccNo.getText().isEmpty()) {
            cmbDepositType.getItems().clear();
            return;
        }
        if (!FormManage.check(RegexTypes.ACCOUNT_NUMBER, txtAccNo.getText())) {
            new Alert(Alert.AlertType.INFORMATION,"Check the typed text again !").show();
            return;
        }
        try {
            if (!accountDetailsBO.isExistAndActive(txtAccNo.getText())) {
                new Alert(Alert.AlertType.ERROR,"Account is not found !").show();
                return;
            }
            String accountNumber = txtAccNo.getText();
            List<DepositAccountDTO> depositAccountsByAccountNumber = depositAccountBO.getDepositAccountsByAccountNumber(accountNumber);
            ObservableList<DepositDetailsDTO> list = FXCollections.observableArrayList();
            for (DepositAccountDTO depositAccountDTO : depositAccountsByAccountNumber) list.add(depositDetailsBO.getDepositTypeById(depositAccountDTO.getDepositTypeId()));
            cmbDepositType.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colDepositType.setCellValueFactory(new PropertyValueFactory<>("depositType"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

}

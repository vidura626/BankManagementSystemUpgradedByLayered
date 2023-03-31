package lk.ijse.gdse.controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import lk.ijse.gdse.controller.tm.TableDetailsCloseAccBalances;
import lk.ijse.gdse.controller.tm.TableDetailsCloseLoanBalances;
import lk.ijse.gdse.controller.util.FormManage;
import lk.ijse.gdse.controller.util.RegexTypes;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.*;
import lk.ijse.gdse.entity.AccountDetails;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.*;
import lk.ijse.gdse.service.bo.custom.imple.DepositBOImple;
import lk.ijse.gdse.service.bo.util.Convert;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CreateAccount1Controller {

    @FXML
    private JFXTabPane pane;

    @FXML
    private Tab tabCreateAccount;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private Label lblNotifyNic;

    @FXML
    private JFXTextField txtName;

    @FXML
    private Label lblNotifyName;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private Label lblNotifyAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private Label lblNotifyContact;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private Label lblNotifyEmail;

    @FXML
    private JFXTextField txtDob;

    @FXML
    private JFXDatePicker datePickerDob;

    @FXML
    private Label lblNotifyDob;

    @FXML
    private HBox hBoxGender;

    @FXML
    private JFXRadioButton radioButtonMale;

    @FXML
    private ToggleGroup toggleGroupGender;

    @FXML
    private JFXRadioButton radioButtonFemale;

    @FXML
    private JFXRadioButton radioButtonOther;

    @FXML
    private Label lblNotifyGender;

    @FXML
    private JFXTextField txtAccNoo;

    @FXML
    private JFXComboBox<DepositDetailsDTO> cmbAccTypes;

    @FXML
    private Label lblNotifyAccType;

    @FXML
    private JFXTextField txtDate;

    @FXML
    private JFXTextField txtTime;

    @FXML
    private JFXTextField txtFirstDeposit;

    @FXML
    private Label lblNotifyFirstDeposit;

    @FXML
    private Label lblNotifyCreateAccount;

    @FXML
    private AnchorPane paneSearchUpdate;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private Label lblNotifySearchUpdate;

    @FXML
    private AnchorPane paneSearchUpdate1;

    @FXML
    private JFXTextField txtFindAccNo;

    @FXML
    private JFXButton btnFindAccNo;

    @FXML
    private Label lblNotifyFindAccNo;

    @FXML
    private Tab tabCloseAccount;

    @FXML
    private JFXTextField txtAccNumb;

    @FXML
    private Label lblAccNumberr;

    @FXML
    private Label lblNicc;

    @FXML
    private Label lblNamee;

    @FXML
    private Label lblAddresss;

    @FXML
    private Label lblContactt;

    @FXML
    private Label lblDobb;

    @FXML
    private Label lblEmaill;

    @FXML
    private Label lblGenderr;

    @FXML
    private Label lblRegDatee;

    @FXML
    private Label lblRegTimee;

    @FXML
    private TableView<TableDetailsCloseAccBalances> tblAccBalancee;

    @FXML
    private TableColumn<?, ?> colDeposittType;

    @FXML
    private TableColumn<?, ?> colBalancee;

    @FXML
    private TableColumn<?, ?> colActionn1;

    @FXML
    private TableView<TableDetailsCloseLoanBalances> tblLoansAvailables;

    @FXML
    private TableColumn<?, ?> colLoanTypee;

    @FXML
    private TableColumn<?, ?> colLoanBalancee;

    @FXML
    private TableColumn<?, ?> colActionn2;

    @FXML
    private JFXButton btnCloseeAccount;

    @FXML
    private Tab tabViewAccount;

    @FXML
    private TableView<AccountDetailsDTO> tblAccounts;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colRegDate;

    @FXML
    private TableColumn<?, ?> colRegTime;

    @FXML
    private TextField txtSelectedID;

    @FXML
    private Tab tabViewAccount1;

    @FXML
    private TableView<DepositAccountDTO> tblDepositAccount;

    @FXML
    private TableColumn<?, ?> colDepositTypeAccountID;

    @FXML
    private TableColumn<?, ?> colState;

    @FXML
    private TableColumn<?, ?> colDepositTypeID;

    @FXML
    private TableColumn<?, ?> colAccountID;

    @FXML
    private TableColumn<?, ?> colCreatedDate;

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private JFXTextField txtAccNo;

    @FXML
    private Label lblNotifyAccNo;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtInterest;

    @FXML
    private JFXComboBox<DepositDetailsDTO> cmbDepositType;

    private ArrayList<JFXTextField> textFields=new ArrayList<>();

    private final DepositDetailsBO depositDetailsBO= (DepositDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_DETAILS_BO);
    private final AccountDetailsBO accountDetailsBO= (AccountDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ACCOUNT_DETAILS_BO);
    private final DepositAccountBO depositAccountBO= (DepositAccountBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_ACCOUNT_BO);
    private final DepositBO depositBO= (DepositBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_BO);
    private final TransactionBO transactionBO= (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);
    private final LoansBO loansBO= (LoansBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOANS_BO);
    private final LoanInstalmentBO loanInstalmentBO = (LoanInstalmentBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOAN_INSTALLMENT_BO);
    private final WithdrawalBO withdrawalBO = (WithdrawalBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.WITHDRAWAL_BO);

    public void initialize(){
        try {
            btnUpdate.setDisable(true);
            Collections.addAll(textFields, txtNic,txtName,txtAddress,txtContact,txtEmail,txtDob,txtFirstDeposit,txtFindAccNo,txtSearch,txtDate,txtTime,txtAccNoo);
            loadDepositTypes();
            loadViewAccount();
            loadViewDepositAccounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadViewDepositAccounts() {
        try {
            setCellValueFactoryDepositAccount();
            ObservableList<DepositAccountDTO> list = FXCollections.observableArrayList();
            List<AccountDetailsDTO> active = accountDetailsBO.getAccountsByState("ACTIVE");
            List<DepositAccountDTO> allDepositAccounts = null;
            for(AccountDetailsDTO accountDetailsDTO : active){
                List<DepositAccountDTO> depositAccountsByAccountNumber = depositAccountBO.getDepositAccountsByAccountNumber(accountDetailsDTO.getAccountId());
                list.addAll(depositAccountsByAccountNumber);
            }
//            list.addAll(allDepositAccounts);
            tblDepositAccount.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactoryDepositAccount() {
        colDepositTypeAccountID.setCellValueFactory(new PropertyValueFactory<>("depositTypeAccountId"));
        colDepositTypeID.setCellValueFactory(new PropertyValueFactory<>("depositTypeId"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        colCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    private void loadViewAccount() throws SQLException {
        setCellValueFactoryy();
        List<AccountDetailsDTO> allAccounts = accountDetailsBO.getAllAccounts();
        ObservableList<AccountDetailsDTO> accounts = FXCollections.observableArrayList();
        for (AccountDetailsDTO allAccount : allAccounts) {
            accounts.add(new AccountDetailsDTO(
                    allAccount.getAccountId(),
                    allAccount.getNic(),
                    allAccount.getName(),
                    allAccount.getAddress(),
                    allAccount.getContact(),
                    allAccount.getDateOfBirth(),
                    allAccount.getEmail(),
                    allAccount.getGender(),
                    allAccount.getRegDate(),
                    allAccount.getRegTime(),
                    allAccount.getState()
            ));
        }
        tblAccounts.setItems(accounts);
    }

    private void setCellValueFactoryy() {
        colID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        colRegTime.setCellValueFactory(new PropertyValueFactory<>("regTime"));
        colState.setCellValueFactory(new PropertyValueFactory<>("state"));
    }

    private void loadDepositTypes() throws SQLException {
        List<DepositDetailsDTO> allDepositTypes = depositDetailsBO.getAllDepositTypes();
        ObservableList<DepositDetailsDTO> ob= FXCollections.observableArrayList();
        for (DepositDetailsDTO detailsDTO: allDepositTypes){
            ob.add(detailsDTO);
        }
        cmbAccTypes.setItems(ob);
        cmbDepositType.setItems(ob);
    }

    @FXML
    void btnClearFormOnAction(ActionEvent event) {
        try {
            cmbAccTypes.setDisable(false);
            txtFirstDeposit.setDisable(false);
            btnUpdate.setDisable(true);
            for (JFXTextField field : textFields) field.clear();
            cmbAccTypes.getSelectionModel().clearSelection();
            toggleGroupGender.getSelectedToggle().setSelected(false);
        }catch (ConcurrentModificationException | NullPointerException e){
        }
    }

    @FXML
    void btnCloseAccounttOnAction(ActionEvent event) {
        try {
            ObservableList<TableDetailsCloseAccBalances> items = tblAccBalancee.getItems();
            ObservableList<TableDetailsCloseLoanBalances> loans = tblLoansAvailables.getItems();

            for (int i = 0; i < loans.size(); i++) {
                if (!loans.get(i).getButton().disableProperty().getValue()) {
                    return;
                }
            }
            for (int i = 0; i < items.size(); i++) {
                if(!items.get(i).getButton().disableProperty().getValue()){
                    return;
                }
            }

            ButtonType ok = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to close your account ?", ok, no).showAndWait();
            if (result.orElse(no) == ok) {
                boolean isUpdated = accountDetailsBO.closeAccount(txtAccNumb.getText());
                new Alert(Alert.AlertType.CONFIRMATION,"Account closed !").show();
                txtAccNumb.clear();
                txtAccNumb.requestFocus();
            } else {
                new Alert(Alert.AlertType.ERROR,"canceled !").show();
            }
        } catch (SQLException | NoCodeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnCopyOnAction(ActionEvent event) {
        if (!txtSelectedID.getText().isEmpty()) {
            StringSelection stringSelection = new StringSelection(txtSelectedID.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            new Alert(Alert.AlertType.CONFIRMATION, "Copied !").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Select Id not found !").show();
        }
    }

    @FXML
    void btnCreateAccountOnAction(ActionEvent event) throws SQLException {
        String accType=cmbAccTypes.getSelectionModel().isEmpty()?null:cmbAccTypes.getSelectionModel().getSelectedItem().getDepositTypeId();
        String gender=radioButtonMale.isSelected()?"MALE":radioButtonFemale.isSelected()?"FEMALE":radioButtonOther.isSelected()?"OTHER":null;
        boolean validate = FormManage.validate(textFields, RegexTypes.NIC, RegexTypes.NAME, RegexTypes.ADDRESS, RegexTypes.CONTACT, RegexTypes.EMAIL, RegexTypes.DATE,RegexTypes.AMOUNT);
//        boolean validate = true;

        if (!validate || accType==null || gender==null) {
            new Alert(Alert.AlertType.WARNING,"Please fill the form correctly....").show();
            return;
        }
        String accountNumber = accountDetailsBO.generateAccountNumber();
        String nic = txtNic.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        Date dob = Date.valueOf(txtDob.getText());
        String email = txtEmail.getText();
        Date date = Date.valueOf(LocalDate.now());
        Time time= Time.valueOf(LocalTime.now());
        String state="ACTIVE";

        String depositTypeId = cmbAccTypes.getSelectionModel().getSelectedItem().getDepositTypeId();
        double amount = Double.parseDouble(txtFirstDeposit.getText());
        try {
            DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            //Create account
            boolean isCreated = accountDetailsBO.creatAccount(new AccountDetailsDTO(accountNumber, nic, name, address, contact, dob, email, gender, date, time, state));
            if (isCreated) {
                //Create Deposit Account
                String depositTypeAcoountId = depositAccountBO.generateNextId();
                boolean isCreatedDepositAccount = depositAccountBO.addDepositAccount(new DepositAccountDTO(depositTypeAcoountId, depositTypeId, accountNumber, date, amount));
                if (isCreatedDepositAccount) {
                    //Make First Deposit Transaction
                    String transactionId = transactionBO.generateNextId();
                    boolean isCreatedTransaction = transactionBO.makeTransaction(new TransactionDTO(transactionId, accountNumber, amount, date, time, TransactionBO.TransactionTypeState.MONEY_IN));
                    if(isCreatedTransaction){
                        //Make First Deposit
                        boolean isDeposited = depositBO.makeDeposit(new DepositDTO(depositBO.generateNextId(), transactionId, depositTypeAcoountId, amount));
                        if (isDeposited) {
                            finalTransactionWithDatabase("Account Created Successfully !","Canceled !");
                            initialize();
                        }
                    }
                } else {
                    DbConnection.getDbConnection().getConnection().rollback();
                }
            } else {
                DbConnection.getDbConnection().getConnection().rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }

    }

    void finalTransactionWithDatabase(String confirmMessage,String cancelMessage) throws SQLException {
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure ? ", ok, cancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(cancel) == ok) {
            new Alert(Alert.AlertType.CONFIRMATION, confirmMessage).show();
            DbConnection.getDbConnection().getConnection().commit();
            btnClearFormOnAction(new ActionEvent());
        } else {
            new Alert(Alert.AlertType.ERROR, cancelMessage).show();
            DbConnection.getDbConnection().getConnection().rollback();
        }
    }

    @FXML
    void btnFindAccNoOnAction(ActionEvent event) {
        if(txtFindAccNo.getText().isEmpty())return;
        if (!btnFindAccNo.getText().equals("Find account No")){
            btnFindAccNo.setText("Find account No");
            txtFindAccNo.clear();
            return;
        }
        String nic = txtFindAccNo.getText();
        boolean check = FormManage.check(RegexTypes.NIC, nic);
        try {
            if (!check) {
                new Alert(Alert.AlertType.WARNING, "Please check typed text again !").show();
                txtFindAccNo.setFocusColor(Paint.valueOf("RED"));
                txtFindAccNo.requestFocus();
                return;
            }
            String accountNumber = accountDetailsBO.getAccountByNic(nic).getAccountId();
            boolean isExists = accountDetailsBO.getAccountByNic(nic)!=null;
            if (!isExists) {
                new Alert(Alert.AlertType.ERROR, "Account is not found !").show();
                return;
            }
            boolean isActive = accountDetailsBO.isActive(accountNumber);
            if (!isActive) {
                new Alert(Alert.AlertType.INFORMATION, "Account is closed !").show();
                return;
            }
            btnFindAccNo.setText("Founded !");
            txtFindAccNo.setText(accountNumber);
            txtFindAccNo.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateDepositTypeOnAction(ActionEvent event) throws SQLException {
        if(cmbDepositType.getSelectionModel().isEmpty()){
            return;
        }
        DepositDetailsDTO selectedItem = cmbDepositType.getSelectionModel().getSelectedItem();
        boolean b = depositDetailsBO.changeInterest(selectedItem.getDepositTypeId(), Double.parseDouble(txtInterest.getText()));
        if (b) {
            new Alert(Alert.AlertType.CONFIRMATION, "Interst is changed !").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION,"Interst is not changed !").show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        try {
            AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO(
                    txtAccNo.getText(),
                    txtNic.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContact.getText(),
                    Date.valueOf(txtDob.getText()),
                    txtEmail.getText(),
                    toggleGroupGender.getSelectedToggle() == radioButtonMale ? "MALE" : toggleGroupGender.getSelectedToggle() == radioButtonFemale ? "FEMALE" : "OTHER",
                    Date.valueOf(txtDate.getText()),
                    Time.valueOf(txtTime.getText()),
                    "ACTIVE"
            );
            DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            boolean isUpdated = accountDetailsBO.updateAccount(accountDetailsDTO);
            if (isUpdated) {
                ButtonType ok = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ? ", ok, cancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.orElse(cancel) == ok) {
                    DbConnection.getDbConnection().getConnection().commit();
                    new Alert(Alert.AlertType.CONFIRMATION, "Update Success !").show();
                    btnClearFormOnAction(new ActionEvent());
                    txtFirstDeposit.setDisable(false);
                    cmbAccTypes.setDisable(false);
                } else {
                    btnClearFormOnAction(new ActionEvent());
                    DbConnection.getDbConnection().getConnection().rollback();
                    new Alert(Alert.AlertType.ERROR, "Update canceled !").show();
                }
            } else {
                DbConnection.getDbConnection().getConnection().rollback();
                new Alert(Alert.AlertType.ERROR, "Update failed !").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (NoCodeException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }

    }

    @FXML
    void btnViewDepositTypeOnAction(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        stage.setTitle("Deposit Types");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/gdse/view/ViewDepositTypesForm.fxml"))));
        if (stage.isShowing()) {
            stage.hide();
        } else {
            stage.show();
        }
    }

    @FXML
    void cmbAccTypesOnAction(ActionEvent event) {

    }

    @FXML
    void cmbDepositTypeOnAction(ActionEvent event) {
        if(cmbDepositType.getSelectionModel().isEmpty())return;
        txtDescription.setText(cmbDepositType.getValue().getDescription());
        txtInterest.setText(String.valueOf(cmbDepositType.getValue().getInterest()));
    }

    @FXML
    void datePickerOnAction(ActionEvent event) {
        txtDob.setText(datePickerDob.getValue().toString());
    }

    @FXML
    void onPasteOnAction(ActionEvent event) {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = c.getContents(this);
        if (t == null)
            return;
        try {
            txtSearch.setText((String) t.getTransferData(DataFlavor.stringFlavor));
            txtSearchOnKeyReleased();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void radioButtonGenderOnAction(ActionEvent event) {

    }

    @FXML
    void tblAccountsOnClicks(MouseEvent event) {
        if (!tblAccounts.getSelectionModel().isEmpty()) {
            txtSelectedID.setText(tblAccounts.getSelectionModel().getSelectedItem().getAccountId());
        } else {
            txtSelectedID.clear();
        }
    }

    @FXML
    void txtAccNoOnAction(ActionEvent event) {
        try {
            if(txtAccNo.getText().isEmpty()){
                setDepositAccounts();
                return;
            }
            boolean check = FormManage.check(RegexTypes.ACCOUNT_NUMBER, txtAccNo.getText());
            String accountNumber = txtAccNo.getText();
            if(!check) {
                new Alert(Alert.AlertType.ERROR, "Check account number again !").show();
                setDepositAccounts();
                return;
            }
            if(!accountDetailsBO.isExistAndActive(accountNumber)){
                new Alert(Alert.AlertType.ERROR, "Account not found !").show();
                return;
            }

            AccountDetailsDTO account = accountDetailsBO.getAccountByAccountNumber(accountNumber);
            setDepositAccountsforID(account.getAccountId());

        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    private void setDepositAccountsforID(String accountId) {
        try {
            ObservableList<DepositAccountDTO> list=FXCollections.observableArrayList();
            ArrayList<DepositAccountDTO> collect = depositAccountBO.getAllDepositAccounts().stream().filter(depositAccountDTO -> depositAccountDTO.getAccountId().equals(accountId)).collect(Collectors.toCollection(ArrayList::new));
            list.addAll(collect);
            tblDepositAccount.setItems(list);
            tblDepositAccount.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setDepositAccounts() {
        try {
            setCellValueFactoryDepositAccount();
            ObservableList<DepositAccountDTO> list = FXCollections.observableArrayList();
            List<AccountDetailsDTO> active = accountDetailsBO.getAccountsByState("ACTIVE");
            List<DepositAccountDTO> allDepositAccounts = null;
            for(AccountDetailsDTO accountDetailsDTO : active){
                List<DepositAccountDTO> depositAccountsByAccountNumber = depositAccountBO.getDepositAccountsByAccountNumber(accountDetailsDTO.getAccountId());
                list.addAll(depositAccountsByAccountNumber);
            }
            tblDepositAccount.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtAccNoOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtAccNumbOnKeyReleased() {
        try {
            boolean check = FormManage.check(RegexTypes.ACCOUNT_NUMBER, txtAccNumb.getText());
            if (txtAccNumb.getText().isEmpty())return;
            if(!check) {
                lblAccNumberr.setText("");
                lblNicc.setText("");
                lblNamee.setText("");
                lblAddresss.setText("");
                lblContactt.setText("");
                lblDobb.setText("");
                lblEmaill.setText("");
                lblGenderr.setText("");
                lblRegDatee.setText("");
                lblRegTimee.setText("");
                tblAccBalancee.getItems().clear();
                tblLoansAvailables.getItems().clear();
                return;
            }
            String accountNumber = txtAccNumb.getText();
            boolean existAndActive = accountDetailsBO.isExistAndActive(accountNumber);
            if (!existAndActive) {
                new Alert(Alert.AlertType.ERROR,"Account is not found !").show();
                return;
            }
            AccountDetailsDTO account = accountDetailsBO.getAccountByAccountNumber(accountNumber);
            lblAccNumberr.setText(account.getAccountId());
            lblNicc.setText(account.getNic());
            lblNamee.setText(account.getName());
            lblAddresss.setText(account.getAddress());
            lblContactt.setText(account.getContact());
            lblDobb.setText(String.valueOf(account.getDateOfBirth()));
            lblEmaill.setText(account.getEmail());
            lblGenderr.setText(account.getGender());
            lblRegDatee.setText(String.valueOf(account.getRegDate()));
            lblRegTimee.setText(String.valueOf(account.getRegTime()));

            //setCellValueFactory

            // Table tblAccBalancee TableDetailsCloseAccBalances
            colDeposittType.setCellValueFactory(new PropertyValueFactory<>("depositTypeID"));
            colBalancee.setCellValueFactory(new PropertyValueFactory<>("balance"));
            colActionn1.setCellValueFactory(new PropertyValueFactory<>("button"));

            // Table tblLoansAvailables TableDetailsCloseLoanBalances
            colLoanTypee.setCellValueFactory(new PropertyValueFactory<>("loanType"));
            colLoanBalancee.setCellValueFactory(new PropertyValueFactory<>("balance"));
            colActionn2.setCellValueFactory(new PropertyValueFactory<>("button"));
            //

            //get data into tblLoansAvailables
            List<LoansDTO> allLoans = loansBO.getLoansByAccountNumber(accountNumber);

            ObservableList<LoansDTO> loans = FXCollections.observableArrayList(allLoans);
            ObservableList<TableDetailsCloseLoanBalances> list = FXCollections.observableArrayList();
            for (LoansDTO l : loans) {
                JFXButton fix = new JFXButton("Fix");
                fix.setOnAction(e -> {
                    if (!tblLoansAvailables.getSelectionModel().isEmpty()) {
                        try {
                            settleSelectedLoan(loans.get(tblLoansAvailables.getSelectionModel().getSelectedIndex()));
                        } catch (SQLException | ClassNotFoundException xe) {
                            xe.printStackTrace();
                        }
                    }
                });
                if (l.getRemainingLoanAmount() == 0.0) {
                    fix.setDisable(true);
                } else {
                    fix.setDisable(false);
                }
                list.add(
                        new TableDetailsCloseLoanBalances(
                                l.getLoanTypeId(),
                                l.getRemainingLoanAmount(),
                                fix
                        )
                );
            }
            tblLoansAvailables.setItems(list);
            //

            String accNo = txtAccNumb.getText();
            //get data into tblAccBalancee
            List<DepositAccountDTO> depositAccountsByAccountNumber = depositAccountBO.getDepositAccountsByAccountNumber(accountNumber);
            ObservableList<DepositAccountDTO> list1 = FXCollections.observableArrayList(depositAccountsByAccountNumber);
            ObservableList<TableDetailsCloseAccBalances> list2 = FXCollections.observableArrayList();
            for (DepositAccountDTO d : list1) {
                JFXButton closetype = new JFXButton("Close");
                closetype.setOnAction(event -> {
                    if (!tblAccBalancee.getSelectionModel().isEmpty()) {
                        try {
                            TableDetailsCloseAccBalances selectedItem = tblAccBalancee.getSelectionModel().getSelectedItem();
                            makeDepositTypeWithdrawal(accNo, selectedItem, list1.get(tblAccBalancee.getSelectionModel().getSelectedIndex()).getDepositTypeId());
                        } catch (SQLException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                });
                if (d.getBalance() == 0.0) {
                    closetype.setDisable(true);
                } else {
                    closetype.setDisable(false);
                }
                list2.add(
                        new TableDetailsCloseAccBalances(
                                d.getDepositTypeId(),
                                d.getBalance(),
                                closetype
                        )
                );
            }
            tblAccBalancee.setItems(list2);
            //
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void makeDepositTypeWithdrawal(String accNo, TableDetailsCloseAccBalances selectedItem, String depositTypeId) throws SQLException, ClassNotFoundException {
        try {
            String depositTypeAccountId = depositAccountBO.getDepositAccountsByAccountNumber(accNo).stream().filter(depositAccountDTO -> depositAccountDTO.getDepositTypeId().equals(depositTypeId)).collect(Collectors.toCollection(ArrayList::new)).get(0).getDepositTypeAccountId();
            double balance = selectedItem.getBalance();
            //transaction, withdrawal, depositaccount
            DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            //Make transaction
            String transasctionID = transactionBO.generateNextId();
            boolean isTransactionSuccess = transactionBO.makeTransaction(
                    new TransactionDTO(
                            transasctionID,
                            accNo,
                            balance,
                            Date.valueOf(LocalDate.now()),
                            Time.valueOf(LocalTime.now()),
                            TransactionBO.TransactionTypeState.MONEY_OUT
                    )
            );
            if (isTransactionSuccess) {
                //Make withdrawal
                boolean isWithdraw = withdrawalBO.makeWithdraw(
                        new WithdrawalDTO(
                                withdrawalBO.generateNextId(),
                                transasctionID,
                                depositTypeAccountId,
                                balance
                        )
                );
                if (isWithdraw) {
                    //Update balance to 0.0
                    boolean isUpdated = depositAccountBO.setBalanceById(depositTypeAccountId, 0.0);
                    if (isUpdated) {
                        ButtonType ok=new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                        ButtonType no=new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?", ok, no).showAndWait();
                        if(result.orElse(no)==ok){
                            DbConnection.getDbConnection().getConnection().commit();
                            new Alert(Alert.AlertType.CONFIRMATION,"Account balance has been withdrawn !").show();
                            txtAccNumbOnKeyReleased();
                        }else {
                            DbConnection.getDbConnection().getConnection().rollback();
                            new Alert(Alert.AlertType.ERROR,"Withdrawal canceled !").show();
                        }

                    } else {
                        DbConnection.getDbConnection().getConnection().rollback();
                        System.out.println("1");
                    }
                } else {
                    DbConnection.getDbConnection().getConnection().rollback();
                }
            } else {
                DbConnection.getDbConnection().getConnection().rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }


    }

    private void settleSelectedLoan(LoansDTO selectedItem) throws SQLException, ClassNotFoundException {
        try {
            double paidAmount = selectedItem.getRemainingLoanAmount()+selectedItem.getInterestAmount();

            //Transaction,LoanInstallment,loans
            DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            //Make transaction
            String transactionID = transactionBO.generateNextId();
            boolean isTransactionSuccess = transactionBO.makeTransaction(
                    new TransactionDTO(
                            transactionID,
                            selectedItem.getAccountId(),
                            paidAmount,
                            Date.valueOf(LocalDate.now()),
                            Time.valueOf(LocalTime.now()),
                            TransactionBO.TransactionTypeState.MONEY_IN
                    )
            );
            if (isTransactionSuccess) {
                //Make Loan Installment
                boolean isLoanInstallmentSuccess = loanInstalmentBO.makeLoanInstallment(
                        new LoanInstallmentDTO(
                                loanInstalmentBO.generateNextId(),
                                transactionID,
                                selectedItem.getLoanId()
                        )
                );
                if (isLoanInstallmentSuccess) {
                    //Update Loan balance
                    boolean isUpdated = loansBO.updateRemainingLoanAmount(selectedItem.getLoanId(), 0.00);
                    if (isUpdated) {
                        boolean b = loansBO.updateState(selectedItem.getLoanId(), LoansBO.LoanState.SETTLED);
                        if(!b){
                            DbConnection.getDbConnection().getConnection().rollback();
                            return;
                        }
                        ButtonType ok=new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                        ButtonType no=new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?", ok, no).showAndWait();
                        if(result.orElse(no)==ok){
                            DbConnection.getDbConnection().getConnection().commit();
                            new Alert(Alert.AlertType.CONFIRMATION,"Loan has been settled !").show();
                            txtAccNumbOnKeyReleased();
                        }else {
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
        } catch (SQLException | NoCodeException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
    }


    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtFirstDepositOnAction(ActionEvent event) {
        boolean check = FormManage.check(RegexTypes.AMOUNT, txtFirstDeposit.getText());
        if(!check)return;
        txtFirstDeposit.setText(txtFirstDeposit.getText()+".00");
    }

    @FXML
    void txtFirstDepositOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtNicOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtSearchOnKeyReleased() {
        try {
            btnUpdate.setDisable(true);
            boolean check = FormManage.check(RegexTypes.ACCOUNT_NUMBER, txtSearch.getText());
            if (txtSearch.getText().equals("")) {
                return;
            }
            if (!check) {
                cmbAccTypes.setDisable(false);
                txtFirstDeposit.setDisable(false);
                btnUpdate.setDisable(true);
                return;
            }
            AccountDetailsDTO account = null;
            try {
                account = accountDetailsBO.getAccountByAccountNumber(txtSearch.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (account == null) {
                cmbAccTypes.setDisable(false);
                txtFirstDeposit.setDisable(false);
                btnUpdate.setDisable(true);
                new Alert(Alert.AlertType.INFORMATION, "Account is not found !").show();
                return;
            }
            if (!accountDetailsBO.isExistAndActive(account.getAccountId())) {
                ButtonType ok=new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel=new ButtonType("cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "This account is closed ! Do you want to Re-Active ?", ok,cancel);
                Optional<ButtonType> buttonType = alert.showAndWait();
                Stage stage = new Stage();
                if (buttonType.orElse(cancel) == ok) {
                    stage.setTitle("Account Re-Activation");
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/gdse/view/Re-ActiveForm.fxml"))));
                    stage.setAlwaysOnTop(true);
                    stage.show();
                } else {
                    txtSearch.clear();
                }
                return;
            }
            cmbAccTypes.setDisable(true);
            txtFirstDeposit.setDisable(true);
            btnUpdate.setDisable(false);
            new Alert(Alert.AlertType.CONFIRMATION, "Account is found !").show();
            txtAccNoo.setText(txtSearch.getText());
            txtNic.setText(account.getNic());
            txtName.setText(account.getName());
            txtAddress.setText(account.getAddress());
            txtContact.setText(account.getContact());
            txtEmail.setText(account.getEmail());
            txtDob.setText(String.valueOf(account.getDateOfBirth()));
            txtTime.setText(String.valueOf(account.getRegTime()));
            txtAccNo.setText(account.getAccountId());
            txtDate.setText(String.valueOf(account.getRegDate()));
            if (account.getGender().equals("Male")) {
                radioButtonMale.setSelected(true);
            } else if (account.getGender().equals("Female")) {
                radioButtonFemale.setSelected(true);
            } else {
                radioButtonOther.setSelected(true);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}

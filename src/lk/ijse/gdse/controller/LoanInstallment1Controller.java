package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.controller.tm.LoanInstallmentTM02;
import lk.ijse.gdse.controller.tm.LoanInstallmentTM01;
import lk.ijse.gdse.controller.util.FormManage;
import lk.ijse.gdse.controller.util.RegexTypes;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.*;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LoanInstallment1Controller {

    @FXML
    private Label lblInstallmentDate;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField txtAccNo;

    @FXML
    private Label lblNotifyAccountNo;

    @FXML
    private JFXComboBox<LoansDTO> cmbLoan;

    @FXML
    private Label lblNotifyLoanTypes;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private TableView<LoanInstallmentTM02> tblLoanInstallment;

    @FXML
    private TableColumn<?, ?> colLoan;

    @FXML
    private TableColumn<?, ?> colInstallmentAmount;

    @FXML
    private Label lblInstallmentAmount;

    @FXML
    private Label lblLoanBalance;

    @FXML
    private TableView<LoanInstallmentTM01> tblViewLoanInstallment;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private Label lblTransactionID;

    private final ArrayList<JFXTextField> textFields=new ArrayList<>();

    private final LoanDetailsBO loanDetailsBO= (LoanDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOAN_DETAILS_BO);
    private final LoansBO loansBO= (LoansBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOANS_BO);
    private final AccountDetailsBO accountDetailsBO= (AccountDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ACCOUNT_DETAILS_BO);
    private final LoanInstalmentBO loanInstalmentBO= (LoanInstalmentBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOAN_INSTALLMENT_BO);
    private final TransactionBO transactionBO= (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);

    public void initialize(){
        Collections.addAll(textFields,txtAccNo,txtAmount);
        loadTransactionID();

        //Set loan interest
        setLoanInfo();
    }

    private void setLoanInfo() {
        try {
            List<LoansDTO> allLoans = loansBO.getAllLoans();
            ObservableList<LoansDTO> loanList =FXCollections.observableArrayList();
            loanList.addAll(allLoans);
            ObservableList<LoanDetailsDTO> loanDetailsList = FXCollections.observableArrayList();
            List<LoanDetailsDTO> allLoanType = loanDetailsBO.getAllLoanType();
            loanDetailsList.addAll(allLoanType);

            String loanTypeID;
            double interest;
            double installmentAmount;
            double amount;
            int installments;
            LocalDate date;
            for (int j = 0; j < loanList.size(); j++) {
                for (int i = 0; i < loanDetailsList.size(); i++) {
                    if(loanList.get(j).getLoanTypeId().equals(loanDetailsList.get(i).getLoanTypeId())){
                        loanTypeID=loanDetailsList.get(i).getLoanTypeId();
                        installmentAmount=loanList.get(j).getInstallmentAmount();
                        installments=loanList.get(j).getInstallments();
                        amount=loanList.get(j).getAmount();
                        String loanID = loanList.get(j).getLoanId();
                        date= loanList.get(j).getIssuedDate().toLocalDate();

                        //Get last installment date for the related loan
                        LocalDate lastInstallmentDate=null;
                        List<LoanInstallmentDTO> loanInstallments = loanInstalmentBO.getLoanInstallmentsByLoanId(loanID);
                        if(loanInstallments.size()>0){
                            String transactionId = loanInstallments.get(loanInstallments.size() - 1).getTransactionId();
                            lastInstallmentDate = transactionBO.getTransaction(transactionId).getDate().toLocalDate();
                        }else {
                            lastInstallmentDate=date;
                        }

                        //Calculate interest for a month for the related loan
                        double interestAmountForMonth = installmentAmount-(amount/(double)installments);

                        //Calculate loan balance
                        Period between = Period.between(lastInstallmentDate, date);
                        int years = between.getYears();
                        int months = between.getMonths();
                        months+=(years*12);
                        if(months==0){
                            months=1;
                        }
                        boolean b =loansBO.updateInterestAmount(loanID, (double) months * interestAmountForMonth);
                        int count = loansBO.getRemainingInstallment(loanID);
                        if (count > 0) {
                            double balance = amount - (count * (installmentAmount - interestAmountForMonth));
                            loansBO.updateRemainingLoanAmount(loanID, amount+balance);
                        } else {
                            loansBO.updateRemainingLoanAmount(loanID, amount);
                        }
                    }
                }
            }
            System.gc();
        } catch (SQLException e) {
            System.out.println(e);
        } catch (NoCodeException e) {
            e.printStackTrace();
        }
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
        boolean validate = FormManage.validate(textFields, RegexTypes.ACCOUNT_NUMBER, RegexTypes.AMOUNT);
        boolean isEmpty = cmbLoan.getSelectionModel().isEmpty();
        if(!validate||isEmpty)return;

        colLoan.setCellValueFactory(new PropertyValueFactory<>("loan"));
        colInstallmentAmount.setCellValueFactory(new PropertyValueFactory<>("installmentAmount"));
        LoanInstallmentTM02 tbl2=new LoanInstallmentTM02(cmbLoan.getValue().getLoanId(),Double.parseDouble(txtAmount.getText()));

        //Add or update rows
        String loanType = cmbLoan.getValue().getLoanTypeId();
        ObservableList<LoanInstallmentTM02> items = tblLoanInstallment.getItems();
        int index = isNewRow(items, loanType);
        if (index==-1) {
            items.add(tbl2);
        } else {
            items.get(index).setLoan(loanType);
            double newAmount = items.get(index).getInstallmentAmount() + tbl2.getInstallmentAmount();
            items.get(index).setInstallmentAmount(newAmount);
        }
        tblLoanInstallment.setItems(items);
        tblLoanInstallment.refresh();

    }

    private int isNewRow(ObservableList<LoanInstallmentTM02> items, String loantype) {
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getLoan().equals(loantype)){
                return i;
            }
        }
        return -1;
    }
    @FXML
    void btnClearFormOnAction(ActionEvent event) {
        txtAccNo.clear();
        txtAmount.clear();
        tblLoanInstallment.getItems().clear();
        tblViewLoanInstallment.getItems().clear();
        cmbLoan.getItems().clear();
    }

    @FXML
    void btnInstallOnAction(ActionEvent event) {
        try {
            boolean validate = FormManage.validate(textFields, RegexTypes.ACCOUNT_NUMBER, RegexTypes.AMOUNT);
            if(!validate)return;

                DbConnection.getDbConnection().getConnection().setAutoCommit(false);
                String transactionID = transactionBO.generateNextId();
                String accountID=txtAccNo.getText();
                double amount= Double.parseDouble(txtAmount.getText());
                Date date= Date.valueOf(LocalDate.now());
                Time time= Time.valueOf(LocalTime.now());
                TransactionDTO transaction = new TransactionDTO(transactionID,accountID,amount,date,time, TransactionBO.TransactionTypeState.MONEY_IN);
                //Make transaction
                boolean isTransactionAdded = transactionBO.makeTransaction(transaction);
                if (isTransactionAdded) {
                    String loanInstallmentID = loanInstalmentBO.generateNextId();
                    String loanID = cmbLoan.getValue().getLoanId();
                    LoanInstallmentDTO loanInstallmentDTO = new LoanInstallmentDTO(loanInstallmentID, transactionID, loanID);
                    //Make loan installment
                    boolean isAdded = loanInstalmentBO.makeLoanInstallment(loanInstallmentDTO);
                    if (isAdded) {
                        //Update rows at loans table (loan balance, interest)

                        //Update interest
                        boolean isUpdated1 = loansBO.updateInterestAmount(loanID, 0);
                        if (isUpdated1) {
                            LoansDTO loanDetails = loansBO.getLoanById(loanID);
                            //Update loan balance
                            double v = loanDetails.getRemainingLoanAmount() - amount;
                            boolean isUpdated2 = loansBO.updateRemainingLoanAmount(loanID, v);
                            if (isUpdated2) {
                                ButtonType ok = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ? ", ok, cancel);
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.orElse(cancel) == ok) {
                                    DbConnection.getDbConnection().getConnection().commit();
                                    new Alert(Alert.AlertType.CONFIRMATION, "Approved Success !").show();
                                    initialize();
                                    btnClearFormOnAction(new ActionEvent());
                                    showReport();

                                } else {
                                    DbConnection.getDbConnection().getConnection().rollback();
                                    new Alert(Alert.AlertType.ERROR, "Approved canceled !").show();
                                }
                                txtAccNo.requestFocus();
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

        } catch (SQLException e) {
            System.out.println(e);
        } catch (NoCodeException e) {
            e.printStackTrace();
        }
    }
    private void showReport(){

        try {
            JasperDesign jasperDesign= JRXmlLoader.load("D:\\Working_Directory\\Sanasa\\Final_Project_IDSE_1st_Semester\\src\\lk\\ijse\\sanasa\\report\\loanReportjrxml.jrxml");
            String query="SELECT sanasa.transaction.`TransactionID`, sanasa.transaction.`AccountID`, sanasa.transaction.`Amount`, sanasa.loans.`LoanID`, sanasa.loans.`RemainingLoanAmount`, sanasa.loandetails.`Description`, sanasa.transaction.`TransactionID`, sanasa.transaction.`AccountID`, sanasa.transaction.`Amount`, sanasa.loaninstallment.`LoanInstallmentID`, sanasa.loandetails.`Description`, sanasa.loans.`RemainingLoanAmount`, sanasa.loans.`SettledOrNot` FROM sanasa.loaninstallment INNER JOIN sanasa.loans ON sanasa.loaninstallment.`LoanID` = sanasa.loans.`LoanID` INNER JOIN sanasa.transaction ON sanasa.loaninstallment.`TransactionID` = sanasa.transaction.`TransactionID` INNER JOIN sanasa.loandetails ON sanasa.loans.`LoanTypeID` = sanasa.loandetails.`LoanTypeID` ORDER BY sanasa.loaninstallment.`LoanInstallmentID` DESC limit 1\n";

            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(query);

            jasperDesign.setQuery(updateQuery);

            JasperReport jasperReport= null;
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,DbConnection.getDbConnection().getConnection());

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void cmbLoanTypesOnAction(ActionEvent event) {
        if(!cmbLoan.getSelectionModel().isEmpty())return;
        LoansDTO selectedItem = cmbLoan.getSelectionModel().getSelectedItem();
        lblInstallmentAmount.setText(String.valueOf(selectedItem.getRemainingInstallments()));
        lblLoanBalance.setText(String.valueOf(selectedItem.getRemainingLoanAmount()));
        String loanId = selectedItem.getLoanId();
        List<LoanInstallmentDTO> loanInstallmentsByLoanId ;
        try {
             loanInstallmentsByLoanId = loanInstalmentBO.getLoanInstallmentsByLoanId(loanId);
            String transactionId = loanInstallmentsByLoanId.get(loanInstallmentsByLoanId.size() - 1).getTransactionId();
            Date date = transactionBO.getTransaction(transactionId).getDate();
            lblInstallmentDate.setText(String.valueOf(date));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtAccNoOnAction(ActionEvent event) {

    }

    @FXML
    void txtAccNoOnKeyReleased(KeyEvent event) {
        cmbLoan.getItems().clear();
        txtAmount.clear();
        lblNotifyAccountNo.setText("");
        lblNotifyAccountNo.setStyle("-fx-text-fill: null");
        try {
            boolean validate = FormManage.check(RegexTypes.ACCOUNT_NUMBER, txtAccNo.getText());
            if(!validate) {
                cmbLoan.getItems().clear();
                return;
            }
            String accountNumber = txtAccNo.getText();
            boolean existAndActive = accountDetailsBO.isExistAndActive(accountNumber);
            if(!existAndActive) {
                new Alert(Alert.AlertType.WARNING,"Account Number Nnot found !").show();
                return;
            }

            boolean isLoaded = loadLoanForCombobox(accountNumber);
            if(isLoaded){
                loadRelatedLoanInstallmentsForTable01(accountNumber);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
    private void loadRelatedLoanInstallmentsForTable01(String accountNumber) {
        try {
            List<LoansDTO> loansByAccountNumber = loansBO.getLoansByAccountNumber(accountNumber);
            ObservableList<LoanInstallmentTM01> list = FXCollections.observableArrayList();
            for (LoansDTO loanDto :loansByAccountNumber) {
                List<LoanInstallmentDTO> loanInstallmentsByLoanId = loanInstalmentBO.getLoanInstallmentsByLoanId(loanDto.getLoanId());
                for (LoanInstallmentDTO loanInstallmentDTO : loanInstallmentsByLoanId) {
                    String transactionId = loanInstallmentDTO.getTransactionId();
                    Date date = transactionBO.getTransaction(transactionId).getDate();
                    list.add(new LoanInstallmentTM01(loanDto.getLoanId(),loanDto.getInstallmentAmount(),date.toString()));
                }
            }
            colID.setCellValueFactory(new PropertyValueFactory<>("loanID"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("installmentAmount"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tblViewLoanInstallment.setItems(list);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private boolean loadLoanForCombobox(String accountNumber) throws SQLException, ClassNotFoundException {
        ObservableList<LoansDTO> list = FXCollections.observableArrayList();
        list.addAll(loansBO.getLoansByAccountNumber(accountNumber));
        cmbLoan.setItems(list);
        return list.size()>0;
    }

}

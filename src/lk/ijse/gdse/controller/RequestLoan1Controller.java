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
import lk.ijse.gdse.dto.AccountDetailsDTO;
import lk.ijse.gdse.dto.LoanDetailsDTO;
import lk.ijse.gdse.dto.PendingLoansDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.AccountDetailsBO;
import lk.ijse.gdse.service.bo.custom.LoanDetailsBO;
import lk.ijse.gdse.service.bo.custom.PendingLoansBO;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class RequestLoan1Controller {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField txtAccNo;

    @FXML
    private Label lblNotifyAccNo;

    @FXML
    private JFXComboBox<LoanDetailsDTO> cmdLoanTypes;

    @FXML
    private Label lblNotifyLoanTypes;

    @FXML
    private JFXComboBox<String> cmbAmount;

    @FXML
    private Label lblNotifyAmountTypes;

    @FXML
    private JFXComboBox<String> cmbInstallmentCounts;

    @FXML
    private Label lblNotifyInstallmentCountTypes;

    @FXML
    private Label lblInterest;

    @FXML
    private Label lblInstallmentAmount;

    @FXML
    private Label lblNotifyRequestLoan;

    @FXML
    private Label lblPendingLoanID;

    private ArrayList<JFXTextField> textFields=new ArrayList<>();

    private final LoanDetailsBO loanDetailsBO= (LoanDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOAN_DETAILS_BO);
    private final PendingLoansBO pendingLoansBO= (PendingLoansBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.PENDING_LOANS_BO);
    private final AccountDetailsBO accountDetailsBO= (AccountDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ACCOUNT_DETAILS_BO);


    public void initialize(){
        Collections.addAll(textFields,txtAccNo);

        //Set pending loan Id
        loadPendingLoanID();

        //Set loan types
        loadLoans();
    }

    private void loadLoans() {
        try {
            List<LoanDetailsDTO> allLoanType = loanDetailsBO.getAllLoanType();
            ObservableList<LoanDetailsDTO> list = FXCollections.observableArrayList();
            list.addAll(allLoanType);
            cmdLoanTypes.setItems(list);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void cmdLoanTypesOnAction(ActionEvent event){
        try {
            if(!cmdLoanTypes.getSelectionModel().isEmpty()){
                //Clear combo boxes
                cmbInstallmentCounts.getSelectionModel().clearSelection();
                cmbAmount.getSelectionModel().clearSelection();
                //Set interest
                LoanDetailsDTO loanDetails = loanDetailsBO.getAllLoanType().stream().filter(loanDetailsDTO -> loanDetailsDTO.getLoanTypeId().equals(cmdLoanTypes.getValue().getLoanTypeId())).collect(Collectors.toCollection(ArrayList::new)).get(0);
                assert loanDetails != null;
                lblInterest.setText(loanDetailsBO.getInterest(cmdLoanTypes.getValue().getLoanTypeId()) + "%");

                //Set loan amounts
                String[] split = loanDetails.getAmounts().split("[,]");
                ObservableList<String> amounts = FXCollections.observableArrayList();
                amounts.addAll(Arrays.asList(split));
                cmbAmount.setItems(amounts);

                //Set loan installments
                ObservableList<String> installmentsCount = FXCollections.observableArrayList();
                installmentsCount.clear();
                installmentsCount.add("6 months");
                installmentsCount.add("12 months");
                installmentsCount.add("18 months");
                installmentsCount.add("24 months");
                installmentsCount.add("48 months");
                installmentsCount.add("60 months");
                cmbInstallmentCounts.setItems(installmentsCount);

                //set Installment Amount
                setInstallmentAmount(lblInstallmentAmount,lblInterest,cmbAmount,cmbInstallmentCounts);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void setInstallmentAmount(Label lblInstallmentAmount, Label lblInterest, JFXComboBox<String> cmbAmount, JFXComboBox<String> cmbInstallmentCounts) {

        boolean isSelectedAmount = cmbAmount.getSelectionModel().getSelectedIndex() > -1;
        boolean isSelectedMonths = cmbInstallmentCounts.getSelectionModel().getSelectedIndex() > -1;
        boolean isSelectedLoanTypes = cmdLoanTypes.getSelectionModel().getSelectedIndex() > -1;
        if (isSelectedAmount && isSelectedMonths && isSelectedLoanTypes) {
            double interest = Double.parseDouble(lblInterest.getText().split("[%]")[0])/100.00;
            double amount = Double.parseDouble(cmbAmount.getValue());
            double months = Double.parseDouble(cmbInstallmentCounts.getValue().split("[ ]")[0]);
            //Calculate installment
            double installmentAmount = (amount/months)+(amount*interest/12.00);
            String format = String.format("%.2f", installmentAmount);
            lblInstallmentAmount.setText(format);
        } else {
            lblInstallmentAmount.setText("");
        }
    }

    private void loadPendingLoanID() {
        try {
            lblPendingLoanID.setText(pendingLoansBO.generateNextId());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    void btnClearFormOnAction(ActionEvent event) {
        for (JFXTextField field : textFields) field.clear();
        cmbAmount.getSelectionModel().clearSelection();
        cmbInstallmentCounts.getSelectionModel().clearSelection();
        cmdLoanTypes.getSelectionModel().clearSelection();
        lblInterest.setText(null);
    }

    @FXML
    void btnRequestLoanOnAction(ActionEvent event) {
        try {
            boolean validate1 = FormManage.validate(textFields, RegexTypes.ACCOUNT_NUMBER);
            boolean validate2 = !(cmbAmount.getSelectionModel().isEmpty() || cmbInstallmentCounts.getSelectionModel().isEmpty() || cmdLoanTypes.getSelectionModel().isEmpty());
            if (!validate1 && !validate2) {
                new Alert(Alert.AlertType.WARNING,"Please fill the form correctly ! ").show();
                return;
            }
                //Set auto commit false
                DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            boolean isAddedPendingLoan = pendingLoansBO.createPendingLoan(new PendingLoansDTO(
                    lblPendingLoanID.getText(),
                    Double.parseDouble(cmbAmount.getValue()),
                    cmdLoanTypes.getValue().getLoanTypeId(),
                    txtAccNo.getText(),
                    Integer.parseInt(cmbInstallmentCounts.getValue().split("[ ]")[0]),
                    Double.parseDouble(lblInstallmentAmount.getText())
            ));
                if (isAddedPendingLoan) {
                    ButtonType yes =new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no =new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Request this loan ?", yes, no);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.orElse(no) == yes) {
                        DbConnection.getDbConnection().getConnection().commit();
                        new Alert(Alert.AlertType.CONFIRMATION,"Loan request is successfully added !").show();
                        loadPendingLoanID();
                    } else {
                        DbConnection.getDbConnection().getConnection().rollback();
                    }
                    btnClearFormOnAction(event);
                } else {
                    DbConnection.getDbConnection().getConnection().rollback();
                }

        } catch (SQLException | NullPointerException e) {
            new Alert(Alert.AlertType.ERROR,"Please fill the form correctly ! ").show();
        }
    }

    @FXML
    void cmbAmountOnAction(ActionEvent event) {
        setInstallmentAmount(lblInstallmentAmount,lblInterest,cmbAmount,cmbInstallmentCounts);
    }

    @FXML
    void cmbInstallmentCountsOnAction(ActionEvent event) {
        setInstallmentAmount(lblInstallmentAmount,lblInterest,cmbAmount,cmbInstallmentCounts);
    }

    @FXML
    void txtAccNoOnKeyReleased(KeyEvent event) {
        try {
            boolean validate = FormManage.validate(textFields, RegexTypes.ACCOUNT_NUMBER);
            if(!validate){
                return;
            }
            String accountNumber = txtAccNo.getText();
            if(accountDetailsBO.isExistAndActive(accountNumber)){
                txtAccNo.setFocusColor(Paint.valueOf("RED"));
                txtAccNo.requestFocus();
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}

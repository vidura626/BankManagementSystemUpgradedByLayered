package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse.controller.tm.ApproveLoansTM;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.LoansDTO;
import lk.ijse.gdse.dto.PendingLoansDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.AccountDetailsBO;
import lk.ijse.gdse.service.bo.custom.LoanDetailsBO;
import lk.ijse.gdse.service.bo.custom.LoansBO;
import lk.ijse.gdse.service.bo.custom.PendingLoansBO;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ApproveLoans1Controller {

    public JFXComboBox<PendingLoansDTO> cmbLoanIDs;
    public TableColumn<?,?> colPendID;
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblNotifyApprove;

    @FXML
    private TableView<ApproveLoansTM> tblLoans;

    @FXML
    private TableColumn<?, ?> colLoan;

    @FXML
    private TableColumn<?, ?> colInstallmentAmount;

    @FXML
    private TableColumn<?, ?> colInstallmentCount;

    @FXML
    private TableColumn<?, ?> colInstallmentDate;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private Label lblLoan;

    @FXML
    private Label lblAccNo;

    @FXML
    private Label lblAmount;

    private final PendingLoansBO pendingLoansBO= (PendingLoansBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.PENDING_LOANS_BO);
    private final LoansBO loansBO= (LoansBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOANS_BO);
    private final AccountDetailsBO accountDetailsBO= (AccountDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ACCOUNT_DETAILS_BO);
    private final LoanDetailsBO loanDetailsBO= (LoanDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOAN_DETAILS_BO);


    public void initialize() {
        loadPendingLoanIds();
    }

    private void loadPendingLoanIds() {
        try {
            List<PendingLoansDTO> allPendingLoans = pendingLoansBO.getAllPendingLoans();
            ObservableList<PendingLoansDTO> list = FXCollections.observableArrayList();
            list.addAll(allPendingLoans);
            cmbLoanIDs.setItems(list);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {

            boolean isEmpty = cmbLoanIDs.getSelectionModel().isEmpty();
            if(isEmpty){
                cmbLoanIDs.setFocusColor(Paint.valueOf("RED"));
                cmbLoanIDs.requestFocus();
                return;
            }

            PendingLoansDTO pendingLoan = cmbLoanIDs.getSelectionModel().getSelectedItem();
            String lastID = null;
            if (tblLoans.getItems().size()==0) {
                lastID = loansBO.generateNextId();
            } else {
                ObservableList<ApproveLoansTM> items = tblLoans.getItems();
                String loanID = items.get(items.size() - 1).getLoanID();
                int lastDigit = Integer.parseInt(loanID.split("[L]")[1])+1;
                lastID =String.format("L%08d",lastDigit);
            }

            ApproveLoansTM loan = new ApproveLoansTM(
                        lastID,
                        pendingLoan.getInstallmentAmount(),
                        pendingLoan.getInstallments(),
                        LocalDate.now().getDayOfMonth(),
                        new Button("Delete"),
                        pendingLoan.getPendingLoanId()
                );
            colLoan.setCellValueFactory(new PropertyValueFactory<>("loanID"));
            colInstallmentAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            colInstallmentCount.setCellValueFactory(new PropertyValueFactory<>("installment"));
            colInstallmentDate.setCellValueFactory(new PropertyValueFactory<>("monthlyInstallmentDate"));
            colPendID.setCellValueFactory(new PropertyValueFactory<>("pendingLoanID"));

            int index = checkAvailableItem(tblLoans, loan);
            if (index>-1) {
                new Alert(Alert.AlertType.WARNING,"Already Added !").show();
            } else {
                int selectedIndex = cmbLoanIDs.getSelectionModel().getSelectedIndex();
                cmbLoanIDs.getItems().remove(selectedIndex);
                tblLoans.getItems().add(loan);
                lblLoan.setText("Loan");
                lblAccNo.setText("Account Number");
                lblAmount.setText("Amount");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private int checkAvailableItem(TableView<ApproveLoansTM> tblLoans, ApproveLoansTM loan) {
        for (int i = 0; i < tblLoans.getItems().size(); i++) {
            if(tblLoans.getItems().get(i).getLoanID().equals(loan.getLoanID())){
                return i;
            }
        }
        return -1;
    }

    @FXML
    void btnApproveOnAction(ActionEvent event) throws SQLException {
        try {
            if(tblLoans.getItems().size()==0){
                new Alert(Alert.AlertType.WARNING,"Loans are not added !").show();
                return;
            }
            DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            ObservableList<ApproveLoansTM> items = tblLoans.getItems();
            for (int i = 0; i < items.size(); i++) {
                String pendingLoanID = items.get(i).getPendingLoanID();
                PendingLoansDTO pendingLoanDTO = pendingLoansBO.getPendingLoanById(pendingLoanID);
                boolean isLoanAdded = loansBO.createLoan(
                        new LoansDTO(
                                loansBO.generateNextId(),
                                pendingLoanDTO.getAmount(),
                                pendingLoanDTO.getLoanTypeId(),
                                pendingLoanDTO.getAccountId(),
                                pendingLoanDTO.getInstallments(),
                                pendingLoanDTO.getInstallmentAmount(),
                                Date.valueOf(LocalDate.now()),
                                pendingLoanDTO.getInstallments(),
                                pendingLoanDTO.getAmount(),
                                LocalDate.now().getDayOfMonth(),
                                "NotSettled",
                                pendingLoanDTO.getInstallmentAmount() - pendingLoanDTO.getAmount()
                        ));

                if (isLoanAdded) {
                    pendingLoansBO.deletePendingLoan(pendingLoanID);
                }else {
                    DbConnection.getDbConnection().getConnection().rollback();
                    return;
                }

                if (i == items.size() - 1) {
                    ButtonType ok = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ? ", ok, cancel);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(cancel) == ok) {
                        DbConnection.getDbConnection().getConnection().commit();
                        new Alert(Alert.AlertType.CONFIRMATION, "Approved Success !").show();
                    } else {
                        DbConnection.getDbConnection().getConnection().rollback();
                        new Alert(Alert.AlertType.ERROR, "Approved canceled !").show();
                    }
                    tblLoans.getItems().clear();
                    tblLoans.refresh();
                } else {
                    DbConnection.getDbConnection().getConnection().rollback();
                }
            }
        } catch (SQLException e) {

        } catch (NoCodeException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
    }

    @FXML
    void btnClearFormOnAction(ActionEvent event) {
        cmbLoanIDs.getSelectionModel().clearSelection();
        tblLoans.getItems().clear();
        lblLoan.setText("Loan");
        lblAccNo.setText("Account Number");
        lblAmount.setText("Amount");
        initialize();
    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        if(tblLoans.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Loans cannot found !").show();
            return;
        }
        String pendingLoanIDtbl = tblLoans.getSelectionModel().getSelectedItem().getPendingLoanID();

        if (!checkComboBox(pendingLoanIDtbl,cmbLoanIDs)) {
            try {
                cmbLoanIDs.getItems().add(pendingLoansBO.getPendingLoanById(tblLoans.getSelectionModel().getSelectedItem().getPendingLoanID()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ObservableList<PendingLoansDTO> items = cmbLoanIDs.getItems();
            Collections.sort(items,getComparator());
            tblLoans.getItems().remove(tblLoans.getSelectionModel().getSelectedItem());
            tblLoans.refresh();
        }
    }
    public static Comparator<PendingLoansDTO> getComparator() {
        return new Comparator<PendingLoansDTO>() {
            @Override
            public int compare(PendingLoansDTO o1, PendingLoansDTO o2) {
                return o1.getPendingLoanId().compareTo(o2.getPendingLoanId());
            }
        };
    }

    private boolean checkComboBox(String pendingLoanIDtbl, JFXComboBox<PendingLoansDTO> tblLoans) {

        for (int i = 0; i < tblLoans.getItems().size(); i++) {
            if(cmbLoanIDs.getItems().get(i).getPendingLoanId().equals(pendingLoanIDtbl)){
                return true;
            }
        }
        return false;
    }

    @FXML
    void cmbLoanIDsOnAction(ActionEvent event) {
        try {
            PendingLoansDTO pendingLoan = pendingLoansBO.getPendingLoanById(cmbLoanIDs.getValue().getPendingLoanId());
            lblLoan.setText(loanDetailsBO.getAllLoanType().stream().filter(loanDetailsDTO -> loanDetailsDTO.getLoanTypeId().equals(pendingLoan.getLoanTypeId())).collect(Collectors.toCollection(ArrayList::new)).get(0).getDescription()+" "+pendingLoan.getAmount());
        } catch (SQLException e) {
            System.out.println(e);
        }catch (NullPointerException e){

        }
    }
}

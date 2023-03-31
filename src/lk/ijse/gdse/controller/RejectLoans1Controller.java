package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.controller.tm.RejectLoansTM;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.PendingLoansDTO;
import lk.ijse.gdse.dto.RejectedLoansDTO;
import lk.ijse.gdse.entity.PendingLoans;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.LoanDetailsBO;
import lk.ijse.gdse.service.bo.custom.PendingLoansBO;
import lk.ijse.gdse.service.bo.custom.RejectedLoansBO;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class RejectLoans1Controller {

    public TableColumn<?,?> colPendingLoanID;
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblNotifyReject;

    @FXML
    private TableView<RejectLoansTM> tblLoans;

    @FXML
    private TableColumn<?, ?> colLoan;

    @FXML
    private TableColumn<?, ?> colRejectedReason;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private Label lblLoanID;

    @FXML
    private JFXTextField txtRejectedReason;

    @FXML
    private JFXComboBox<PendingLoansDTO> cmbPendingLoans;

    @FXML
    private Label lblNotifyAdd;

    private final PendingLoansBO pendingLoansBO= (PendingLoansBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.PENDING_LOANS_BO);
    private final RejectedLoansBO rejectedLoansBO= (RejectedLoansBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.REJECTED_LOANS_BO);
    private final LoanDetailsBO loanDetailsBO= (LoanDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOAN_DETAILS_BO);

    public void initialize() {
        loadPendingLoanIds();
    }

    private void loadPendingLoanIds() {
        try {
            List<PendingLoansDTO> allPendingLoans = pendingLoansBO.getAllPendingLoans();
            ObservableList<PendingLoansDTO> list = FXCollections.observableArrayList();
            list.addAll(allPendingLoans);
            cmbPendingLoans.setItems(list);
        } catch (SQLException | NullPointerException e ) {
        }
    }
    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            boolean isEmpty = cmbPendingLoans.getSelectionModel().isEmpty();
            if(isEmpty)return;


            PendingLoansDTO pendingLoan = cmbPendingLoans.getValue();
            String lastID = null;
            if (tblLoans.getItems().size()==0) {
                lastID = rejectedLoansBO.generateNextId();
            } else {
                ObservableList<RejectLoansTM> items = tblLoans.getItems();
                String loanID = items.get(items.size() - 1).getLoanID();
                int lastDigit = Integer.parseInt(loanID.split("[RJ]")[2])+1;
                lastID =String.format("RJ%08d",lastDigit);
            }

            RejectLoansTM loan=new RejectLoansTM(
                        lastID,
                        txtRejectedReason.getText().isEmpty()?"<No Reason>":txtRejectedReason.getText(),
                        new Button("Delete"),
                        pendingLoan.getPendingLoanId()
                );
            ObservableList<RejectLoansTM> loans = FXCollections.observableArrayList();
            colLoan.setCellValueFactory(new PropertyValueFactory<>("loanID"));
            colRejectedReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
            colPendingLoanID.setCellValueFactory(new PropertyValueFactory<>("pendingLoanID"));

            int index = checkAvailableItem(tblLoans, loan);
            if (index>-1) {
                new Alert(Alert.AlertType.WARNING,"Already Added !").show();
            } else {
                cmbPendingLoans.getItems().remove(pendingLoan);
                tblLoans.getItems().add(loan);
                lblLoanID.setText("Loan");
                txtRejectedReason.setText("");
            }
        } catch (SQLException e) {
        }
    }

    private int checkAvailableItem(TableView<RejectLoansTM> tblLoans, RejectLoansTM loan) {
        for (int i = 0; i < tblLoans.getItems().size(); i++) {
            if(tblLoans.getItems().get(i).getLoanID().equals(loan.getLoanID())){
                return i;
            }
        }
        return -1;
    }

    @FXML
    void btnClearFormOnAction(ActionEvent event) {
        cmbPendingLoans.getSelectionModel().clearSelection();
        txtRejectedReason.setText("Rejected Reason");
        tblLoans.getItems().clear();
        lblLoanID.setText("Loan");
        initialize();
    }

    @FXML
    void btnRejectOnAction(ActionEvent event) throws SQLException {
        try {
            if(tblLoans.getItems().size()==0){
                new Alert(Alert.AlertType.WARNING,"Loans are not added !").show();
                return;
            }
            DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            ObservableList<RejectLoansTM> items = tblLoans.getItems();

            for (int i = 0; i < items.size(); i++) {
                PendingLoansDTO pendingLoanDTO = pendingLoansBO.getPendingLoanById(items.get(i).getPendingLoanID());
                boolean reject = rejectedLoansBO.createRejectedLoan(new RejectedLoansDTO(
                                rejectedLoansBO.generateNextId(),
                                pendingLoanDTO.getAmount(),
                                pendingLoanDTO.getLoanTypeId(),
                                pendingLoanDTO.getAccountId(),
                                items.get(i).getReason()
                        )
                );
                if(reject){
                    pendingLoansBO.deletePendingLoan(pendingLoanDTO.getPendingLoanId());
                }else{
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

    private boolean checkComboBox(String pendingLoanIDtbl, JFXComboBox<PendingLoansDTO> tblLoans) {

        for (int i = 0; i < tblLoans.getItems().size(); i++) {
            if(tblLoans.getItems().get(i).getPendingLoanId().equals(pendingLoanIDtbl)){
                return true;
            }
        }
        return false;
    }

    @FXML
    void btnRemveOnAction(ActionEvent event) {
        if(tblLoans.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Loans cannot found !").show();
            return;
        }
        String pendingLoanIDtbl = tblLoans.getSelectionModel().getSelectedItem().getPendingLoanID();

        if (!checkComboBox(pendingLoanIDtbl,cmbPendingLoans)) {
            try {
                cmbPendingLoans.getItems().add(pendingLoansBO.getPendingLoanById(tblLoans.getSelectionModel().getSelectedItem().getPendingLoanID()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ObservableList<PendingLoansDTO> items = cmbPendingLoans.getItems();
            items.sort(getComparator());
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

    @FXML
    void cmbPendingLoansOnAction(ActionEvent event) {
        try {
            PendingLoansDTO pendingLoan = pendingLoansBO.getPendingLoanById(cmbPendingLoans.getValue().getPendingLoanId());
            lblLoanID.setText(loanDetailsBO.getAllLoanType().stream().filter(loanDetailsDTO -> loanDetailsDTO.getLoanTypeId().equals(pendingLoan.getLoanTypeId())).collect(Collectors.toCollection(ArrayList::new)).get(0).getDescription()+" "+pendingLoan.getAmount());
        } catch (SQLException e) {
            System.out.println(e);
        }catch (NullPointerException e){

        }
    }

}

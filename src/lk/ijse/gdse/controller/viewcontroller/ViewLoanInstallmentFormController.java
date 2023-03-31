package lk.ijse.gdse.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.controller.tm.LoanInstViewTM;
import lk.ijse.gdse.dto.LoanInstallmentDTO;
import lk.ijse.gdse.dto.TransactionDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.LoanInstalmentBO;
import lk.ijse.gdse.service.bo.custom.TransactionBO;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class ViewLoanInstallmentFormController {

    @FXML
    private TableView<LoanInstViewTM> tblLoanInstallments;

    @FXML
    private TableColumn<?, ?> colLoanInstallmentID;

    @FXML
    private TableColumn<?, ?> colTransactionID;

    @FXML
    private TableColumn<?, ?> colLoanID;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colAccountID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    private final LoanInstalmentBO loanInstalmentBO= (LoanInstalmentBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOAN_INSTALLMENT_BO);
    private final TransactionBO transactionBO= (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);

    public void initialize(){
        tblLoanInstallments.getItems().clear();

        colLoanInstallmentID.setCellValueFactory(new PropertyValueFactory<>("loanInstallmentId"));
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colLoanID.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            List<LoanInstallmentDTO> allLoanInstallments = loanInstalmentBO.getAllLoanInstallments();
            for(LoanInstallmentDTO loanInstallmentDTO : allLoanInstallments){
                String loanInstallmentId = loanInstallmentDTO.getLoanInstallmentId();
                String transactionId = loanInstallmentDTO.getTransactionId();
                String loanId = loanInstallmentDTO.getLoanId();
                TransactionDTO transaction = transactionBO.getTransaction(transactionId);
                double amount = transaction.getAmount();
                String accountId = transaction.getAccountId();
                Date date = transaction.getDate();
                Time time = transaction.getTime();
                tblLoanInstallments.getItems().add(new LoanInstViewTM(loanInstallmentId,transactionId,loanId,amount,accountId,date,time));
            }
            tblLoanInstallments.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

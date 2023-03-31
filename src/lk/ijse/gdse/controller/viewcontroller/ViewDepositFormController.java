package lk.ijse.gdse.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.controller.tm.DepositViewTM;
import lk.ijse.gdse.dto.DepositDTO;
import lk.ijse.gdse.dto.TransactionDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.DepositAccountBO;
import lk.ijse.gdse.service.bo.custom.DepositBO;
import lk.ijse.gdse.service.bo.custom.TransactionBO;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class ViewDepositFormController {

    @FXML
    private TableView<DepositViewTM> tblDeposits;

    @FXML
    private TableColumn<?, ?> colDepositID;

    @FXML
    private TableColumn<?, ?> colTransactionID;

    @FXML
    private TableColumn<?, ?> colDepositTypeAccountID;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colAccountID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    private final DepositBO depositBO = (DepositBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_BO);
    private final DepositAccountBO depositAccountBO = (DepositAccountBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_ACCOUNT_BO);
    private final TransactionBO transactionBO = (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);

    public void initialize(){
        tblDeposits.getItems().clear();
        List<DepositDTO> allDeposits = null;

        colDepositID.setCellValueFactory(new PropertyValueFactory<>("depositId"));
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colDepositTypeAccountID.setCellValueFactory(new PropertyValueFactory<>("depositTypeAccountId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            allDeposits = depositBO.getAllDeposits();
            for(DepositDTO depositDTO : allDeposits){
                TransactionDTO transaction = transactionBO.getTransaction(depositDTO.getTransactionId());
                String depositId = depositDTO.getDepositId();
                String depositTypeAccountId = depositDTO.getDepositTypeAccountId();
                String transactionId = transaction.getTransactionId();
                double amount = transaction.getAmount();
                String accountId = transaction.getAccountId();
                Date date = transaction.getDate();
                Time time = transaction.getTime();
                tblDeposits.getItems().add(new DepositViewTM(depositId,depositTypeAccountId,transactionId,amount,accountId,date,time));
            }
            tblDeposits.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

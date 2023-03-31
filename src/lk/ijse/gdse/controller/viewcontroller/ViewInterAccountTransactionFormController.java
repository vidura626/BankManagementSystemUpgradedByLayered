package lk.ijse.gdse.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.controller.tm.InterAccTransViewTM;
import lk.ijse.gdse.dto.InterAccountTransactionDTO;
import lk.ijse.gdse.dto.TransactionDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.InterAccountTransactionBO;
import lk.ijse.gdse.service.bo.custom.TransactionBO;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class ViewInterAccountTransactionFormController {

    @FXML
    private TableView<InterAccTransViewTM> tblInterAccTransaction;

    @FXML
    private TableColumn<?, ?> colIInterAccountTransactionID;

    @FXML
    private TableColumn<?, ?> colTransactionID;

    @FXML
    private TableColumn<?, ?> colAccount01;

    @FXML
    private TableColumn<?, ?> colAccount02;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    private final InterAccountTransactionBO interAccountTransactionBO= (InterAccountTransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.INTER_ACCOUNT_TRANSACTION_BO);
    private final TransactionBO transactionBO= (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);

    public void initialize(){

        colIInterAccountTransactionID.setCellValueFactory(new PropertyValueFactory<>("interAccountTransactionId"));
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colAccount01.setCellValueFactory(new PropertyValueFactory<>("account01Id"));
        colAccount02.setCellValueFactory(new PropertyValueFactory<>("account02Id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            List<InterAccountTransactionDTO> allTransactions = interAccountTransactionBO.getAllTransactions();
            for(InterAccountTransactionDTO interAccountTransactionDTO : allTransactions){
                String interAccountTransactionId = interAccountTransactionDTO.getInterAccountTransactionId();
                String transactionId = interAccountTransactionDTO.getTransactionId();
                String account01Id = interAccountTransactionDTO.getAccount01Id();
                String account02Id = interAccountTransactionDTO.getAccount02Id();
                double amount = interAccountTransactionDTO.getAmount();
                TransactionDTO transaction = transactionBO.getTransaction(transactionId);
                Date date = transaction.getDate();
                Time time = transaction.getTime();
                tblInterAccTransaction.getItems().add(new InterAccTransViewTM(interAccountTransactionId,transactionId,account01Id,account02Id,amount,date,time));
            }
            tblInterAccTransaction.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

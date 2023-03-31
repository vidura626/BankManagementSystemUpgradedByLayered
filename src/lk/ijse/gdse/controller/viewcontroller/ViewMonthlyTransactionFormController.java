package lk.ijse.gdse.controller.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.InterAccountTransactionBO;
import lk.ijse.gdse.service.bo.custom.TransactionBO;

public class ViewMonthlyTransactionFormController {

    @FXML
    private TableView<?> tblDailyTransaction;

    @FXML
    private TableColumn<?, ?> colTransactionID;

    @FXML
    private TableColumn<?, ?> colAccountID;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colType;

    private final InterAccountTransactionBO interAccountTransactionBO= (InterAccountTransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.INTER_ACCOUNT_TRANSACTION_BO);
    private final TransactionBO transactionBO= (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);

    public void initialize(){

    }
}

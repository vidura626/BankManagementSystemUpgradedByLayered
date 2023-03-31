package lk.ijse.gdse.controller.viewcontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.dto.AccountDetailsDTO;
import lk.ijse.gdse.dto.PendingLoansDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.AccountDetailsBO;
import lk.ijse.gdse.service.bo.custom.PendingLoansBO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewPendingLoansFormController {

    @FXML
    private TableView<PendingLoansDTO> tblPendingLoans;

    @FXML
    private TableColumn<?, ?> colPendingLoanID;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colLoanTypeID;

    @FXML
    private TableColumn<?, ?> colAccountID;

    @FXML
    private TableColumn<?, ?> colInstallments;

    @FXML
    private TableColumn<?, ?> colInstallmentAmount;

    private final PendingLoansBO pendingLoansBO= (PendingLoansBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.PENDING_LOANS_BO);
    private final AccountDetailsBO accountDetailsBO= (AccountDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ACCOUNT_DETAILS_BO);

    public void initialize(){
        try {
            colPendingLoanID.setCellValueFactory(new PropertyValueFactory<>("pendingLoanId"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            colLoanTypeID.setCellValueFactory(new PropertyValueFactory<>("loanTypeId"));
            colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
            colInstallments.setCellValueFactory(new PropertyValueFactory<>("installments"));
            colInstallmentAmount.setCellValueFactory(new PropertyValueFactory<>("installmentAmount"));
            ObservableList<PendingLoansDTO> list = FXCollections.observableArrayList();
            List<AccountDetailsDTO> active = accountDetailsBO.getAccountsByState("ACTIVE");
            for(AccountDetailsDTO accountDetailsDTO : active){
                ArrayList<PendingLoansDTO> collect = pendingLoansBO.getAllPendingLoans().stream().filter(pendingLoansDTO -> pendingLoansDTO.getAccountId().equals(accountDetailsDTO.getAccountId())).collect(Collectors.toCollection(ArrayList::new));
                list.addAll(collect);
            }
            tblPendingLoans.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

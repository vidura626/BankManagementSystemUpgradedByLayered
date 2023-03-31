package lk.ijse.gdse.controller.viewcontroller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.dto.AccountDetailsDTO;
import lk.ijse.gdse.dto.LoansDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.LoanDetailsBO;
import lk.ijse.gdse.service.bo.custom.LoansBO;

import java.sql.SQLException;
import java.util.List;

public class ViewSettledLoansFormController {

    @FXML
    private TableView<LoansDTO> tblSettledLoans;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colLoanTypeID;

    @FXML
    private TableColumn<?, ?> colAccountID;

    @FXML
    private TableColumn<?, ?> colIssuedDate;

    private final LoansBO loansBO = (LoansBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOANS_BO);
    private final LoanDetailsBO loanDetailsBO = (LoanDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOAN_DETAILS_BO);

    public void initialize(){
        loanAccountDetails();
    }

    private void loanAccountDetails() {
        setCellValueFactory();
        tblSettledLoans.getItems().clear();
        try {
            List<LoansDTO> loansByState = loansBO.getLoansByState(LoansBO.LoanState.SETTLED);
            for (LoansDTO loansDTO : loansByState) {
                loansDTO.setLoanTypeId(loanDetailsBO.getLoanTypeById(loansDTO.getLoanTypeId()).getLoanTypeId());
            }
            tblSettledLoans.getItems().addAll(loansByState);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colLoanTypeID.setCellValueFactory(new PropertyValueFactory<>(""));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>(""));
        colIssuedDate.setCellValueFactory(new PropertyValueFactory<>(""));

    }
}

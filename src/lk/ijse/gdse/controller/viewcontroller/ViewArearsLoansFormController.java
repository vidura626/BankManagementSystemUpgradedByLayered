package lk.ijse.gdse.controller.viewcontroller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.dto.AccountDetailsDTO;
import lk.ijse.gdse.dto.LoanInstallmentDTO;
import lk.ijse.gdse.dto.LoansDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.AccountDetailsBO;
import lk.ijse.gdse.service.bo.custom.LoanInstalmentBO;
import lk.ijse.gdse.service.bo.custom.LoansBO;
import lk.ijse.gdse.service.bo.custom.TransactionBO;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class ViewArearsLoansFormController {

    @FXML
    private TableView<LoansDTO> tblArrearsLoans;

    @FXML
    private TableColumn<?, ?> colLoanID;

    @FXML
    private TableColumn<?, ?> colArrearsDateFrom;

    @FXML
    private TableColumn<?, ?> colAmount;

    private final LoansBO loansBO = (LoansBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOANS_BO);
    private final LoanInstalmentBO loanInstalmentBO = (LoanInstalmentBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOAN_INSTALLMENT_BO);
    private final AccountDetailsBO accountDetailsBO = (AccountDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ACCOUNT_DETAILS_BO);
    private final TransactionBO transactionBO = (TransactionBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.TRANSACTION_BO);

    public void initialize(){
        colLoanID.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colArrearsDateFrom.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        try {
            ObservableList<LoansDTO> items = tblArrearsLoans.getItems();
            String date=null;
            List<LoansDTO> loansByState = loansBO.getLoansByState(LoansBO.LoanState.NOTSETTLED);
            for (LoansDTO loansDTO : loansByState) {
                if (!accountDetailsBO.isExistAndActive(loansDTO.getAccountId())) continue;
                List<LoanInstallmentDTO> loanInstallmentsByLoanId = loanInstalmentBO.getLoanInstallmentsByLoanId(loansDTO.getLoanId());
                if(loanInstallmentsByLoanId.size()==0) {
                    double i = calculateMonths(loansDTO.getIssuedDate().toLocalDate(), LocalDate.now());
                    if(i>3){
                        double arearsAmount = i * loansDTO.getInstallmentAmount();
                        loansDTO.setAmount(arearsAmount);
                        items.add(loansDTO);
                        continue;
                    }
                    continue;
                }
                String transactionId = loanInstallmentsByLoanId.get(loanInstallmentsByLoanId.size() - 1).getTransactionId();
                LocalDate lastInstallmentDate = transactionBO.getTransaction(transactionId).getDate().toLocalDate();
                double i = calculateMonths(lastInstallmentDate, LocalDate.now());
                if(i>3){
                    double arearsAmount = i * loansDTO.getInstallmentAmount();
                    loansDTO.setAmount(arearsAmount);
                    loansDTO.setIssuedDate(Date.valueOf(lastInstallmentDate));
                }
            }
            tblArrearsLoans.setItems(items);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    double calculateMonths(LocalDate before, LocalDate after){
        Period between = Period.between(before, after);
        double years = between.getYears();
        double months = between.getMonths();
        months+=(years*12);
        return months;
    }
}

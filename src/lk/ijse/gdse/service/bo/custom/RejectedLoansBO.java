package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dto.RejectedLoansDTO;
import lk.ijse.gdse.service.bo.SuperBO;

import java.sql.SQLException;
import java.util.List;

public interface RejectedLoansBO extends SuperBO {
    public String getLoanTypeIdById(String loanId) throws SQLException;
    public String generateNextId() throws SQLException;
    public String getReasonById(String loanId) throws SQLException;
    public String getAccountNumberById(String loanId) throws SQLException;
    public boolean createRejectedLoan(RejectedLoansDTO RejectedLoansDTO) throws SQLException;
    public RejectedLoansDTO getRejectedLoanById(String RejectedLoanId) throws SQLException;
    public List<RejectedLoansDTO> getAllRejectedLoans() throws SQLException;
    public List<RejectedLoansDTO> getRejectedLoansByAmount(double amount) throws SQLException;
    public List<RejectedLoansDTO> getRejectedLoansByLoanType(String loanTypeId) throws SQLException;
    public List<RejectedLoansDTO> getRejectedLoansByAccountNumber(String accountNumber) throws SQLException;
}

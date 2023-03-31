package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.dto.PendingLoansDTO;
import lk.ijse.gdse.service.bo.SuperBO;

import java.sql.SQLException;
import java.util.List;

public interface PendingLoansBO extends SuperBO {
    public String getLoanTypeIdById(String loanId) throws SQLException;
    public String generateNextId() throws SQLException;
    public String getAccountNumberById(String loanId) throws SQLException;
    public boolean createPendingLoan(PendingLoansDTO pendingLoansDTO) throws SQLException;
    public boolean updatePendingLoan(PendingLoansDTO pendingLoansDTO) throws SQLException, NoCodeException;
    public boolean deletePendingLoan(String pendingLoanId) throws SQLException, NoCodeException;
    public PendingLoansDTO getPendingLoanById(String pendingLoanId) throws SQLException;
    public List<PendingLoansDTO> getAllPendingLoans() throws SQLException;
    public List<PendingLoansDTO> getPendingLoansByAmount(double amount) throws SQLException;
    public List<PendingLoansDTO> getPendingLoansByAccountNumber(String accountNumber) throws SQLException;
    public List<PendingLoansDTO> getPendingLoansByLoanType(String loanTypeId) throws SQLException;
}

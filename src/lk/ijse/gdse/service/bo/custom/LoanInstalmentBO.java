package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dto.LoanInstallmentDTO;
import lk.ijse.gdse.service.bo.SuperBO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface LoanInstalmentBO extends SuperBO {
    public boolean makeLoanInstallment(LoanInstallmentDTO loanInstallmentDTO) throws SQLException;
    public String getTransactionIdById(String loanInstallmentId) throws SQLException;
    public String generateNextId() throws SQLException;
    public String getLoanIdById(String loanInstallmentId) throws SQLException;
    public LoanInstallmentDTO getInstallmentById(String loanInstallmentId) throws SQLException;
    public LoanInstallmentDTO getInstallmentByTransactionId(String transactionId) throws SQLException;
    public List<LoanInstallmentDTO> getAllLoanInstallments() throws SQLException;
    public List<LoanInstallmentDTO> getLoanInstallmentsByLoanId(String loanId) throws SQLException;
}

package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.dto.LoansDTO;
import lk.ijse.gdse.service.bo.SuperBO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface LoansBO extends SuperBO {
    public boolean createLoan(LoansDTO loansDTO) throws SQLException;
    public String getLoanTypeIdById(String loanId) throws SQLException;
    public String generateNextId() throws SQLException;
    public String getAccountNumberById(String loanId) throws SQLException;
    public double getRemainingLoanAmount(String loanId) throws SQLException;
    public boolean updateRemainingLoanAmount(String loanId,double newAmount) throws SQLException, NoCodeException;
    public int getRemainingInstallment(String loanId) throws SQLException;
    public boolean updateRemainingInstallment(String loanId,int newAmount) throws SQLException, NoCodeException;
    public double getInterestAmount(String loanId) throws SQLException;
    public boolean updateInterestAmount(String loanId,double amount) throws SQLException, NoCodeException;
    public String getState(String loanId) throws SQLException;
    public boolean updateState(String loanId, LoanState state) throws SQLException;
    public LoansDTO getLoanById(String loanId) throws SQLException;
    public List<LoansDTO> getLoansByAccountNumber(String acountNumber) throws SQLException;
    public List<LoansDTO> getLoansByLoanType(String loanTypeId) throws SQLException;
    public List<LoansDTO> getLoansByAmount(double amount) throws SQLException;
    public List<LoansDTO> getLoansByDate(Date issuedDate) throws SQLException;
    public List<LoansDTO> getLoansByFromToDate(Date from,Date to) throws SQLException;
    public List<LoansDTO> getLoansByState(LoanState state) throws SQLException;
    public List<LoansDTO> getAllLoans() throws SQLException;

    public enum LoanState{SETTLED,NOTSETTLED}
}

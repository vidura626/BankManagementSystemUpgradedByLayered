package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Loans;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface LoansDAO extends CrudDAO<Loans> {
    public List<Loans> searchLoansByAccountId(String accountId) throws SQLException;
    public List<Loans> searchLoansByLoanTypeId(String loanTypeId) throws SQLException;
    public List<Loans> searchLoansByLoanIssuedDate(Date issuedDate) throws SQLException;
    public List<Loans> searchLoansByState(String state) throws SQLException;
    public boolean updateRemainingInstallmentCountByPk(String loanId,int newCount) throws SQLException;
    public boolean updateRemainingInstallmentAmountByPk(String loanId,double newAmount) throws SQLException;
    public boolean setInterestAmountByPk(String loanId,double amount) throws SQLException;
    public double getInterestAmountByPk(String loanId) throws SQLException;
    public String getStateByPk(String loanId) throws SQLException;
    public boolean setStateByPk(String loanId,String state) throws SQLException;

}
package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Loans;
import lk.ijse.gdse.entity.PendingLoans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface PendingLoansDAO extends CrudDAO<PendingLoans> {
    public List<PendingLoans> searchLoansByAccountId(String accountId) throws SQLException;
    public List<PendingLoans> searchLoansByLoanTypeId(String loanTypeId) throws SQLException;
    public List<PendingLoans> searchLoansByLoanAmount(double amount) throws SQLException;
}

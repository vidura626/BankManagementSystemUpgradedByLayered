package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.RejectedLoans;

import java.sql.SQLException;
import java.util.List;

public interface RejectedLoansDAO extends CrudDAO<RejectedLoans> {
    public List<RejectedLoans> searchLoansByAccountId(String accountId) throws SQLException;
    public List<RejectedLoans> searchLoansByLoanTypeId(String loanTypeId) throws SQLException;
    public List<RejectedLoans> searchLoansByLoanAmount(double amount) throws SQLException;
    public String searchReasonByPk(String rejLoanId) throws SQLException;

}

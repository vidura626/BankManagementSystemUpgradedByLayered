package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.LoanDetails;

import java.sql.SQLException;

public interface LoanDetailsDAO extends CrudDAO<LoanDetails> {
    public double getInterestByPk(String loanTypeId) throws SQLException;
    public boolean updateInterestByPk(String loanTypeId,double newInterest) throws SQLException;
}

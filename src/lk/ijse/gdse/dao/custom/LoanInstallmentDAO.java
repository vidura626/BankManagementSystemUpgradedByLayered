package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.dao.SearchByTransactionId;
import lk.ijse.gdse.entity.LoanInstallment;

import java.sql.SQLException;

public interface LoanInstallmentDAO extends CrudDAO<LoanInstallment>, SearchByTransactionId<LoanInstallment> {
    public String getLoanIdByTransactionId(String transactionId) throws SQLException;
}

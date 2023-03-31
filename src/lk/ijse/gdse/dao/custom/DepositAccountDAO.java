package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.DepositAccount;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface DepositAccountDAO extends CrudDAO<DepositAccount> {
    public List<DepositAccount> searchByCreatedDate(Date createdDate) throws SQLException;
    public double getBalanceByPk(String depositTypeAccountId) throws SQLException;
    public boolean updateBalanceByPk(String depositTypeAccountId, double newBalance) throws SQLException;
    public String getDepositTypeAccountId(String accountId, String depositType) throws SQLException;
}

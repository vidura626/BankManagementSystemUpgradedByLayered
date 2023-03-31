package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.dao.SearchByTransactionId;
import lk.ijse.gdse.entity.Withdrawal;

import java.sql.SQLException;
import java.util.List;

public interface WithdrawalDAO extends CrudDAO<Withdrawal>, SearchByTransactionId<Withdrawal> {
    public List<Withdrawal> searchByDepositTypeAccountId(String depositTypeAccountId) throws SQLException;
    public double searchAmountByTransactionId(String transactionId) throws SQLException;
    public double searchAmountByPk(String withdrawalId) throws SQLException;
}

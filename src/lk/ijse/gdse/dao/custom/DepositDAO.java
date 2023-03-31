package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.dao.SearchByTransactionId;
import lk.ijse.gdse.entity.Deposit;

import java.sql.SQLException;
import java.util.List;

public interface DepositDAO extends CrudDAO<Deposit>, SearchByTransactionId<Deposit> {
    public List<Deposit> searchByDepositTypeAcountId(String depositTypeAcountId) throws SQLException;
}

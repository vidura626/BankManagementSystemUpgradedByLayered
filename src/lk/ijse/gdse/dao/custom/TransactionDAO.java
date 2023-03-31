package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface TransactionDAO extends CrudDAO<Transaction> {
    public List<Transaction> searchByAccountId(String accountId) throws SQLException;
    public List<Transaction> searchByDate(Date date) throws SQLException;
    public List<Transaction> searchByType(String type) throws SQLException;
}

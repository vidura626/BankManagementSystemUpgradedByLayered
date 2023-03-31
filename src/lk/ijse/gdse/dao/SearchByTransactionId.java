package lk.ijse.gdse.dao;

import java.sql.SQLException;

public interface SearchByTransactionId<T> extends SuperDAO {
    public T searchByTransactionId(String transactionId) throws SQLException;
}

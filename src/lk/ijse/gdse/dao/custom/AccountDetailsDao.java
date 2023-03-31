package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.AccountDetails;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface AccountDetailsDao extends CrudDAO<AccountDetails> {
    public AccountDetails searchByNic(String nic) throws SQLException;
    public List<AccountDetails> searchByCreatedDate(Date createdDate) throws SQLException;
    public List<AccountDetails> searchByAddress(String address) throws SQLException;
    public List<AccountDetails> searchByState(String state) throws SQLException;
}

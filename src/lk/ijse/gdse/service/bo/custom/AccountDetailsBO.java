package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.dto.AccountDetailsDTO;
import lk.ijse.gdse.service.bo.SuperBO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface AccountDetailsBO extends SuperBO {

    public String getAccountNumberByNic(String nic) throws SQLException;
    public String generateAccountNumber();
    public boolean creatAccount(AccountDetailsDTO accountDetailsDTO) throws SQLException;
    public boolean updateAccount(AccountDetailsDTO accountDetailsDTO) throws SQLException, NoCodeException;
    public boolean closeAccount(String accountNumber) throws SQLException, NoCodeException;
    public boolean isExists(String accountNumber) throws SQLException;
    public boolean isActive(String accountNumber) throws SQLException;
    public boolean isExistAndActive(String accountNumber) throws SQLException;

    public AccountDetailsDTO getAccountByAccountNumber(String accountNumber) throws SQLException;
    public AccountDetailsDTO getAccountByNic(String nic) throws SQLException;

    public List<AccountDetailsDTO> getAllAccounts() throws SQLException;
    public List<AccountDetailsDTO> getAccountsByLocation(String location);
    public List<AccountDetailsDTO> getAccountsByCreatedDate(Date createdDate) throws SQLException;
    public List<AccountDetailsDTO> getAccountsByState(String state) throws SQLException;


    boolean reActiveAccount(String nic) throws SQLException, NoCodeException;
}

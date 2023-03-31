package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.custom.AccountDetailsDao;
import lk.ijse.gdse.dao.custom.imple.AccountDetailsDAOImple;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.AccountDetailsDTO;
import lk.ijse.gdse.entity.AccountDetails;
import lk.ijse.gdse.service.bo.custom.AccountDetailsBO;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDetailsBOImple implements AccountDetailsBO {
    private final AccountDetailsDao accountDetailsDao=new AccountDetailsDAOImple(DbConnection.getDbConnection().getConnection());

    @Override
    public String getAccountNumberByNic(String nic) throws SQLException {
        return accountDetailsDao.searchByNic(nic).getAccountId();
    }

    @Override
    public String generateAccountNumber() {

        return String.format("%02d", LocalTime.now().getSecond())
                + "" +
                String.format("%03d", LocalDate.now().getDayOfYear())
                + String.format("%03d", LocalTime.now().getHour()+100)
                + "" +
                +LocalDate.now().getYear()
                + String.format("%02d", LocalTime.now().getMinute());
    }

    @Override
    public boolean creatAccount(AccountDetailsDTO accountDetailsDTO) throws SQLException {
        return accountDetailsDao.add(Convert.toAccountDetails(accountDetailsDTO));
    }

    @Override
    public boolean updateAccount(AccountDetailsDTO accountDetailsDTO) throws SQLException, NoCodeException {
        return accountDetailsDao.update(Convert.toAccountDetails(accountDetailsDTO));
    }

    @Override
    public boolean closeAccount(String accountNumber) throws SQLException, NoCodeException {
        AccountDetails accountDetails = accountDetailsDao.searchByPk(accountNumber);
        accountDetails.setState("CLOSED");
        return updateAccount(Convert.fromAccountDetails(accountDetails));
    }

    @Override
    public boolean isExists(String accountNumber) throws SQLException {
        return accountDetailsDao.isExist(accountNumber);
    }

    @Override
    public boolean isActive(String accountNumber) throws SQLException {
        return accountDetailsDao.searchByState("ACTIVE").stream().filter(accountDetails -> accountDetails.getAccountId().equals(accountNumber)).collect(Collectors.toCollection(ArrayList::new)).size()>0;
    }

    @Override
    public boolean isExistAndActive(String accountNumber) throws SQLException {
        return isExists(accountNumber) && isActive(accountNumber);
    }

    @Override
    public AccountDetailsDTO getAccountByAccountNumber(String accountNumber) throws SQLException {
        return Convert.fromAccountDetails(accountDetailsDao.searchByPk(accountNumber));
    }

    @Override
    public AccountDetailsDTO getAccountByNic(String nic) throws SQLException {
        return Convert.fromAccountDetails(accountDetailsDao.searchByNic(nic));
    }

    @Override
    public List<AccountDetailsDTO> getAllAccounts() throws SQLException {
        return accountDetailsDao.getAll().stream().map(Convert::fromAccountDetails).collect(Collectors.toCollection(ArrayList<AccountDetailsDTO>::new));
    }

    @Override
    public List<AccountDetailsDTO> getAccountsByLocation(String location) {
        return null;
    }

    @Override
    public List<AccountDetailsDTO> getAccountsByCreatedDate(Date createdDate) throws SQLException {
        return getAllAccounts().stream().filter(a -> a.getRegDate().equals(createdDate)).collect(Collectors.toList());
    }

    @Override
    public List<AccountDetailsDTO> getAccountsByState(String state) throws SQLException {
        return getAllAccounts().stream().filter(a -> a.getState().equals(state)).collect(Collectors.toList());
    }

    @Override
    public boolean reActiveAccount(String nic) throws SQLException, NoCodeException {
        AccountDetails accountDetails = accountDetailsDao.searchByNic(nic);
        accountDetails.setState("ACTIVE");
        return accountDetailsDao.update(accountDetails);
    }
}

package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.DepositAccountDAO;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.DepositAccountDTO;
import lk.ijse.gdse.service.bo.custom.DepositAccountBO;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DepositAccountBOImple implements DepositAccountBO {

    DepositAccountDAO depositAccountDAO= (DepositAccountDAO) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.DEPOSIT_ACCOUNT, DbConnection.getDbConnection().getConnection());

    @Override
    public boolean addDepositAccount(DepositAccountDTO depositAccountDTO) throws SQLException {
        return depositAccountDAO.add(Convert.toDepositAccount(depositAccountDTO));
    }

    @Override
    public String getAccountIdById(String depositTypeAccountId) throws SQLException {
        return depositAccountDAO.searchByPk(depositTypeAccountId).getAccountId();
    }

    @Override
    public String generateNextId() throws SQLException {
        return depositAccountDAO.generateNextId();
    }

    @Override
    public String getDepositTypeIdById(String depositTypeAccountId) throws SQLException {
        return depositAccountDAO.searchByPk(depositTypeAccountId).getDepositTypeId();
    }

    @Override
    public double getBalanceById(String depositTypeAccountId) throws SQLException {
        return depositAccountDAO.searchByPk(depositTypeAccountId).getBalance();
    }

    @Override
    public boolean setBalanceById(String depositTypeAccountId, double amount) throws SQLException {
        return depositAccountDAO.updateBalanceByPk(depositTypeAccountId, amount);
    }

    @Override
    public DepositAccountDTO getDepositAccountById(String depositTypeAccountId) throws SQLException {
        return Convert.fromDepositAccount(depositAccountDAO.searchByPk(depositTypeAccountId));
    }

    @Override
    public List<DepositAccountDTO> getAllDepositAccounts() throws SQLException {
        return depositAccountDAO.getAll().stream().map(Convert::fromDepositAccount).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<DepositAccountDTO> getDepositAccountsByDate(Date createdDate) throws SQLException {
        return depositAccountDAO.getAll().stream().filter(d -> d.getCreatedDate().equals(createdDate)).map(Convert::fromDepositAccount).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<DepositAccountDTO> getDepositAccountsByAccountNumber(String accountNumber) throws SQLException {
        return depositAccountDAO.getAll().stream().filter(d -> d.getAccountId().equals(accountNumber)).map(Convert::fromDepositAccount).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<DepositAccountDTO> getDepositAccountsByDepositTypeId(String depositeTypeId) throws SQLException {
        return depositAccountDAO.getAll().stream().filter(d -> d.getDepositTypeAccountId().equals(depositeTypeId)).map(Convert::fromDepositAccount).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public int countDepositAccountsByBalance(double balance, CheckState state) throws SQLException {
        switch (state){
            case LESS_THAN:
                return depositAccountDAO.getAll().stream().filter(d -> d.getBalance() < balance).map(Convert::fromDepositAccount).collect(Collectors.toCollection(ArrayList::new)).size();
            case GREATER_THAN:
                return depositAccountDAO.getAll().stream().filter(d -> d.getBalance() > balance).map(Convert::fromDepositAccount).collect(Collectors.toCollection(ArrayList::new)).size();
            case GREATER_THAN_OR_EQUAL:
                return depositAccountDAO.getAll().stream().filter(d -> d.getBalance() >= balance).map(Convert::fromDepositAccount).collect(Collectors.toCollection(ArrayList::new)).size();
            default:
                return -1;
        }
    }
}

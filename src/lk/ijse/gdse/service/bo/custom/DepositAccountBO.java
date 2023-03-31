package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dto.DepositAccountDTO;
import lk.ijse.gdse.service.bo.SuperBO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface DepositAccountBO extends SuperBO {

    public boolean addDepositAccount(DepositAccountDTO depositAccountDTO) throws SQLException;
    public String getAccountIdById(String depositTypeAccountId) throws SQLException;
    public String generateNextId() throws SQLException;
    public String getDepositTypeIdById(String depositTypeAccountId) throws SQLException;
    public int countDepositAccountsByBalance(double balance,CheckState state) throws SQLException;
    public double getBalanceById(String depositTypeAccountId) throws SQLException;
    public boolean setBalanceById(String depositTypeAccountId,double amount) throws SQLException;
    public DepositAccountDTO getDepositAccountById(String DepositTypeAccountId) throws SQLException;
    public List<DepositAccountDTO> getAllDepositAccounts() throws SQLException;
    public List<DepositAccountDTO> getDepositAccountsByDate(Date createdDate) throws SQLException;
    public List<DepositAccountDTO> getDepositAccountsByAccountNumber(String accountNumber) throws SQLException;
    public List<DepositAccountDTO> getDepositAccountsByDepositTypeId(String depositeTypeId) throws SQLException;

    public enum CheckState{ GREATER_THAN,GREATER_THAN_OR_EQUAL,LESS_THAN }
}

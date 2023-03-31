package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dto.DepositDTO;
import lk.ijse.gdse.service.bo.SuperBO;
import lk.ijse.gdse.service.bo.exception.YetProcessingException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface DepositBO extends SuperBO {

    public boolean makeDeposit(DepositDTO depositDTO) throws SQLException;

    public String getDepositTypeAccountIdById(String depositId);
    public String generateNextId() throws SQLException;
    public String getTransactionIdById(String transactionId);

    public double getAmountByDepositId(String depositId) throws YetProcessingException;
    public double getTotalOfDepositsByDate(Date date) throws YetProcessingException;
    public double getTotalOfDepositsForNow();

    public int countDepositsFomToDate(Date from,Date to);
    public int countDepositsByDate(Date date);

    public DepositDTO getDepositByDepositId(String depositId) throws SQLException;
    public DepositDTO getDepositsByTransactionId(String transactionId) throws SQLException;

    public List<DepositDTO> getAllDeposits() throws SQLException;
    public List<DepositDTO> getDepositsByDepositTypeAccountsId(String depositTypeAccountId) throws SQLException;

}

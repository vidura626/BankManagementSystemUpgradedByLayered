package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dto.WithdrawalDTO;
import lk.ijse.gdse.service.bo.SuperBO;

import java.sql.SQLException;
import java.util.List;

public interface WithdrawalBO extends SuperBO {
    public String generateNextId() throws SQLException;
    public boolean makeWithdraw(WithdrawalDTO withdrawalDTO) throws SQLException;
    public String getTransactionId(String withdrawalId) throws SQLException;
    public String getDepositTypeAccountId(String withdrawalId) throws SQLException;
    public WithdrawalDTO getWithdraw(String withdrawalId) throws SQLException;
    public WithdrawalDTO getWithdrawByTransactionId(String transactionId) throws SQLException;
    public List<WithdrawalDTO> getAllWithdraws() throws SQLException;
    public List<WithdrawalDTO> getAllWithdrawsByDepositTypeAccountId(String transactionId) throws SQLException;
}

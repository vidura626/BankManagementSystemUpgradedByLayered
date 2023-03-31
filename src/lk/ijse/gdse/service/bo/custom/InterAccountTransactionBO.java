package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dto.InterAccountTransactionDTO;
import lk.ijse.gdse.service.bo.SuperBO;

import java.sql.SQLException;
import java.util.List;

public interface InterAccountTransactionBO extends SuperBO {
    public String getTransactionIdById(String interAccountTransactionId) throws SQLException;
    public String generateNextId() throws SQLException;
    public String getSenderAccountIdById(String interAccountTransactionId) throws SQLException;
    public String getReceiverAccountIdById(String interAccountTransactionId) throws SQLException;
    public boolean makeInterAccountTransaction(InterAccountTransactionDTO interAccountTransactionDTO) throws SQLException;
    public double getAmountById(String interAccountTransactionId) throws SQLException;
    public double getAmountByTransactionId(String transactionId) throws SQLException;
    public InterAccountTransactionDTO getTransactionById(String interAccountTransactionDTO) throws SQLException;
    public List<InterAccountTransactionDTO> getAllTransactions() throws SQLException;
    public List<InterAccountTransactionDTO> getTransactionsBySenderId(String senderAccountNumber) throws SQLException;
    public List<InterAccountTransactionDTO> getTransactionsByReceiverId(String senderAccountNumber) throws SQLException;

}

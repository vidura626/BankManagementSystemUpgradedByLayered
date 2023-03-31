package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dto.TransactionDTO;
import lk.ijse.gdse.service.bo.SuperBO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface TransactionBO extends SuperBO {
    public boolean makeTransaction(TransactionDTO transactionDTO) throws SQLException;
    public String getAccountNumber(String transactionId) throws SQLException;
    public String generateNextId() throws SQLException;
    public TransactionDTO getTransaction(String transactionId) throws SQLException;
    public List<TransactionDTO> getAllTransactions() throws SQLException;
    public List<TransactionDTO> getTransactionsByDate(Date date) throws SQLException;
    public List<TransactionDTO> getTransactionsByFromToDate(Date from,Date to) throws SQLException;
    public List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber) throws SQLException;
    public List<TransactionDTO> getTransactionsByType(TransactionTypeState type) throws SQLException;
    public List<TransactionDTO> getTransactionsByFilterAmount(double amount,TransactionFilter type) throws SQLException;

    public enum TransactionTypeState{MONEY_IN,MONEY_OUT,MONEY_TRANSFER}
    public enum TransactionFilter{GREATER_THAN_OR_EQUAL,LESS_THAN_OR_EQUAL,GREATER_THAN,LESS_THAN,EQUAL}
}

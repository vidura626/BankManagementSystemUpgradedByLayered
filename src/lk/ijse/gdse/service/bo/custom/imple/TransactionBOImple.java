package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.TransactionDAO;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.TransactionDTO;
import lk.ijse.gdse.service.bo.custom.TransactionBO;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionBOImple implements TransactionBO {
    private final TransactionDAO transactionDAO= (TransactionDAO) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.TRANSACTION, DbConnection.getDbConnection().getConnection());
    @Override
    public boolean makeTransaction(TransactionDTO transactionDTO) throws SQLException {
        return transactionDAO.add(Convert.toTransaction(transactionDTO));
    }

    @Override
    public String getAccountNumber(String transactionId) throws SQLException {
        return transactionDAO.searchByPk(transactionId).getAccountId();
    }

    @Override
    public String generateNextId() throws SQLException {
        return transactionDAO.generateNextId();
    }

    @Override
    public TransactionDTO getTransaction(String transactionId) throws SQLException {
        return Convert.fromTransaction(transactionDAO.searchByPk(transactionId));
    }

    @Override
    public List<TransactionDTO> getAllTransactions() throws SQLException {
        return transactionDAO.getAll().stream().map(Convert::fromTransaction).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsByDate(Date date) throws SQLException {
        return getAllTransactions().stream().filter(transactionDTO -> transactionDTO.getDate().equals(date)).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsByFromToDate(Date from, Date to) throws SQLException {
        return getAllTransactions().stream().filter(t -> t.getDate().compareTo(from) >= 0 && t.getDate().compareTo(to) <=0).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber) throws SQLException {
        return getAllTransactions().stream().filter(transactionDTO -> transactionDTO.getAccountId().equals(accountNumber)).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsByType(TransactionTypeState type) throws SQLException {
        return getAllTransactions().stream().filter(transactionDTO -> transactionDTO.getType().equals(type.toString())).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsByFilterAmount(double amount, TransactionFilter type) throws SQLException {
        switch (type){
            case EQUAL:
                return getAllTransactions().stream().filter(transactionDTO -> transactionDTO.getAmount() == amount).collect(Collectors.toList());
            case LESS_THAN:
                getAllTransactions().stream().filter(transactionDTO -> transactionDTO.getAmount() < amount).collect(Collectors.toList());
            case GREATER_THAN:
                getAllTransactions().stream().filter(transactionDTO -> transactionDTO.getAmount() > amount).collect(Collectors.toList());
            case GREATER_THAN_OR_EQUAL:
                getAllTransactions().stream().filter(transactionDTO -> transactionDTO.getAmount() >= amount).collect(Collectors.toList());
            case LESS_THAN_OR_EQUAL:
                getAllTransactions().stream().filter(transactionDTO -> transactionDTO.getAmount() <= amount).collect(Collectors.toList());
            default:
                return null;
        }
    }
}

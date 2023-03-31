package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.InterAccountTransactionDAO;
import lk.ijse.gdse.dao.custom.TransactionDAO;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.InterAccountTransactionDTO;
import lk.ijse.gdse.service.bo.custom.InterAccountTransactionBO;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class InterAccountTransactionBOImple implements InterAccountTransactionBO {
    private final InterAccountTransactionDAO interAccountTransactionDAO= (InterAccountTransactionDAO) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.INTER_ACCOUNT_TRANSACTION, DbConnection.getDbConnection().getConnection());
    @Override
    public String getTransactionIdById(String interAccountTransactionId) throws SQLException {
        return interAccountTransactionDAO.searchByPk(interAccountTransactionId).getTransactionId();
    }

    @Override
    public String generateNextId() throws SQLException {
        return interAccountTransactionDAO.generateNextId();
    }

    @Override
    public String getSenderAccountIdById(String interAccountTransactionId) throws SQLException {
        return interAccountTransactionDAO.searchByPk(interAccountTransactionId).getAccount01Id();
    }

    @Override
    public String getReceiverAccountIdById(String interAccountTransactionId) throws SQLException {
        return interAccountTransactionDAO.searchByPk(interAccountTransactionId).getAccount02Id();
    }

    @Override
    public boolean makeInterAccountTransaction(InterAccountTransactionDTO interAccountTransactionDTO) throws SQLException {
        return interAccountTransactionDAO.add(Convert.toInterAccountTransaction(interAccountTransactionDTO));
    }

    @Override
    public double getAmountById(String interAccountTransactionId) throws SQLException {
        return interAccountTransactionDAO.searchByPk(interAccountTransactionId).getAmount();
    }

    @Override
    public double getAmountByTransactionId(String transactionId) throws SQLException {
        return interAccountTransactionDAO.searchByTransactionId(transactionId).getAmount();
    }

    @Override
    public InterAccountTransactionDTO getTransactionById(String interAccountTransactionDTO) throws SQLException {
        return Convert.fromInterAccountTransaction(interAccountTransactionDAO.searchByPk(interAccountTransactionDTO));
    }

    @Override
    public List<InterAccountTransactionDTO> getAllTransactions() throws SQLException {
        return interAccountTransactionDAO.getAll().stream().map(Convert::fromInterAccountTransaction).collect(Collectors.toList());
    }

    @Override
    public List<InterAccountTransactionDTO> getTransactionsBySenderId(String senderAccountNumber) throws SQLException {
        return getAllTransactions().stream().filter(i -> i.getAccount01Id().equals(senderAccountNumber)).collect(Collectors.toList());
    }

    @Override
    public List<InterAccountTransactionDTO> getTransactionsByReceiverId(String receiverAccountNumber) throws SQLException {
        return getAllTransactions().stream().filter(i -> i.getAccount02Id().equals(receiverAccountNumber)).collect(Collectors.toList());
    }
}

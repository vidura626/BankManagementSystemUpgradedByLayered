package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.WithdrawalDAO;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.WithdrawalDTO;
import lk.ijse.gdse.service.bo.custom.WithdrawalBO;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class WithdrawalBOImple implements WithdrawalBO {
    private final WithdrawalDAO withdrawalDAO= (WithdrawalDAO) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.WITHDRAWAL, DbConnection.getDbConnection().getConnection());

    @Override
    public String generateNextId() throws SQLException {
        return withdrawalDAO.generateNextId();
    }

    @Override
    public boolean makeWithdraw(WithdrawalDTO withdrawalDTO) throws SQLException {
        return withdrawalDAO.add(Convert.toWithdrawal(withdrawalDTO));
    }

    @Override
    public String getTransactionId(String withdrawalId) throws SQLException {
        return withdrawalDAO.searchByPk(withdrawalId).getTransactionId();
    }

    @Override
    public String getDepositTypeAccountId(String withdrawalId) throws SQLException {
        return withdrawalDAO.searchByPk(withdrawalId).getWithdrawalId();
    }

    @Override
    public WithdrawalDTO getWithdraw(String withdrawalId) throws SQLException {
        return Convert.fromWithdrawal(withdrawalDAO.searchByPk(withdrawalId));
    }

    @Override
    public WithdrawalDTO getWithdrawByTransactionId(String transactionId) throws SQLException {
        return Convert.fromWithdrawal(withdrawalDAO.searchByTransactionId(transactionId));
    }

    @Override
    public List<WithdrawalDTO> getAllWithdraws() throws SQLException {
        return withdrawalDAO.getAll().stream().map(Convert::fromWithdrawal).collect(Collectors.toList());
    }

    @Override
    public List<WithdrawalDTO> getAllWithdrawsByDepositTypeAccountId(String depositTypeAccountId) throws SQLException {
        return withdrawalDAO.searchByDepositTypeAccountId(depositTypeAccountId).stream().map(Convert::fromWithdrawal).collect(Collectors.toList());
    }
}

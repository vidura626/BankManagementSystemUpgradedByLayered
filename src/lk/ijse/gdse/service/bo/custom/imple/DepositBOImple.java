package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.DepositDAO;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.DepositDTO;
import lk.ijse.gdse.entity.Deposit;
import lk.ijse.gdse.service.bo.custom.DepositBO;
import lk.ijse.gdse.service.bo.exception.YetProcessingException;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepositBOImple implements DepositBO {

    private final DepositDAO depositDAO= (DepositDAO) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.DEPOSIT, DbConnection.getDbConnection().getConnection());

    @Override
    public boolean makeDeposit(DepositDTO depositDTO) throws SQLException {
        return depositDAO.add(Convert.toDeposit(depositDTO));
    }

    @Override
    public String getDepositTypeAccountIdById(String depositId) {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        return depositDAO.generateNextId();
    }

    @Override
    public String getTransactionIdById(String transactionId) {
        return null;
    }

    @Override
    public double getAmountByDepositId(String depositId) throws YetProcessingException {
        return 0;
    }

    @Override
    public double getTotalOfDepositsByDate(Date date) throws YetProcessingException {
        return 0;
    }

    @Override
    public double getTotalOfDepositsForNow() {
        return 0;
    }

    @Override
    public int countDepositsFomToDate(Date from, Date to) {
        return 0;
    }

    @Override
    public int countDepositsByDate(Date date) {
        return 0;
    }

    @Override
    public DepositDTO getDepositByDepositId(String depositId) throws SQLException {
        List<Deposit> all = depositDAO.getAll();
        for (Deposit deposit : all) {
            if(deposit.getDepositId().equals(depositId)){
                return Convert.fromDeposit(deposit);
            }
        }
        return null;
    }

    @Override
    public DepositDTO getDepositsByTransactionId(String transactionId) throws SQLException {
        Deposit deposit = depositDAO.searchByTransactionId(transactionId);
        return Convert.fromDeposit(deposit);
    }

    @Override
    public List<DepositDTO> getAllDeposits() throws SQLException {
        List<Deposit> all = depositDAO.getAll();
        List<DepositDTO> list=new ArrayList<>();
        for (Deposit deposit : all) {
            list.add(Convert.fromDeposit(deposit));
        }
        return list;
    }


    @Override
    public List<DepositDTO> getDepositsByDepositTypeAccountsId(String depositTypeAccountId) throws SQLException {
        List<Deposit> all = depositDAO.getAll();
        List<DepositDTO> list=new ArrayList<>();
        for (Deposit deposit : all) {
            if(deposit.getDepositTypeAccountId().equals(depositTypeAccountId)){
                list.add(Convert.fromDeposit(deposit));
            }
        }
        return list;
    }
}

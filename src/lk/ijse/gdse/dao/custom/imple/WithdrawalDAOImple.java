package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.WithdrawalDAO;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.entity.Withdrawal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WithdrawalDAOImple implements WithdrawalDAO {
    private Connection connection;
    public WithdrawalDAOImple(Connection connection) {
        this.connection=connection;
    }

    @Override
    public boolean add(Withdrawal obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.withdrawal values (?,?,?,?)",
                obj.getWithdrawalId(),
                obj.getTransactionId(),
                obj.getDepositTypeAccountId(),
                obj.getAmount()
        );
    }

    @Override
    public boolean delete(String withdrawalId) throws SQLException, NoCodeException {
        throw new NoCodeException("WARNING! Cannot delete withdrawal");
    }

    @Override
    public boolean update(Withdrawal obj) throws SQLException, NoCodeException {
        throw new NoCodeException("WARNING! Cannot update withdrawal");
    }

    @Override
    public Withdrawal searchByPk(String withdrawalId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.withdrawal w where w.WithdrawalID=?",withdrawalId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<Withdrawal> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.withdrawal");
        return convert(rst, new ArrayList<Withdrawal>());
    }

    @Override
    public boolean isExist(String withdrawalId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.withdrawal w where w.WithdrawalID=?",withdrawalId);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT d.WithdrawalID FROM sanasa.withdrawal d ORDER BY d.WithdrawalID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString(1);
            int newCustomerId = Integer.parseInt(id.replace("W", "")) + 1;
            return String.format("W%08d", newCustomerId);
        } else {
            return "W00000001";
        }
    }

    private List<Withdrawal> convert(ResultSet rst, List<Withdrawal> arrayList) throws SQLException {
        while (rst.next()){
            arrayList.add(convert(rst));
        }
        return arrayList.size() > 0 ? arrayList : null;
    }

    private Withdrawal convert(ResultSet rst) throws SQLException {
        return new Withdrawal(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getDouble(4)
        );
    }

    @Override
    public List<Withdrawal> searchByDepositTypeAccountId(String depositTypeAccountId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.withdrawal w where w.DepositTypeAccountID=?",depositTypeAccountId);
        return convert(rst, new ArrayList<Withdrawal>());
    }

    @Override
    public double searchAmountByTransactionId(String transactionId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select w.Amount from sanasa.withdrawal w where w.TransactionID=?", transactionId);
        return rst.next() ? rst.getDouble(1) : -1;
    }

    @Override
    public double searchAmountByPk(String withdrawalId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select w.Amount from sanasa.withdrawal w where w.WithdrawalID=?", withdrawalId);
        return rst.next() ? rst.getDouble(1) : -1;
    }

    @Override
    public Withdrawal searchByTransactionId(String transactionId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.withdrawal w where w.TransactionID=?", transactionId);
        return rst.next() ? convert(rst) : null;
    }

}

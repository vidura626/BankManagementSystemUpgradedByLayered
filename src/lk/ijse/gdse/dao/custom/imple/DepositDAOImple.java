package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.DepositDAO;
import lk.ijse.gdse.entity.Deposit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepositDAOImple implements DepositDAO {
    private Connection connection;
    public DepositDAOImple(Connection connection) {
        this.connection=connection;
    }

    @Override
    public boolean add(Deposit obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.deposit values (?,?,?,?)",
                obj.getDepositId(),
                obj.getTransactionId(),
                obj.getDepositTypeAccountId(),
                obj.getAmount()
        );
    }

    @Override
    public boolean delete(String depositId) throws SQLException {
        return CrudUtil.execute("delete from sanasa.deposit d where d.DepositID = ?", depositId);
    }

    @Override
    public boolean update(Deposit obj) throws SQLException {
        return CrudUtil.execute(
                "update sanasa.deposit set deposit.TransactionID=?,deposit.DepositTypeAccountID=?,deposit.Amount=? where deposit.DepositID=?",
                obj.getTransactionId(),
                obj.getDepositTypeAccountId(),
                obj.getAmount(),
                obj.getDepositId()
        );
    }

    @Override
    public Deposit searchByPk(String depositId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.deposit d where d.DepositID = ?", depositId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<Deposit> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.deposit d");
        return convert(rst,new ArrayList<Deposit>());
    }

    @Override
    public boolean isExist(String depositId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.deposit d where d.DepositID = ?", depositId);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT d.DepositID FROM sanasa.deposit d ORDER BY d.DepositID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString(1);
            int newCustomerId = Integer.parseInt(id.replace("D", "")) + 1;
            return String.format("D%08d", newCustomerId);
        } else {
            return "D00000001";
        }
    }

    private List<Deposit> convert(ResultSet rst, List<Deposit> arrayList) throws SQLException {
        while (rst.next()){
            arrayList.add(convert(rst));
        }
        return arrayList.size() > 0 ? arrayList : null;
    }

    private Deposit convert(ResultSet rst) throws SQLException {
        return new Deposit(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getDouble(4)
        );
    }

    @Override
    public Deposit searchByTransactionId(String transactionId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.deposit d where d.TransactionID=?", transactionId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<Deposit> searchByDepositTypeAcountId(String depositTypeAcountId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.deposit d where d.DepositTypeAccountID=?", depositTypeAcountId);
        return convert(rst,new ArrayList<Deposit>());
    }
}

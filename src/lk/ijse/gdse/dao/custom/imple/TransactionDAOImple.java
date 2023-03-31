package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.TransactionDAO;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.entity.Transaction;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImple implements TransactionDAO {
    private Connection connection;
    public TransactionDAOImple(Connection connection) {
        this.connection=connection;
    }

    @Override
    public boolean add(Transaction obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.transaction values (?,?,?,?,?,?)",
                obj.getTransactionId(),
                obj.getAccountId(),
                obj.getAmount(),
                obj.getDate(),
                obj.getTime(),
                obj.getType()
        );
    }

    @Override
    public boolean delete(String transactionId) throws SQLException, NoCodeException {
        throw new NoCodeException("WARNING! Cannot delete transaction");
    }

    @Override
    public boolean update(Transaction obj) throws SQLException, NoCodeException {
        throw new NoCodeException("WARNING! Cannot update transaction");
    }

    @Override
    public Transaction searchByPk(String transactionId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.transaction t where t.TransactionID=?", transactionId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<Transaction> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.transaction");
        return convert(rst, new ArrayList<Transaction>());
    }

    @Override
    public boolean isExist(String transactionId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.transaction t where t.TransactionID=?", transactionId);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT d.TransactionID FROM sanasa.transaction d ORDER BY d.TransactionID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString(1);
            int newId = Integer.parseInt(id.replace("T", "")) + 1;
            return String.format("T%08d", newId);
        } else {
            return "T00000001";
        }
    }

    private List<Transaction> convert(ResultSet rst, List<Transaction> arrayList) throws SQLException {
        while (rst.next()){
            arrayList.add(convert(rst));
        }
        return arrayList.size() > 0 ? arrayList : null;
    }

    private Transaction convert(ResultSet rst) throws SQLException {
        return new Transaction(
                rst.getString(1),
                rst.getString(2),
                rst.getDouble(3),
                rst.getDate(4),
                rst.getTime(5),
                rst.getString(6)
        );
    }

    @Override
    public List<Transaction> searchByAccountId(String accountId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.transaction where transaction.AccountID=?",accountId);
        return convert(rst, new ArrayList<Transaction>());
    }

    @Override
    public List<Transaction> searchByDate(Date date) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.transaction where transaction.Date=?",date);
        return convert(rst, new ArrayList<Transaction>());
    }

    @Override
    public List<Transaction> searchByType(String type) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.transaction where transaction.Type=?",type);
        return convert(rst, new ArrayList<Transaction>());
    }
}

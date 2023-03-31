package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.InterAccountTransactionDAO;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.entity.InterAccountTransaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InterAccountTransactionDAOImple implements InterAccountTransactionDAO {

    public Connection connection;
    public InterAccountTransactionDAOImple(Connection connection) {
        this.connection=connection;
    }

    @Override
    public boolean add(InterAccountTransaction obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.interaccounttransaction values (?,?,?,?,?);",
                obj.getInterAccountTransactionId(),
                obj.getTransactionId(),
                obj.getAccount01Id(),
                obj.getAccount02Id(),
                obj.getAmount()
        );
    }

    @Override
    public boolean delete(String interAccountTransactionId) throws SQLException, NoCodeException {
        throw new NoCodeException("WARNING! Cannot delete InterAccountTransaction");
    }

    @Override
    public boolean update(InterAccountTransaction obj) throws SQLException, NoCodeException {
        throw new NoCodeException("WARNING! Cannot update InterAccountTransaction");
    }

    @Override
    public InterAccountTransaction searchByPk(String interAccountTransactionId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM sanasa.interaccounttransaction i where i.InterAccountTransactionID=? ",interAccountTransactionId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<InterAccountTransaction> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM sanasa.interaccounttransaction i");
        return convert(rst, new ArrayList<InterAccountTransaction>());
    }

    @Override
    public boolean isExist(String interAccountTransactionId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM sanasa.interaccounttransaction i where i.InterAccountTransactionID=? ",interAccountTransactionId);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT d.InterAccountTransactionID FROM sanasa.interaccounttransaction d ORDER BY d.InterAccountTransactionID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString(1);
            int newId = Integer.parseInt(id.replace("IT", "")) + 1;
            return String.format("IT%08d", newId);
        } else {
            return "IT00000001";
        }
    }

    private List<InterAccountTransaction> convert(ResultSet rst, List<InterAccountTransaction> arrayList) throws SQLException {
        while (rst.next()){
            arrayList.add(convert(rst));
        }
        return arrayList.size() > 0 ? arrayList : null;
    }

    private InterAccountTransaction convert(ResultSet rst) throws SQLException {
        return new InterAccountTransaction(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getDouble(5)
        );
    }

    @Override
    public InterAccountTransaction searchByTransactionId(String transactionId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select  * from sanasa.interaccounttransaction i where i.TransactionID=?", transactionId);
        return rst.next() ? convert(rst) : null;
    }
}

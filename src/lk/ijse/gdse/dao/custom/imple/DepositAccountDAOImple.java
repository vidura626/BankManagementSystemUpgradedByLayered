package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.DepositAccountDAO;
import lk.ijse.gdse.dao.exception.EmptyTableException;
import lk.ijse.gdse.entity.DepositAccount;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepositAccountDAOImple implements DepositAccountDAO {
    private Connection connection;
    public DepositAccountDAOImple(Connection connection) {
        this.connection=connection;
    }

    @Override
    public boolean add(DepositAccount obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.depositaccount values (?,?,?,?,?)",
                obj.getDepositTypeAccountId(),
                obj.getDepositTypeId(),
                obj.getAccountId(),
                obj.getCreatedDate(),
                obj.getBalance()
        );
    }

    @Override
    public boolean delete(String depositTypeAccountId) throws SQLException {
        return CrudUtil.execute("delete from sanasa.depositaccount d where d.DepositTypeAccountID = ?", depositTypeAccountId);
    }

    @Override
    public boolean update(DepositAccount obj) throws SQLException {
        return CrudUtil.execute("update sanasa.depositaccount set DepositTypeID = ?,depositaccount.AccountID=?,depositaccount.CreatedDate=?,depositaccount.Balance=? where depositaccount.DepositTypeAccountID=?",
                obj.getDepositTypeId(),
                obj.getAccountId(),
                obj.getCreatedDate(),
                obj.getBalance(),
                obj.getDepositTypeAccountId()
        );
    }

    @Override
    public DepositAccount searchByPk(String depositTypeAccountId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.depositaccount d where d.DepositTypeAccountID = ?", depositTypeAccountId);
        return rst.next() ? convert(rst) : new DepositAccount();
    }

    @Override
    public List<DepositAccount> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.depositaccount d");
        return convert(rst, new ArrayList<DepositAccount>());
    }

    @Override
    public boolean isExist(String depositTypeAccountId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.depositaccount d where d.DepositTypeAccountID = ?", depositTypeAccountId);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT d.DepositTypeAccountID FROM sanasa.depositaccount d ORDER BY d.AccountID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString(1);
            int newId = Integer.parseInt(id.replace("D", "")) + 1;
            return String.format("D%08d", newId);
        } else {
            return "D00000001";
        }
    }

    private List<DepositAccount> convert(ResultSet rst, List<DepositAccount> arrayList) throws SQLException {
        while (rst.next()) {
            arrayList.add(convert(rst));
        }
        return arrayList;
    }

    private DepositAccount convert(ResultSet rst) throws SQLException {
        return new DepositAccount(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getDate(4),
                rst.getDouble(5)
        );
    }

    @Override
    public List<DepositAccount> searchByCreatedDate(Date createdDate) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.depositaccount d where d.CreatedDate = ?", createdDate);
        return convert(rst,new ArrayList<DepositAccount>());
    }

    @Override
    public double getBalanceByPk(String depositTypeAccountId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.depositaccount d where d.DepositTypeAccountID =?", depositTypeAccountId);
        return rst.getDouble(1);
    }

    @Override
    public boolean updateBalanceByPk(String depositTypeAccountId, double newBalance) throws SQLException {
        return CrudUtil.execute("update sanasa.depositaccount set depositaccount.Balance = ?  where depositaccount.DepositTypeAccountID = ?", newBalance, depositTypeAccountId);
    }

    @Override
    public String getDepositTypeAccountId(String accountId, String depositType) throws SQLException {
        ResultSet rst = CrudUtil.execute("select d.DepositTypeAccountID from sanasa.depositaccount d where d.AccountID=? AND d.DepositTypeID=?", accountId, depositType);
        return rst.getString(1);
    }
}
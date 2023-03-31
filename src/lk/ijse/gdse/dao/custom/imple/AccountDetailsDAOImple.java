package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.AccountDetailsDao;
import lk.ijse.gdse.entity.AccountDetails;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDetailsDAOImple implements AccountDetailsDao {
    private Connection connection;
    public AccountDetailsDAOImple(Connection connection) {
        this.connection=connection;
    }

    private List<AccountDetails> convert(ResultSet rst, List<AccountDetails> arrayList) throws SQLException {

        while (rst.next()) {
            arrayList.add(convert(rst));
        }
        return arrayList;
    }

    private AccountDetails convert(ResultSet rst) throws SQLException {
        return new AccountDetails(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5),
                rst.getDate(6),
                rst.getString(7),
                rst.getString(8),
                rst.getDate(9),
                rst.getTime(10),
                rst.getString(11)
        );
    }

    @Override
    public boolean add(AccountDetails obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.accountdetails values (?,?,?,?,?,?,?,?,?,?,?)",
                obj.getAccountId(),
                obj.getNic(),
                obj.getName(),
                obj.getAddress(),
                obj.getContact(),
                obj.getDateOfBirth(),
                obj.getEmail(),
                obj.getGender(),
                obj.getRegDate(),
                obj.getRegTime(),
                obj.getState()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from sanasa.accountdetails a where a.AccountID=?", id);
    }

    @Override
    public boolean update(AccountDetails obj) throws SQLException {
        return CrudUtil.execute(
                "update sanasa.accountdetails a set a.NIC=?,a.Name=?,a.Address=?,a.Contact=?,a.DateOfBirth=?,a.Email=?,a.Gender=?,a.RegDate=?,a.regTime=?,a.state=? where a.AccountID=?",
                obj.getNic(),
                obj.getName(),
                obj.getAddress(),
                obj.getContact(),
                obj.getDateOfBirth(),
                obj.getEmail(),
                obj.getGender(),
                obj.getRegDate(),
                obj.getRegTime(),
                obj.getState(),
                obj.getAccountId()
        );
    }

    @Override
    public AccountDetails searchByPk(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.accountdetails a where a.AccountID=?",id);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<AccountDetails> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.accountdetails a");
        return convert(rst, new ArrayList<AccountDetails>());
    }
    @Override
    public boolean isExist(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.accountdetails a where a.AccountID=?", id);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT a.AccountID FROM sanasa.accountdetails a ORDER BY a.AccountID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString(1);
            String[] as = id.split("A");
            int newId = Integer.parseInt(as[1]);
//            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("A%06d", newId);
        } else {
            return "A000001";
        }
    }

    @Override
    public AccountDetails searchByNic(String nic) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.accountdetails a where a.NIC=?", nic);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<AccountDetails> searchByCreatedDate(Date createdDate) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.accountdetails a where a.RegDate = ?", createdDate);
        return convert(rst,new ArrayList<AccountDetails>());
    }

    @Override
    public List<AccountDetails> searchByAddress(String address) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.accountdetails a where a.Address = ?", address);
        return convert(rst,new ArrayList<AccountDetails>());
    }

    @Override
    public List<AccountDetails> searchByState(String state) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.accountdetails a where a.state = ?", state);
        return convert(rst,new ArrayList<AccountDetails>());
    }
}
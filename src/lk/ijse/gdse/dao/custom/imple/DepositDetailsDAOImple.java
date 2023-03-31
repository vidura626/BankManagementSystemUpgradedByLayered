package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.DepositDetailsDAO;
import lk.ijse.gdse.entity.DepositDetails;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepositDetailsDAOImple implements DepositDetailsDAO {
    private Connection connection;
    public DepositDetailsDAOImple(Connection connection) {
        this.connection=connection;
    }

    @Override
    public boolean add(DepositDetails obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.depositdetails values (?,?,?)",
                obj.getDepositTypeId(),
                obj.getDepositTypeId(),
                obj.getInterest()
        );
    }

    @Override
    public boolean delete(String depositTypeId) throws SQLException {
        return CrudUtil.execute("delete from sanasa.depositdetails d where d.DepositTypeID=?", depositTypeId);
    }

    @Override
    public boolean update(DepositDetails obj) throws SQLException {
        return CrudUtil.execute(
                "update sanasa.depositdetails set depositdetails.Description=?,depositdetails.Interest=? where depositdetails.DepositTypeID=?",
                obj.getDescription(),
                obj.getInterest(),
                obj.getDepositTypeId()
        );
    }

    @Override
    public DepositDetails searchByPk(String depositTypeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.depositdetails d where d.DepositTypeID=?", depositTypeId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<DepositDetails> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.depositdetails d");
        return convert(rst, new ArrayList<DepositDetails>());
    }

    @Override
    public boolean isExist(String depositTypeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.depositdetails d where d.DepositTypeID=?", depositTypeId);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }

    private List<DepositDetails> convert(ResultSet rst, List<DepositDetails> arrayList) throws SQLException {
        while (rst.next()){
            arrayList.add(convert(rst));
        }
        return arrayList.size() > 0 ? arrayList : null;
    }

    private DepositDetails convert(ResultSet rst) throws SQLException {
        return new DepositDetails(
                rst.getString(1),
                rst.getString(2),
                rst.getDouble(3)
        );
    }

    @Override
    public double getInterestByPk(String depositTypeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select d.Interest from sanasa.depositdetails d where d.DepositTypeID=?", depositTypeId);
        return rst.next() ? rst.getDouble(1) : -1;
    }

    @Override
    public boolean updateInterestByPk(String depositTypeId, double newInterestAmount) throws SQLException {
        return CrudUtil.execute("update sanasa.depositdetails set Interest = ? where depositdetails.DepositTypeID=?", newInterestAmount,depositTypeId);
    }

    @Override
    public boolean addLoanAmountsByPk(List<Double> amounts) {
        return false;
    }
}

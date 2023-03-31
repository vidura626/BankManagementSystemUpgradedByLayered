package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.LoanDetailsDAO;
import lk.ijse.gdse.entity.LoanDetails;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDetailsDAOImple implements LoanDetailsDAO {
    private Connection connection;
    public LoanDetailsDAOImple(Connection connection) {
        this.connection=connection;
    }

    @Override
    public boolean add(LoanDetails obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.loandetails values (?,?,?,?)",
                obj.getLoanTypeId(),
                obj.getDescription(),
                obj.getInterest(),
                obj.getAmounts()
        );
    }

    @Override
    public boolean delete(String loanTypeId) throws SQLException {
        return CrudUtil.execute("delete from sanasa.loandetails l where l.LoanTypeID=?", loanTypeId);
    }

    @Override
    public boolean update(LoanDetails obj) throws SQLException {
        return CrudUtil.execute("update sanasa.loandetails set loandetails.Description=?,loandetails.Interest=?,loandetails.Amounts=?  where loandetails.LoanTypeID=?",
                obj.getDescription(),
                obj.getInterest(),
                obj.getAmounts(),
                obj.getLoanTypeId()
        );
    }

    @Override
    public LoanDetails searchByPk(String loanTypeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loandetails l where l.LoanTypeID=?", loanTypeId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<LoanDetails> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loandetails l");
        return convert(rst, new ArrayList<LoanDetails>());
    }

    @Override
    public boolean isExist(String loanTypeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loandetails l where l.LoanTypeID=?", loanTypeId);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet resultset = CrudUtil.execute("select s.LoanTypeID from sanasa.loandetails s order by s.LoanTypeID desc limit 1");
        if (resultset.next()) {
            String lastId = resultset.getString(1);
            int lastDigit = Integer.parseInt(lastId.replaceAll("LD", ""))+1;
            return String.format("LD%04d",lastDigit);
        } else {
            return "LD0001";
        }
    }

    private List<LoanDetails> convert(ResultSet rst, List<LoanDetails> arrayList) throws SQLException {
        while (rst.next()){
            arrayList.add(convert(rst));
        }
        return arrayList.size() > 0 ? arrayList : null;
    }

    private LoanDetails convert(ResultSet rst) throws SQLException {
        return new LoanDetails(
                rst.getString(1),
                rst.getString(2),
                rst.getDouble(3),
                rst.getString(4)
        );
    }

    @Override
    public double getInterestByPk(String loanTypeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select l.Interest from sanasa.loandetails l where l.LoanTypeID=?", loanTypeId);
        return rst.next() ? rst.getDouble(1) : -1;
    }

    @Override
    public boolean updateInterestByPk(String loanTypeId, double newInterest) throws SQLException {
        ResultSet rst = CrudUtil.execute("update sanasa.loandetails set loandetails.Interest=? where loandetails.LoanTypeID=?", newInterest, loanTypeId);
        return rst.next();
    }
}

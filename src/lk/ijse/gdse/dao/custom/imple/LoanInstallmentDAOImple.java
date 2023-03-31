package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.LoanInstallmentDAO;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.entity.LoanInstallment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanInstallmentDAOImple implements LoanInstallmentDAO {
    private Connection connection;
    public LoanInstallmentDAOImple(Connection connection) {
        this.connection=connection;
    }

    @Override
    public boolean add(LoanInstallment obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.loaninstallment values (?,?,?)",
                obj.getLoanInstallmentId(),
                obj.getTransactionId(),
                obj.getLoanId()
        );
    }

    @Override
    public boolean delete(String loanInstallmentId) throws SQLException, NoCodeException {
        throw new NoCodeException("cannot delete loan Installments");
    }

    @Override
    public boolean update(LoanInstallment obj) throws SQLException, NoCodeException {
        throw new NoCodeException("cannot update loan Installments");
    }

    @Override
    public LoanInstallment searchByPk(String loanInstallmentId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loaninstallment l where l.LoanInstallmentID=?", loanInstallmentId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<LoanInstallment> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loaninstallment l");
        return convert(rst, new ArrayList<LoanInstallment>());
    }

    @Override
    public boolean isExist(String loanInstallmentId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loaninstallment l where l.LoanInstallmentID=?",loanInstallmentId);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select LoanInstallmentID from sanasa.loaninstallment order by LoanInstallmentID desc limit 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            int lastDigit = Integer.parseInt(lastId.replaceAll("LI", "")) + 1;
            return String.format("LI%08d",lastDigit);
        } else {
            return "LI00000001";
        }
    }

    private List<LoanInstallment> convert(ResultSet rst, List<LoanInstallment> arrayList) throws SQLException {
        while (rst.next()){
            arrayList.add(convert(rst));
        }
        return arrayList;
    }

    private LoanInstallment convert(ResultSet rst) throws SQLException {
        return new LoanInstallment(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3)
        );
    }

    @Override
    public LoanInstallment searchByTransactionId(String transactionId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loaninstallment l where l.TransactionID=?", transactionId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public String getLoanIdByTransactionId(String transactionId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select l.LoanID from sanasa.loaninstallment l where l.TransactionID=?", transactionId);
        return rst.next() ? rst.getString(1) : null;
    }
}

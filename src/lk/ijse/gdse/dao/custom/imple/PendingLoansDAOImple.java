package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.PendingLoansDAO;
import lk.ijse.gdse.entity.PendingLoans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PendingLoansDAOImple implements PendingLoansDAO {
    private Connection connection;
    public PendingLoansDAOImple(Connection connection) {
        this.connection=connection;
    }

    @Override
    public boolean add(PendingLoans obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.pendingloans values (?,?,?,?,?,?)",
                obj.getPendingLoanId(),
                obj.getAmount(),
                obj.getLoanTypeId(),
                obj.getAccountId(),
                obj.getInstallments(),
                obj.getInstallmentAmount()
        );
    }

    @Override
    public boolean delete(String pendingLoanId) throws SQLException {
        return CrudUtil.execute("delete from sanasa.pendingloans p where p.PendingLoanID=?", pendingLoanId);
    }

    @Override
    public boolean update(PendingLoans obj) throws SQLException {
        return CrudUtil.execute(
                "update sanasa.pendingloans set Amount = ?,pendingloans.LoanTypeID=?,pendingloans.AccountID=?,pendingloans.Installments=?,pendingloans.InstallmentsAmount=? where pendingloans.PendingLoanID=?",
                obj.getAmount(),
                obj.getLoanTypeId(),
                obj.getAccountId(),
                obj.getInstallments(),
                obj.getInstallmentAmount(),
                obj.getPendingLoanId()
        );
    }

    @Override
    public PendingLoans searchByPk(String pendingLoanId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.pendingloans p where p.PendingLoanID=?", pendingLoanId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<PendingLoans> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.pendingloans");
        return convert(rst, new ArrayList<PendingLoans>());
    }

    @Override
    public boolean isExist(String pendingLoanId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.pendingloans p where p.PendingLoanID=?", pendingLoanId);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT a.PendingLoanID FROM sanasa.pendingloans a ORDER BY a.PendingLoanID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString(1);
            int newId = Integer.parseInt(id.replace("P", "")) + 1;
            return String.format("P%08d", newId);
        } else {
            return "P00000001";
        }
    }

    private List<PendingLoans> convert(ResultSet rst, List<PendingLoans> arrayList) throws SQLException {
        while (rst.next()){
            arrayList.add(convert(rst));
        }
        return arrayList;
    }

    private PendingLoans convert(ResultSet rst) throws SQLException {
        return new PendingLoans(
                rst.getString(1),
                rst.getDouble(2),
                rst.getString(3),
                rst.getString(4),
                rst.getInt(5),
                rst.getDouble(6)
        );
    }

    @Override
    public List<PendingLoans> searchLoansByAccountId(String accountId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.pendingloans where pendingloans.AccountID=?",accountId);
        return convert(rst, new ArrayList<PendingLoans>());
    }

    @Override
    public List<PendingLoans> searchLoansByLoanTypeId(String loanTypeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.pendingloans where pendingloans.LoanTypeID=?",loanTypeId);
        return convert(rst, new ArrayList<PendingLoans>());
    }

    @Override
    public List<PendingLoans> searchLoansByLoanAmount(double amount) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.pendingloans where pendingloans.Amount=?",amount);
        return convert(rst, new ArrayList<PendingLoans>());
    }
}

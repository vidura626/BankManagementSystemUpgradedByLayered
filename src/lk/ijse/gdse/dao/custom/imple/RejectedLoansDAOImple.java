package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.RejectedLoansDAO;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.entity.RejectedLoans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RejectedLoansDAOImple implements RejectedLoansDAO {
    private Connection connection;
    public RejectedLoansDAOImple(Connection connection) {
        this.connection=connection;
    }

    @Override
    public boolean add(RejectedLoans obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.rejectedloans values (?,?,?,?,?)",
                obj.getRejLoanId(),
                obj.getAmount(),
                obj.getLoanTypeId(),
                obj.getAccountId(),
                obj.getReason()
        );
    }

    @Override
    public boolean delete(String rejLoanId) throws SQLException, NoCodeException {
        throw new NoCodeException("WARNING! Cannot delete Rejected Loan");
    }

    @Override
    public boolean update(RejectedLoans obj) throws SQLException, NoCodeException {
        throw new NoCodeException("WARNING! Cannot update Rejected Loan");
    }

    @Override
    public RejectedLoans searchByPk(String rejLoanId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.rejectedloans r where r.RejLoanID=?", rejLoanId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<RejectedLoans> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.rejectedloans r");
        return convert(rst, new ArrayList<RejectedLoans>());
    }

    @Override
    public boolean isExist(String rejLoanId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.rejectedloans r where r.RejLoanID=?", rejLoanId);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT d.RejLoanID FROM sanasa.rejectedloans d ORDER BY d.RejLoanID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString(1);
            int newId = Integer.parseInt(id.replace("RJ", "")) + 1;
            return String.format("RJ%08d", newId);
        } else {
            return "RJ00000001";
        }
    }

    private List<RejectedLoans> convert(ResultSet rst, List<RejectedLoans> arrayList) throws SQLException {
        while (rst.next()){
            arrayList.add(convert(rst));
        }
        return arrayList.size() > 0 ? arrayList : null;
    }

    private RejectedLoans convert(ResultSet rst) throws SQLException {
        return new RejectedLoans(
                rst.getString(1),
                rst.getDouble(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5)
        );
    }

    @Override
    public List<RejectedLoans> searchLoansByAccountId(String accountId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.rejectedloans r where r.AccountID=?", accountId);
        return convert(rst, new ArrayList<RejectedLoans>());
    }

    @Override
    public List<RejectedLoans> searchLoansByLoanTypeId(String loanTypeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.rejectedloans r where r.LoanTypeID=?", loanTypeId);
        return convert(rst, new ArrayList<RejectedLoans>());
    }

    @Override
    public List<RejectedLoans> searchLoansByLoanAmount(double amount) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.rejectedloans r where r.Amount=?", amount);
        return convert(rst, new ArrayList<RejectedLoans>());
    }

    @Override
    public String searchReasonByPk(String rejLoanId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select r.Reason from sanasa.rejectedloans r where r.RejLoanID=?", rejLoanId);
        return rst.next() ? rst.getString(1) : null;
    }
}

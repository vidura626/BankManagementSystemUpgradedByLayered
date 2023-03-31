package lk.ijse.gdse.dao.custom.imple;

import lk.ijse.gdse.dao.CrudUtil;
import lk.ijse.gdse.dao.custom.LoansDAO;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.entity.Loans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoansDAOImple implements LoansDAO {
    private Connection connection;
    public LoansDAOImple(Connection connection) {
        this.connection=connection;
    }

    @Override
    public boolean add(Loans obj) throws SQLException {
        return CrudUtil.execute(
                "insert into sanasa.loans values (?,?,?,?,?,?,?,?,?,?,?,?)",
                obj.getLoanId(),
                obj.getAmount(),
                obj.getLoanTypeId(),
                obj.getAccountId(),
                obj.getInstallments(),
                obj.getInstallmentAmount(),
                obj.getIssuedDate(),
                obj.getRemainingInstallments(),
                obj.getRemainingLoanAmount(),
                obj.getMonthlyInstallmentDate(),
                obj.getSettledOrNot(),
                obj.getInterestAmount()
        );
    }

    @Override
    public boolean delete(String loanId) throws SQLException, NoCodeException {
        throw new NoCodeException("Cannot delete loan");
    }

    @Override
    public boolean update(Loans obj) throws SQLException {
        return CrudUtil.execute(
                "update sanasa.loans set loans.Amount=?,loans.LoanTypeID=?,loans.AccountID=?,loans.Installments=?,loans.InstallmentsAmount=?,loans.IssuedDate=?,loans.RemainingInstallments=?,loans.RemainingLoanAmount=?,loans.MonthlyInstallmentDate=?,loans.SettledOrNot=?,loans.interestAmount=? where loans.LoanID=?",
                obj.getAmount(),
                obj.getLoanTypeId(),
                obj.getAccountId(),
                obj.getInstallments(),
                obj.getInstallmentAmount(),
                obj.getIssuedDate(),
                obj.getRemainingInstallments(),
                obj.getRemainingLoanAmount(),
                obj.getMonthlyInstallmentDate(),
                obj.getSettledOrNot(),
                obj.getInterestAmount(),
                obj.getLoanId()
        );
    }

    @Override
    public Loans searchByPk(String loanId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loans l where l.LoanID=?", loanId);
        return rst.next() ? convert(rst) : null;
    }

    @Override
    public List<Loans> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loans l");
        return convert(rst, new ArrayList<Loans>());
    }

    @Override
    public boolean isExist(String loanId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loans l where l.LoanID=?", loanId);
        return rst.next();
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT d.LoanID FROM sanasa.loans d ORDER BY d.LoanID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString(1);
            int newId = Integer.parseInt(id.replace("L", "")) + 1;
            return String.format("L%08d", newId);
        } else {
            return "L00000001";
        }
    }

    private List<Loans> convert(ResultSet rst, List<Loans> arrayList) throws SQLException {
        while (rst.next()){
            arrayList.add(convert(rst));
        }
        return arrayList;
    }

    private Loans convert(ResultSet rst) throws SQLException {
        return new Loans(
                rst.getString(1),
                rst.getDouble(2),
                rst.getString(3),
                rst.getString(4),
                rst.getInt(5),
                rst.getDouble(6),
                rst.getDate(7),
                rst.getInt(8),
                rst.getDouble(9),
                rst.getInt(10),
                rst.getString(11),
                rst.getDouble(12)
        );
    }

    @Override
    public List<Loans> searchLoansByAccountId(String accountId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loans l where l.AccountID=?", accountId);
        return convert(rst, new ArrayList<Loans>());
    }

    @Override
    public List<Loans> searchLoansByLoanTypeId(String loanTypeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loans l where l.LoanTypeID=?", loanTypeId);
        return convert(rst, new ArrayList<Loans>());
    }

    @Override
    public List<Loans> searchLoansByLoanIssuedDate(Date issuedDate) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loans l where l.IssuedDate=?", issuedDate);
        return convert(rst, new ArrayList<Loans>());
    }

    @Override
    public List<Loans> searchLoansByState(String state) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sanasa.loans l where l.SettledOrNot=?", state);
        return convert(rst, new ArrayList<Loans>());
    }

    @Override
    public boolean updateRemainingInstallmentCountByPk(String loanId,int newCount) throws SQLException {
        return CrudUtil.execute("update sanasa.loans set loans.RemainingInstallments=? where loans.LoanID=?", newCount, loanId);
    }

    @Override
    public boolean updateRemainingInstallmentAmountByPk(String loanId, double newAmount) throws SQLException {
        return CrudUtil.execute("update sanasa.loans set loans.RemainingLoanAmount=? where loans.LoanID=?", newAmount, loanId);
    }

    @Override
    public boolean setInterestAmountByPk(String loanId, double amount) throws SQLException {
        return CrudUtil.execute("update sanasa.loans set loans.interestAmount=? where loans.LoanID=?", amount, loanId);
    }

    @Override
    public double getInterestAmountByPk(String loanId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select l.interestAmount from sanasa.loans l where l.LoanID=?", loanId);
        return rst.next() ? rst.getDouble(1) : null;
    }

    @Override
    public String getStateByPk(String loanId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select l.SettledOrNot from sanasa.loans l where l.LoanID=?", loanId);
        return rst.next() ? rst.getString(1) : null;
    }

    @Override
    public boolean setStateByPk(String loanId,String state) throws SQLException {
        return CrudUtil.execute("update sanasa.loans set loans.SettledOrNot=? where loans.LoanID=?", state, loanId);
    }
}

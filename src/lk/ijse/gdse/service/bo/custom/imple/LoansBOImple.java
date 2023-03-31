package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.LoansDAO;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.LoansDTO;
import lk.ijse.gdse.entity.Loans;
import lk.ijse.gdse.service.bo.custom.LoansBO;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoansBOImple implements LoansBO {
    private final LoansDAO loansDAO= (LoansDAO) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.LOANS, DbConnection.getDbConnection().getConnection());
    @Override
    public boolean createLoan(LoansDTO loansDTO) throws SQLException {
        return loansDAO.add(Convert.toLoans(loansDTO));
    }

    @Override
    public String getLoanTypeIdById(String loanId) throws SQLException {
        return loansDAO.searchByPk(loanId).getLoanTypeId();
    }

    @Override
    public String generateNextId() throws SQLException {
        return loansDAO.generateNextId();
    }

    @Override
    public String getAccountNumberById(String loanId) throws SQLException {
        return loansDAO.searchByPk(loanId).getAccountId();
    }

    @Override
    public double getRemainingLoanAmount(String loanId) throws SQLException {
        return loansDAO.searchByPk(loanId).getRemainingLoanAmount();
    }

    @Override
    public boolean updateRemainingLoanAmount(String loanId, double newAmount) throws SQLException, NoCodeException {
        Loans loans = loansDAO.searchByPk(loanId);
        loans.setRemainingLoanAmount(newAmount);
        return loansDAO.update(loans);
    }

    @Override
    public int getRemainingInstallment(String loanId) throws SQLException {
        return loansDAO.searchByPk(loanId).getRemainingInstallments();
    }

    @Override
    public boolean updateRemainingInstallment(String loanId, int newAmount) throws SQLException{
        return loansDAO.updateRemainingInstallmentCountByPk(loanId, newAmount);
    }

    @Override
    public double getInterestAmount(String loanId) throws SQLException {
        return loansDAO.searchByPk(loanId).getInterestAmount();
    }

    @Override
    public boolean updateInterestAmount(String loanId, double amount) throws SQLException, NoCodeException {
        Loans loans = loansDAO.searchByPk(loanId);
        loans.setInterestAmount(amount);
        return loansDAO.update(loans);
    }

    @Override
    public String getState(String loanId) throws SQLException {
        return loansDAO.searchByPk(loanId).getSettledOrNot();
    }

    @Override
    public boolean updateState(String loanId, LoanState state) throws SQLException {
        return loansDAO.setStateByPk(loanId, state.toString());
    }

    @Override
    public LoansDTO getLoanById(String loanId) throws SQLException {
        return Convert.fromLoans(loansDAO.searchByPk(loanId));
    }

    @Override
    public List<LoansDTO> getLoansByAccountNumber(String acountNumber) throws SQLException {
        return loansDAO.searchLoansByAccountId(acountNumber).stream().map(Convert::fromLoans).collect(Collectors.toList());
    }

    @Override
    public List<LoansDTO> getLoansByLoanType(String loanTypeId) throws SQLException {
        return loansDAO.searchLoansByLoanTypeId(loanTypeId).stream().map(Convert::fromLoans).collect(Collectors.toList());
    }

    @Override
    public List<LoansDTO> getLoansByAmount(double amount) throws SQLException {
        return loansDAO.getAll().stream().filter(loans -> loans.getAmount() == amount).map(Convert::fromLoans).collect(Collectors.toList());
    }

    @Override
    public List<LoansDTO> getLoansByDate(Date issuedDate) throws SQLException {
        return loansDAO.searchLoansByLoanIssuedDate(issuedDate).stream().map(Convert::fromLoans).collect(Collectors.toList());
    }

    @Override
    public List<LoansDTO> getLoansByFromToDate(Date from, Date to) throws SQLException {
        return loansDAO.getAll().stream().filter(loans -> loans.getIssuedDate().compareTo(from) >= 0 && loans.getIssuedDate().compareTo(to) >= 0).map(Convert::fromLoans).collect(Collectors.toList());
    }

    @Override
    public List<LoansDTO> getLoansByState(LoanState state) throws SQLException {
        return loansDAO.searchLoansByState(state.toString()).stream().map(Convert::fromLoans).collect(Collectors.toList());
    }

    @Override
    public List<LoansDTO> getAllLoans() throws SQLException {
        return loansDAO.getAll().stream().map(Convert::fromLoans).collect(Collectors.toList());
    }
}

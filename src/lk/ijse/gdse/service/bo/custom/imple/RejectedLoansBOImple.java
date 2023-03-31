package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.RejectedLoansDAO;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.RejectedLoansDTO;
import lk.ijse.gdse.service.bo.custom.LoanDetailsBO;
import lk.ijse.gdse.service.bo.custom.RejectedLoansBO;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class RejectedLoansBOImple implements RejectedLoansBO {
    private final RejectedLoansDAO rejectedLoansDAO= (RejectedLoansDAO) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.REJECTED_LOANS, DbConnection.getDbConnection().getConnection());
    @Override
    public String getLoanTypeIdById(String loanId) throws SQLException {
        return rejectedLoansDAO.searchByPk(loanId).getLoanTypeId();
    }

    @Override
    public String generateNextId() throws SQLException {
        return rejectedLoansDAO.generateNextId();
    }

    @Override
    public String getReasonById(String loanId) throws SQLException {
        return rejectedLoansDAO.searchByPk(loanId).getReason();
    }

    @Override
    public String getAccountNumberById(String loanId) throws SQLException {
        return rejectedLoansDAO.searchByPk(loanId).getAccountId();
    }

    @Override
    public boolean createRejectedLoan(RejectedLoansDTO rejectedLoansDTO) throws SQLException {
        return rejectedLoansDAO.add(Convert.toRejectedLoans(rejectedLoansDTO));
    }

    @Override
    public RejectedLoansDTO getRejectedLoanById(String rejectedLoanId) throws SQLException {
        return Convert.fromRejectedLoans(rejectedLoansDAO.searchByPk(rejectedLoanId));
    }

    @Override
    public List<RejectedLoansDTO> getAllRejectedLoans() throws SQLException {
        return rejectedLoansDAO.getAll().stream().map(Convert::fromRejectedLoans).collect(Collectors.toList());
    }

    @Override
    public List<RejectedLoansDTO> getRejectedLoansByAmount(double amount) throws SQLException {
        return getAllRejectedLoans().stream().filter(rejectedLoansDTO -> rejectedLoansDTO.getAmount() == amount).collect(Collectors.toList());
    }

    @Override
    public List<RejectedLoansDTO> getRejectedLoansByLoanType(String loanTypeId) throws SQLException {
        return getAllRejectedLoans().stream().filter(rejectedLoansDTO -> rejectedLoansDTO.getLoanTypeId().equals(loanTypeId)).collect(Collectors.toList());
    }

    @Override
    public List<RejectedLoansDTO> getRejectedLoansByAccountNumber(String accountNumber) throws SQLException {
        return getAllRejectedLoans().stream().filter(rejectedLoansDTO -> rejectedLoansDTO.getAccountId().equals(accountNumber)).collect(Collectors.toList());
    }
}

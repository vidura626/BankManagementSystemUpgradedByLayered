package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.PendingLoansDAO;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.PendingLoansDTO;
import lk.ijse.gdse.service.bo.custom.LoanDetailsBO;
import lk.ijse.gdse.service.bo.custom.PendingLoansBO;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PendingLoansBOImple implements PendingLoansBO {
    private final PendingLoansDAO pendingLoansDAO= (PendingLoansDAO) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.PENDING_LOANS, DbConnection.getDbConnection().getConnection());
    @Override
    public String getLoanTypeIdById(String loanId) throws SQLException {
        return pendingLoansDAO.searchByPk(loanId).getLoanTypeId();
    }

    @Override
    public String generateNextId() throws SQLException {
        return pendingLoansDAO.generateNextId();
    }

    @Override
    public String getAccountNumberById(String loanId) throws SQLException {
        return pendingLoansDAO.searchByPk(loanId).getAccountId();
    }

    @Override
    public boolean createPendingLoan(PendingLoansDTO pendingLoansDTO) throws SQLException {
        return pendingLoansDAO.add(Convert.toPendingLoans(pendingLoansDTO));
    }

    @Override
    public boolean updatePendingLoan(PendingLoansDTO pendingLoansDTO) throws SQLException, NoCodeException {
        return pendingLoansDAO.update(Convert.toPendingLoans(pendingLoansDTO));
    }

    @Override
    public boolean deletePendingLoan(String pendingLoanId) throws SQLException, NoCodeException {
        return pendingLoansDAO.delete(pendingLoanId);
    }

    @Override
    public PendingLoansDTO getPendingLoanById(String pendingLoanId) throws SQLException {
        return Convert.fromPendingLoans(pendingLoansDAO.searchByPk(pendingLoanId));
    }

    @Override
    public List<PendingLoansDTO> getAllPendingLoans() throws SQLException {
        return pendingLoansDAO.getAll().stream().map(Convert::fromPendingLoans).collect(Collectors.toList());
    }

    @Override
    public List<PendingLoansDTO> getPendingLoansByAmount(double amount) throws SQLException {
        return getAllPendingLoans().stream().filter(pendingLoansDTO -> pendingLoansDTO.getAmount() == amount).collect(Collectors.toList());
    }

    @Override
    public List<PendingLoansDTO> getPendingLoansByAccountNumber(String accountNumber) throws SQLException {
        return getAllPendingLoans().stream().filter(pendingLoansDTO -> pendingLoansDTO.getAccountId().equals(accountNumber)).collect(Collectors.toList());
    }

    @Override
    public List<PendingLoansDTO> getPendingLoansByLoanType(String loanTypeId) throws SQLException {
        return getAllPendingLoans().stream().filter(pendingLoansDTO -> pendingLoansDTO.getLoanTypeId().equals(loanTypeId)).collect(Collectors.toList());
    }
}

package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.LoanDetailsDAO;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.LoanDetailsDTO;
import lk.ijse.gdse.service.bo.custom.LoanDetailsBO;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class LoanDetailsBOImple implements LoanDetailsBO {

    private final LoanDetailsDAO loanDetailsDAO= (LoanDetailsDAO) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.LOAN_DETAILS, DbConnection.getDbConnection().getConnection());

    @Override
    public String generateNextId() throws SQLException {
        return loanDetailsDAO.generateNextId();
    }

    @Override
    public boolean createLoanType(LoanDetailsDTO loanDetailsDTO) throws SQLException {
        return loanDetailsDAO.add(Convert.toLoanDetails(loanDetailsDTO));
    }

    @Override
    public boolean updateLoanType(LoanDetailsDTO loanDetailsDTO) throws SQLException, NoCodeException {
        return loanDetailsDAO.update(Convert.toLoanDetails(loanDetailsDTO));
    }

    @Override
    public boolean changeInterest(String loanTypeId, double interest) throws SQLException {
        return loanDetailsDAO.updateInterestByPk(loanTypeId, interest);
    }

    @Override
    public double getInterest(String loanTypeId) throws SQLException {
        return loanDetailsDAO.getInterestByPk(loanTypeId);
    }

    @Override
    public List<LoanDetailsDTO> getAllLoanType() throws SQLException {
        return loanDetailsDAO.getAll().stream().map(Convert::fromLoanDetails).collect(Collectors.toList());
    }

    @Override
    public LoanDetailsDTO getLoanTypeById(String loanTypeId) throws SQLException {
        return Convert.fromLoanDetails(loanDetailsDAO.searchByPk(loanTypeId));
    }

}

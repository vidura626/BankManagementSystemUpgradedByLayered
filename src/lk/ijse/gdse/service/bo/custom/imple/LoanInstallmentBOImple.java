package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.LoanInstallmentDAO;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.LoanInstallmentDTO;
import lk.ijse.gdse.service.bo.custom.LoanInstalmentBO;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanInstallmentBOImple implements LoanInstalmentBO {
    private final LoanInstallmentDAO loanInstallmentDAO= (LoanInstallmentDAO) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.LOAN_INSTALLMENT, DbConnection.getDbConnection().getConnection());
    @Override
    public boolean makeLoanInstallment(LoanInstallmentDTO loanInstallmentDTO) throws SQLException {
        return loanInstallmentDAO.add(Convert.toLoanInstallment(loanInstallmentDTO));
    }

    @Override
    public String getTransactionIdById(String loanInstallmentId) throws SQLException {
        return loanInstallmentDAO.searchByPk(loanInstallmentId).getTransactionId();
    }

    @Override
    public String generateNextId() throws SQLException {
        return loanInstallmentDAO.generateNextId();
    }

    @Override
    public String getLoanIdById(String loanInstallmentId) throws SQLException {
        return loanInstallmentDAO.searchByPk(loanInstallmentId).getLoanId();
    }

    @Override
    public LoanInstallmentDTO getInstallmentById(String loanInstallmentId) throws SQLException {
        return Convert.fromLoanInstallment(loanInstallmentDAO.searchByPk(loanInstallmentId));
    }

    @Override
    public LoanInstallmentDTO getInstallmentByTransactionId(String transactionId) throws SQLException {
        return Convert.fromLoanInstallment(loanInstallmentDAO.searchByTransactionId(transactionId));
    }

    @Override
    public List<LoanInstallmentDTO> getAllLoanInstallments() throws SQLException {
        return loanInstallmentDAO.getAll().stream().map(Convert::fromLoanInstallment).collect(Collectors.toList());
    }

    @Override
    public List<LoanInstallmentDTO> getLoanInstallmentsByLoanId(String loanId) throws SQLException {
        return loanInstallmentDAO.getAll().stream().filter(loanInstallment -> loanInstallment.getLoanId().equals(loanId)).map(Convert::fromLoanInstallment).collect(Collectors.toList());
    }

}

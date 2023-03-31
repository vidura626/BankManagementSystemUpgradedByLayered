package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.dto.LoanDetailsDTO;
import lk.ijse.gdse.service.bo.SuperBO;

import java.sql.SQLException;
import java.util.List;

public interface LoanDetailsBO extends SuperBO {
    public String generateNextId() throws SQLException;
    public boolean createLoanType(LoanDetailsDTO loanDetailsDTO) throws SQLException;
    public boolean updateLoanType(LoanDetailsDTO loanDetailsDTO) throws SQLException, NoCodeException;
    public boolean changeInterest(String loanTypeId,double interest) throws SQLException;
    public double getInterest(String loanTypeId) throws SQLException;
    public List<LoanDetailsDTO> getAllLoanType() throws SQLException;

    LoanDetailsDTO getLoanTypeById(String loanTypeId) throws SQLException;
}

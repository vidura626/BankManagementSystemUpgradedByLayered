package lk.ijse.gdse.service.bo.custom;

import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.dto.DepositDetailsDTO;
import lk.ijse.gdse.service.bo.SuperBO;

import java.sql.SQLException;
import java.util.List;

public interface DepositDetailsBO extends SuperBO {

    public boolean createDepositType(DepositDetailsDTO depositDetailsDTO) throws SQLException;
    public boolean updateDepositType(DepositDetailsDTO depositDetailsDTO) throws SQLException, NoCodeException;
    public boolean changeInterest(String depositTypeId,double interest) throws SQLException;
    public double getInterest(String depositTypeId) throws SQLException;
    public DepositDetailsDTO getDepositTypeById(String depositType) throws SQLException;
    public List<DepositDetailsDTO> getAllDepositTypes() throws SQLException;

}

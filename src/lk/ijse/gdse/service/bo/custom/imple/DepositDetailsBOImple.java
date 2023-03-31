package lk.ijse.gdse.service.bo.custom.imple;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.custom.DepositDetailsDAO;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.DepositDetailsDTO;
import lk.ijse.gdse.service.bo.custom.DepositDetailsBO;
import lk.ijse.gdse.service.bo.util.Convert;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DepositDetailsBOImple implements DepositDetailsBO {

    private final DepositDetailsDAO depositDetailsDAO= (DepositDetailsDAO) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoTypes.DEPOSIT_DETAILS, DbConnection.getDbConnection().getConnection());

    @Override
    public boolean createDepositType(DepositDetailsDTO depositDetailsDTO) throws SQLException {
        return depositDetailsDAO.add(Convert.toDepositDetails(depositDetailsDTO));
    }

    @Override
    public boolean updateDepositType(DepositDetailsDTO depositDetailsDTO) throws SQLException, NoCodeException {
        return depositDetailsDAO.update(Convert.toDepositDetails(depositDetailsDTO));
    }

    @Override
    public boolean changeInterest(String depositTypeId, double interest) throws SQLException {
        return depositDetailsDAO.updateInterestByPk(depositTypeId, interest);
    }

    @Override
    public double getInterest(String depositTypeId) throws SQLException {
        return depositDetailsDAO.getInterestByPk(depositTypeId);
    }

    @Override
    public DepositDetailsDTO getDepositTypeById(String depositTypeId) throws SQLException {
        return Convert.fromDepositDetails(depositDetailsDAO.searchByPk(depositTypeId));
    }


    @Override
    public List<DepositDetailsDTO> getAllDepositTypes() throws SQLException {
        return depositDetailsDAO.getAll().stream().map(Convert::fromDepositDetails).collect(Collectors.toList());
    }
}

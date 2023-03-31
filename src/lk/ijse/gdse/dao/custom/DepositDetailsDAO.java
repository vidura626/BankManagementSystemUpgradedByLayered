package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.DepositDetails;

import java.sql.SQLException;
import java.util.List;

public interface DepositDetailsDAO extends CrudDAO<DepositDetails> {
    public double getInterestByPk(String depositTypeId) throws SQLException;
    public boolean updateInterestByPk(String depositTypeId,double newInterestAmount) throws SQLException;
    public boolean addLoanAmountsByPk(List<Double> amounts);
}

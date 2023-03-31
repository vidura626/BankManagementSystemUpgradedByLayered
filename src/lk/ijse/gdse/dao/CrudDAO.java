package lk.ijse.gdse.dao;

import lk.ijse.gdse.dao.exception.EmptyTableException;
import lk.ijse.gdse.dao.exception.NoCodeException;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    public boolean add(T obj) throws SQLException;
    public boolean delete(String id) throws SQLException, NoCodeException;
    public boolean update(T obj) throws SQLException, NoCodeException;
    public T searchByPk(String id) throws SQLException;
    public List<T> getAll() throws SQLException;
    public boolean isExist(String id) throws SQLException;
    public String generateNextId() throws SQLException;

}

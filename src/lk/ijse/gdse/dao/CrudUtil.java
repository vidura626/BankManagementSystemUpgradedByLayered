package lk.ijse.gdse.dao;

import lk.ijse.gdse.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CrudUtil {
    public static <T>T execute(String sql,Object...args) throws SQLException {
        PreparedStatement pstm = DbConnection.getDbConnection().getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            pstm.setObject(i+1, args[i]);
        }
        if(sql.startsWith("select")||sql.startsWith("SELECT")){
            return (T) pstm.executeQuery();
        }
        return (T)((Boolean)(pstm.executeUpdate()>0));
    }
}

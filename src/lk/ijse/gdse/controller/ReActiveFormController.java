package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Paint;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.AccountDetailsBO;

import java.sql.SQLException;

public class ReActiveFormController {
    public JFXTextField txtNic;

    private final AccountDetailsBO accountDetailsBO= (AccountDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ACCOUNT_DETAILS_BO);

    public void btnReActioveOnAction(ActionEvent event) throws SQLException {
        if(txtNic.getText().isEmpty()){
            txtNic.setFocusColor(Paint.valueOf("RED"));
            txtNic.requestFocus();
            return;
        }else if (accountDetailsBO.getAccountByNic(txtNic.getText())==null){
            txtNic.setFocusColor(Paint.valueOf("RED"));
            txtNic.requestFocus();
            return;
        }
        try {
            DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            boolean isReActive = accountDetailsBO.reActiveAccount(txtNic.getText());
            if (isReActive) {
                ButtonType ok=new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel=new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure ?",ok,cancel);
                if (alert.showAndWait().orElse(cancel) == ok) {
                    DbConnection.getDbConnection().getConnection().commit();
                    new Alert(Alert.AlertType.CONFIRMATION, "Accunt re-Activation is succeed !").show();
                } else {
                    DbConnection.getDbConnection().getConnection().rollback();
                }
            } else {
                DbConnection.getDbConnection().getConnection().rollback();
            }
        } catch (SQLException | NoCodeException e) {
            e.printStackTrace();
        }finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
    }
}

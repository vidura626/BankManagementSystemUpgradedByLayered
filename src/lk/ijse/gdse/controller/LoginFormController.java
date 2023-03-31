package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.controller.util.Navigation;
import lk.ijse.gdse.controller.util.Routes;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {

    public JFXTextField txtUser;
    public JFXPasswordField txtPass;
    @FXML
    private AnchorPane pane;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String username="User";
        String password="1234";
        if(!username.equals(txtUser.getText())||!password.equals(txtPass.getText())){
            new Alert(Alert.AlertType.WARNING,"Username or password not matched !").show();
            return;
        }
        Stage stage=new Stage();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/lk/ijse/gdse/view/MainDashboardForm.fxml"));
        AnchorPane container = (AnchorPane) pane.lookup("#pane");
        container.getChildren().clear();
        Navigation.setPane(container);
        stage.setScene(new Scene(pane));
        this.pane.getScene().getWindow().hide();
        stage.setMinWidth(1500.00);
        stage.setMinHeight(900.00);
        stage.setWidth(1500.00);
        stage.setHeight(900.00);
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
        stage.show();
    }

}

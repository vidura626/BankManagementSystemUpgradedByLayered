package lk.ijse.gdse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.dto.DepositDetailsDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.DepositDetailsBO;

import java.sql.SQLException;

public class ViewDepositTypesFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<DepositDetailsDTO> tblViewDepositDetails;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colInterest;

    private final DepositDetailsBO depositDetailsBO= (DepositDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.DEPOSIT_DETAILS_BO);

    public void initialize(){
        try {
            setCellValueFactory();
            tblViewDepositDetails.getItems().addAll(depositDetailsBO.getAllDepositTypes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("depositTypeId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colInterest.setCellValueFactory(new PropertyValueFactory<>("interest"));
    }
}

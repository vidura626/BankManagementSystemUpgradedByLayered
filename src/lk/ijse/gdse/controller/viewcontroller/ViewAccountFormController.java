package lk.ijse.gdse.controller.viewcontroller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.dto.AccountDetailsDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.AccountDetailsBO;

import java.sql.SQLException;
import java.util.List;

public class ViewAccountFormController {

    @FXML
    private TableView<AccountDetailsDTO> tblAccounts;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colRegDate;

    @FXML
    private TableColumn<?, ?> colRegTime;

    private final AccountDetailsBO accountDetailsBO= (AccountDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.ACCOUNT_DETAILS_BO);

    public void initialize(){
        loanAccountDetails();
    }

    private void loanAccountDetails() {
        setCellValueFactory();
        tblAccounts.getItems().clear();
        try {
            List<AccountDetailsDTO> allAccounts = accountDetailsBO.getAllAccounts();
            tblAccounts.getItems().addAll(FXCollections.observableArrayList(allAccounts));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        colRegTime.setCellValueFactory(new PropertyValueFactory<>("regTime"));
    }
}

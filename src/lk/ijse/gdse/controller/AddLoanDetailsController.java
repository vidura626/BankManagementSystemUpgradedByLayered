package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse.controller.tm.LoanTypeDetailsTM;
import lk.ijse.gdse.controller.util.FormManage;
import lk.ijse.gdse.controller.util.RegexTypes;
import lk.ijse.gdse.dao.exception.NoCodeException;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.dto.LoanDetailsDTO;
import lk.ijse.gdse.service.bo.BoFactory;
import lk.ijse.gdse.service.bo.custom.LoanDetailsBO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddLoanDetailsController {
    public TableView<LoanTypeDetailsTM> tblLoanTypes;
    public TableColumn<?,?> colDescription;
    public TableColumn<?,?> colLoanTypeID;
    public TableColumn<?,?> colInterest;
    public TableColumn<?,?> colAmounts;
    public TableColumn<?,?> colRemove;
    public JFXButton btnDelete;
    @FXML
    private JFXTabPane pane;

    @FXML
    private Tab tabLoanTypeDetails;

    @FXML
    private JFXTextField txtLoanTypeID;

    @FXML
    private Label lblNotifyNic;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private Label lblDescription;

    @FXML
    private JFXTextField txtInterest;

    @FXML
    private Label lblInterest;

    @FXML
    private Label lblNotifyAddLoanTypes;

    @FXML
    private JFXTextField txtAmounts;

    @FXML
    private Label lblAmounts;

    @FXML
    private Label lblNotifyCreateAccount;

    @FXML
    private AnchorPane paneSearchUpdate;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private Label lblNotifySearchUpdate;

    @FXML
    private Tab tabCloseAccount;

    @FXML
    private Tab tabViewAccount;

    @FXML
    private JFXButton btnOk;

    @FXML
    private JFXButton btnSaveChanges;

    private ArrayList<JFXTextField> textFields = new ArrayList<>();

    private final LoanDetailsBO loanDetailsBO= (LoanDetailsBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOAN_DETAILS_BO);

    public void initialize(){
        Collections.addAll(textFields,txtDescription,txtInterest,txtAmounts,txtSearch);
        setLoanTypeID();
        btnOk.setVisible(false);
        btnUpdate.setDisable(true);
        txtAmounts.setDisable(true);
        txtDescription.setDisable(true);
        txtInterest.setDisable(true);
    }
    private void loadLoanTypeDetailsTable() {
        try {
            List<LoanDetailsDTO> allLoanType = loanDetailsBO.getAllLoanType();
            ObservableList<LoanDetailsDTO> list = FXCollections.observableArrayList();
            list.addAll(allLoanType);
            ObservableList<LoanTypeDetailsTM> loanTypeDetails = FXCollections.observableArrayList();
            for (LoanDetailsDTO loanDetailsDTO : list) {
                JFXButton remove = new JFXButton("X");
                remove.setStyle("-fx-text-fill: red");
                buttonSetOnAction(remove, tblLoanTypes);
                loanTypeDetails.add(
                        new LoanTypeDetailsTM(
                                loanDetailsDTO.getLoanTypeId(),
                                loanDetailsDTO.getDescription(),
                                loanDetailsDTO.getInterest(),
                                loanDetailsDTO.getAmounts(),
                                remove
                        )
                );
            }
            setCellValueFactory();
            tblLoanTypes.setItems(loanTypeDetails);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void buttonSetOnAction(JFXButton remove, TableView<LoanTypeDetailsTM> tblLoanTypes) {
        try {
            remove.setOnAction(e -> {
                ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                if(!tblLoanTypes.getSelectionModel().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(no) == ok) {
                        tblLoanTypes.getItems().removeAll(tblLoanTypes.getSelectionModel().getSelectedItem());
                        refreshTable();
                    }
                }
            });
        }catch (NullPointerException e){
            System.out.println(e);
        }
    }

    private void refreshTable() {
        try {
            ObservableList<LoanTypeDetailsTM> items = tblLoanTypes.getItems();
            int lastDigit = Integer.parseInt(txtLoanTypeID.getText().split("LD")[1])-1;
            String loanTypeId = loanDetailsBO.generateNextId();
            txtLoanTypeID.setText(loanTypeId);
            for (int i = items.size()-1; i >= 0; i--) {
                lastDigit--;
                items.get(i).setLoanTypeID(String.format("LD%04d",lastDigit));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colLoanTypeID.setCellValueFactory(new PropertyValueFactory<>("loanTypeID"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colInterest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        colAmounts.setCellValueFactory(new PropertyValueFactory<>("amounts"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("button"));
    }

    private void setLoanTypeID() {
        try {
            txtLoanTypeID.setText(loanDetailsBO.generateNextId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tblLoanTypesOnMouseCklicked(){
        if(tblLoanTypes.getSelectionModel().isEmpty()||tblLoanTypes.getItems().isEmpty())return;
        LoanTypeDetailsTM selectedItem = tblLoanTypes.getSelectionModel().getSelectedItem();
        txtLoanTypeID.setText(selectedItem.getLoanTypeID());
        txtDescription.setText(selectedItem.getDescription());
        txtInterest.setText(String.valueOf(selectedItem.getInterest()));
        txtAmounts.setText(selectedItem.getAmounts());
        txtSearch.setText(selectedItem.getLoanTypeID());
        txtSearchOnKeyReleased();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            if (0 < loanDetailsBO.getInterest(txtLoanTypeID.getText())) {
                new Alert(Alert.AlertType.WARNING,"Aleready added !").show();
                return;
            }
            setCellValueFactory();
            boolean validate = FormManage.validate(textFields, RegexTypes.DESCRIPTION, RegexTypes.AMOUNT, RegexTypes.DESCRIPTION);
            if (!validate || !btnOk.visibleProperty().getValue()) {
                txtAmounts.setDisable(false);
                txtDescription.setDisable(false);
                txtInterest.setDisable(false);
                tblLoanTypes.getItems().clear();
                btnOk.setVisible(true);
                return;
            }
            colRemove.setVisible(true);
            String loanTypeID = txtLoanTypeID.getText();
            String description = txtDescription.getText();
            double interest = Double.parseDouble(txtInterest.getText());
            String amounts = txtAmounts.getText();
            JFXButton remove = new JFXButton("X");

            buttonSetOnAction(remove,tblLoanTypes);

            LoanTypeDetailsTM LoanTypeDetailsTM = new LoanTypeDetailsTM(loanTypeID, description, interest, amounts, remove);

            tblLoanTypes.getItems().add(LoanTypeDetailsTM);
            tblLoanTypes.refresh();

            txtLoanTypeID.setText((String.format("LD%02d",Integer.parseInt(txtLoanTypeID.getText().split("LD")[1])+1)));

        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.WARNING,"Check the form again !").show();
        }catch (NullPointerException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        for (JFXTextField field : textFields) field.clear();
        tblLoanTypes.getItems().clear();
        initialize();
    }

    @FXML
    void btnOkOnAction(ActionEvent event) throws SQLException {
        try {
            DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            ObservableList<LoanTypeDetailsTM> items = tblLoanTypes.getItems();
            for (int i = 0; i < items.size(); i++) {
                boolean add = loanDetailsBO.createLoanType(new LoanDetailsDTO(
                        items.get(i).getLoanTypeID(),
                        items.get(i).getDescription(),
                        items.get(i).getInterest(),
                        items.get(i).getAmounts())
                );
                if(!add){
                    DbConnection.getDbConnection().getConnection().rollback();
                    new Alert(Alert.AlertType.ERROR,"Process failed !").show();
                    return;
                }

                if (i == items.size() - 1) {
                    ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(no) == ok) {
                        DbConnection.getDbConnection().getConnection().commit();
                        new Alert(Alert.AlertType.CONFIRMATION, "Ok !").show();
                    } else {
                        DbConnection.getDbConnection().getConnection().rollback();
                    }
                    btnClearOnAction(event);
                    initialize();
                } else {
                    DbConnection.getDbConnection().getConnection().rollback();
                }
            }
        } catch (SQLException | NullPointerException e) {

        }finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        try {
            boolean validate = FormManage.validate(textFields, RegexTypes.DESCRIPTION, RegexTypes.AMOUNT);
            if(!validate){
                return;
            }

            DbConnection.getDbConnection().getConnection().setAutoCommit(false);
            boolean isUpdated = loanDetailsBO.updateLoanType(new LoanDetailsDTO(txtLoanTypeID.getText(), txtDescription.getText(), Double.parseDouble(txtInterest.getText()), txtAmounts.getText()));
            if (isUpdated) {
                ButtonType ok = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ? ", ok, cancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.orElse(cancel) == ok) {
                    DbConnection.getDbConnection().getConnection().commit();
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated !").show();
                } else {
                    DbConnection.getDbConnection().getConnection().rollback();
                }
                btnClearOnAction(event);
                initialize();
            } else {
                DbConnection.getDbConnection().getConnection().rollback();
            }

        } catch (SQLException | NoCodeException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
    }

    @FXML
    void btnViewLoanTypesOnAction(ActionEvent event) {
        colRemove.setVisible(false);
        loadLoanTypeDetailsTable();
        btnOk.setVisible(false);
        txtAmounts.setDisable(true);
        txtDescription.setDisable(true);
        txtInterest.setDisable(true);
    }

    @FXML
    void txtAmountsOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtDescriptionOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtInterestOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtSearchOnKeyReleased() {
        try {
            boolean validate = Pattern.compile("^(LD)[0-9]{4,4}$").matcher(txtSearch.getText()).matches();
            if(!validate){
                txtLoanTypeID.setText(null);
                txtDescription.setText(null);
                txtInterest.setText(null);
                txtAmounts.setText(null);
                btnUpdate.setDisable(true);
                txtDescription.setDisable(true);
                txtAmounts.setDisable(true);
                txtInterest.setDisable(true);
                txtSearch.setFocusColor(Paint.valueOf("RED"));
                txtSearch.requestFocus();
                initialize();
                return;
            }
            String loanTypeId = txtSearch.getText();
            LoanDetailsDTO loanDetails = loanDetailsBO.getAllLoanType().stream().filter(loanDetailsDTO -> loanDetailsDTO.getLoanTypeId().equals(loanTypeId)).collect(Collectors.toCollection(ArrayList::new)).get(0);
            if(loanDetails==null) return;
            txtDescription.setDisable(false);
            txtInterest.setDisable(false);
            txtAmounts.setDisable(false);
            txtLoanTypeID.setText(loanDetails.getLoanTypeId());
            txtDescription.setText(loanDetails.getDescription());
            txtInterest.setText(String.format("%.2f",loanDetails.getInterest()));
            txtAmounts.setText(loanDetails.getAmounts());
            btnUpdate.setDisable(false);

        } catch(SQLException | IndexOutOfBoundsException e){
        }
    }

}


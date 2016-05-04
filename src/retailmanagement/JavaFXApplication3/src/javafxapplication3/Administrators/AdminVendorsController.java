/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3.Administrators;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafxapplication3.Runner;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class for HomeAdmin
 *
 * @author Ted
 * @author Sam
 */
public class AdminVendorsController implements Initializable {


    @FXML
    private TableView tblVendors;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnAddVendor;


    @FXML
    private VBox boxAdd;

    @FXML
    private TextField txtAddPhone;

    @FXML
    private TextField txtAddName;

    @FXML
    private TextField txtAddEmail;

    @FXML
    private Button btnAddAdd;

    @FXML
    private Button btnAddCancel;

    //private static ObservableList<ObservableList> data;

    private String indexSSN = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTableView();
        putSelectedRowInForm();
    }

    private void putSelectedRowInForm() {
        tblVendors.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    String selectedEmployee = newValue.toString();
                    String[] splitData = selectedEmployee.substring(1, selectedEmployee.length() - 1).split(",");
                    //someone selected something

                    //first enable the textboxes
                    txtName.setDisable(true);
                    txtPhone.setDisable(false);
                    txtEmail.setDisable(false);

                    //populate the textboxes with all values
                    txtName.setText(splitData[0].trim());
                    txtPhone.setText(splitData[1].trim());
                    txtEmail.setText(splitData[2].trim());

                    System.out.println("EMAIL SHOULD BE " + splitData[2].trim());

                } catch (NullPointerException e) {
                    System.out.print("Unselected items! Non critical Exception");
                }
            }
        });
    }

    private void populateTableView() {
        tblVendors.getColumns().clear();
        try {
            ResultSet rs = Runner.sC.runQuery("SELECT * FROM rsussa1db.Vendors;");
            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblVendors.getColumns().addAll(col);
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tblVendors.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveBtn(ActionEvent event) throws IOException, SQLException {
        System.out.println("About To Save record");
        // perform input validation to detect attacks

        String query = "UPDATE Vendors SET Phone=?,Email=? WHERE Name=?";

        PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query);
        pstmt.setString(1, txtPhone.getText());
        pstmt.setString(2, txtEmail.getText());
        pstmt.setString(3, txtName.getText());

        System.out.println(pstmt.toString());


        try {
            int rs = pstmt.executeUpdate();
            if (rs == 0) { //if failed
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.setScene(new Scene(VBoxBuilder.create().
                        children(new Text("Could not update. Please try again.")).
                        alignment(Pos.CENTER).padding(new Insets(5)).build()));
                dialogStage.show();

            } else {
                //succeeded
                //update Tableview
                populateTableView();

                //empty the forms and disable them if no row auto selected
                txtPhone.setDisable(true);
                txtEmail.setDisable(true);


            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleNewBtn(ActionEvent event) throws IOException, SQLException {
        boxAdd.setVisible(true);
        btnAddCancel.setVisible(true);
        btnAddAdd.setVisible(true);

        btnAddVendor.setVisible(false);

        txtName.setDisable(true);
        txtPhone.setDisable(true);
        txtEmail.setDisable(true);
    }

    @FXML
    private void handleAddNewBtn(ActionEvent event) throws IOException, SQLException {
        System.out.println("About To Create New Record");
        //My String
        String query = "INSERT INTO Vendors (Name, Phone, Email) VALUES (?, ?, ?)";

        //Prevent SQL Injection
        PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query);
        pstmt.setString(1, txtAddName.getText());
        pstmt.setString(2, txtAddPhone.getText());
        pstmt.setString(3, txtAddEmail.getText());

        System.out.println(pstmt.toString());
        int rs = 0;
        try {
            rs = pstmt.executeUpdate();
            if (rs == 0) { //if failed
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.setScene(new Scene(VBoxBuilder.create().
                        children(new Text("Could not create. Please try again.")).
                        alignment(Pos.CENTER).padding(new Insets(5)).build()));
                dialogStage.show();

            } else {
                //succeeded
                //update Tableview
                populateTableView();

                boxAdd.setVisible(false);
                btnAddCancel.setVisible(false);
                btnAddAdd.setVisible(false);

                btnAddVendor.setVisible(true);

                //set add textfields to empty
                txtAddName.setText("");
                txtAddPhone.setText("");
                txtAddEmail.setText("");
            }
        } catch (SQLException e) {
            //Primary Key Exists
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(VBoxBuilder.create().
                    children(new Text("Vendor Already exists. Please choose a new vendor name or continue to adding products from this vendor.")).
                    alignment(Pos.CENTER).padding(new Insets(5)).build()));
            dialogStage.show();
        }
    }

    @FXML
    private void handleCancelBtn(ActionEvent event) throws IOException, SQLException {
        boxAdd.setVisible(false);
        btnAddAdd.setVisible(false);
        btnAddCancel.setVisible(false);

        btnAddVendor.setVisible(true);

        txtName.setDisable(false);
        txtPhone.setDisable(false);
        txtEmail.setDisable(false);
    }
}



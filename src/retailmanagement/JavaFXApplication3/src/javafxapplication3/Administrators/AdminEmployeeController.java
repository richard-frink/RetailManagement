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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class for HomeAdmin
 *
 * @author Ted
 * @author Sam
 */
public class AdminEmployeeController implements Initializable {


    @FXML
    private TableView tblEmployees;
    @FXML
    private TextField btnSSN;

    @FXML
    private TextField btnName;

    @FXML
    private CheckBox chkManager;

    @FXML
    private TextField btnHourlyRate;

    @FXML
    private Button btnAddEmployee;



    @FXML
    private VBox boxAdd;

    @FXML
    private Label lblAddSSN;

    @FXML
    private Label lblAddName;

    @FXML
    private Label lblAddHourlyRate;

    @FXML
    private Label lblAddManager;

    @FXML
    private CheckBox chkAddManager;

    @FXML
    private TextField txtAddSSN;

    @FXML
    private TextField txtAddName;

    @FXML
    private TextField txtAddManager;

    @FXML
    private TextField txtAddHourlyRate;

    @FXML
    private Button btnAddAdd;

    @FXML
    private Button btnAddCancel;

    @FXML
    private Label lblAddUsername;

    @FXML
    private Label lblAddPassword;

    @FXML
    private TextField txtAddUsername;

    @FXML
    private PasswordField txtAddPassword;


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
        tblEmployees.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    String selectedEmployee = newValue.toString();
                    String[] splitData = selectedEmployee.substring(1, selectedEmployee.length() - 1).split(",");
                    //someone selected something

                    //first enable the textboxes
                    btnName.setDisable(false);
                    chkManager.setDisable(false);
                    btnHourlyRate.setDisable(false);

                    //populate the textboxes with all values
                    btnSSN.setText(splitData[0].trim());
                    btnName.setText(splitData[1].trim());
                    if (splitData[2].trim().equals("1")) {
//                    System.out.print("This is true " + splitData[2]);
                        chkManager.setSelected(true);
                    } else {
                        chkManager.setSelected(false);
//                    System.out.print("This is false"+ splitData[2]);
                    }
                    btnHourlyRate.setText(splitData[3].trim());

                } catch (NullPointerException e) {
                    System.out.print("Unselected items! Non critical Exception");
                }
            }
        });
    }

    private void populateTableView() {
        tblEmployees.getColumns().clear();
        try {
            ResultSet rs = Runner.sC.runQuery("SELECT SSN,Name,ManagerFlag,HourlyRate,Username FROM rsussa1db.Employees;");
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

                tblEmployees.getColumns().addAll(col);
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
            tblEmployees.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveBtn(ActionEvent event) throws IOException, SQLException {
        System.out.println("About To Save record");
        // perform input validation to detect attacks

        String query = "UPDATE Employees SET Name=?,ManagerFlag=?,HourlyRate=?,Username=? WHERE SSN=?";

        PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query);
        pstmt.setString(1, btnName.getText());
        if (chkManager.isSelected()) {
            pstmt.setString(2, "1");
        } else {
            pstmt.setString(2, "0");
        }
        pstmt.setString(3, btnHourlyRate.getText());
        pstmt.setString(4, txtAddUsername.getText());
        pstmt.setString(5, btnSSN.getText());

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
                btnSSN.setDisable(true);
                btnName.setDisable(true);
                chkManager.setDisable(true);
                btnHourlyRate.setDisable(true);


            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleDeleteBtn(ActionEvent event) throws IOException, SQLException {
        System.out.println("About To Save record");
        // perform input validation to detect attacks

        String query1 = "DELETE FROM HoursWorked WHERE SSN=?";
        String query2 = "DELETE FROM Employees WHERE SSN=?";

        PreparedStatement pstmt1 = Runner.sC.getConnection().prepareStatement(query1);
        PreparedStatement pstmt2 = Runner.sC.getConnection().prepareStatement(query2);
        pstmt1.setString(1, btnSSN.getText());
        pstmt2.setString(1, btnSSN.getText());
        System.out.println(pstmt1.toString());
        System.out.println(pstmt2.toString());
        try {
            int rs1 = pstmt1.executeUpdate();
            int rs2 = pstmt2.executeUpdate();
            if (rs1 != 1 && rs2 != 1) { //if failed
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.setScene(new Scene(VBoxBuilder.create().
                        children(new Text("Could not delete. Please try again.")).
                        alignment(Pos.CENTER).padding(new Insets(5)).build()));
                dialogStage.show();

            } else {
                //succeeded
                //update Tableview
                populateTableView();

                //empty the forms and disable them if no row auto selected
                btnSSN.setDisable(true);
                btnName.setDisable(true);
                chkManager.setDisable(true);
                btnHourlyRate.setDisable(true);


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

        btnAddEmployee.setVisible(false);

        lblAddUsername.setVisible(true);
        lblAddPassword.setVisible(true);
        txtAddUsername.setVisible(true);
        txtAddPassword.setVisible(true);
    }

    @FXML
    private void handleAddNewBtn(ActionEvent event) throws IOException, SQLException {
        System.out.println("About To Create New Record");
        //My String
        String query = "INSERT INTO Employees (SSN, Name, ManagerFlag, HourlyRate, Username, Password) VALUES (?, ?, ?, ?, ?, ?)";

        //Prevent SQL Injection
        PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query);
        pstmt.setString(1, txtAddSSN.getText());
        pstmt.setString(2, txtAddName.getText());
        if (chkAddManager.isSelected()) {
            pstmt.setString(3, "1");
        } else {
            pstmt.setString(3, "0");
        }
        pstmt.setString(4, txtAddHourlyRate.getText());
        pstmt.setString(5, txtAddUsername.getText());
        pstmt.setString(6, txtAddUsername.getText());


        System.out.println(pstmt.toString());


        try {
            int rs = pstmt.executeUpdate();
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
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        boxAdd.setVisible(false);
        btnAddCancel.setVisible(false);
        btnAddAdd.setVisible(false);

        btnAddEmployee.setVisible(true);

        lblAddUsername.setVisible(false);
        lblAddPassword.setVisible(false);
        txtAddUsername.setVisible(false);
        txtAddPassword.setVisible(false);

        //set add textfields to empty
        txtAddName.setText("");
        txtAddUsername.setText("");
        txtAddPassword.setText("");
        txtAddSSN.setText("");
        txtAddHourlyRate.setText("");
        chkAddManager.setSelected(false);
    }
    @FXML
    private void handleCancelBtn(ActionEvent event) throws IOException, SQLException{
        boxAdd.setVisible(false);
        btnAddAdd.setVisible(false);
        btnAddCancel.setVisible(false);

        btnAddEmployee.setVisible(true);

        lblAddUsername.setVisible(false);
        lblAddPassword.setVisible(false);
        txtAddUsername.setVisible(false);
        txtAddPassword.setVisible(false);
    }
}



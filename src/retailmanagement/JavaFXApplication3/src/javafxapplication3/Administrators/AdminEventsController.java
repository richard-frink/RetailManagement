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
import java.time.format.DateTimeFormatter;
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
public class AdminEventsController implements Initializable {


    @FXML
    private TableView tblEmployees;

    @FXML
    private DatePicker txtAddIncDate;
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
                    btnSSN.setDisable(true);

                    //populate the textboxes with all values
                    btnSSN.setText(splitData[0].trim());
                    btnName.setText(splitData[1].trim());

                } catch (NullPointerException e) {
                    System.out.print("Unselected items! Non critical Exception");
                }
            }
        });
    }

    private void populateTableView() {
        tblEmployees.getColumns().clear();
        try {
            ResultSet rs = Runner.sC.runQuery("SELECT * FROM rsussa1db.CorporateEvents;");
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

        String query = "UPDATE CorporateEvents SET NameOf=? WHERE Date=?";

        PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query);
        pstmt.setString(1, btnName.getText());
        pstmt.setString(2, btnSSN.getText());

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

        String query2 = "DELETE FROM CorporateEvents WHERE Date=?";

        PreparedStatement pstmt2 = Runner.sC.getConnection().prepareStatement(query2);
        pstmt2.setString(1, btnSSN.getText());
        System.out.println(pstmt2.toString());
        try {
            int rs2 = pstmt2.executeUpdate();
            if (rs2 != 1) { //if failed
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
    }

    @FXML
    private void handleAddNewBtn(ActionEvent event) throws IOException, SQLException {
        System.out.println("About To Create New Record");
        //My String
        String query = "INSERT INTO CorporateEvents (Date, NameOf) VALUES (?, ?)";

        //Prevent SQL Injection
        PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query);
        pstmt.setString(1, txtAddIncDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        pstmt.setString(2, txtAddName.getText());

        //execute financial reports really quick
        String temp_query = "INSERT INTO FinancialReports VALUES (?, '5000', '2500')";
        PreparedStatement pstmt_temp = Runner.sC.getConnection().prepareStatement(temp_query);
        pstmt_temp.setString(1, txtAddIncDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        try{
            int t_rs=pstmt_temp.executeUpdate();
            if(t_rs == 0){
                System.out.println("Couldn't create financial Report");
            } else {
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
            }
        } catch (SQLException e){
            System.out.println(e);
        }
        System.out.println(pstmt.toString());


//        try {
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }

        boxAdd.setVisible(false);
        btnAddCancel.setVisible(false);
        btnAddAdd.setVisible(false);

        btnAddEmployee.setVisible(true);

        //set add textfields to empty
        txtAddName.setText("");
    }
    @FXML
    private void handleCancelBtn(ActionEvent event) throws IOException, SQLException{
        boxAdd.setVisible(false);
        btnAddAdd.setVisible(false);
        btnAddCancel.setVisible(false);

        btnAddEmployee.setVisible(true);
    }
}



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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafxapplication3.AdminLoginController;
import javafxapplication3.LoginPageController;
import javafxapplication3.Runner;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * FXML Controller class for HomeAdmin
 *
 * @author Ted
 * @author Sam
 */
public class AdminPropertyController implements Initializable {

    @FXML
    private TextField txtAddIncType;

    @FXML
    private TextArea txtAddIncDesc;

    @FXML
    private DatePicker txtAddIncDate;

    @FXML
    private VBox boxAddNewIncident;

    @FXML
    private Button btnAddOrder;

    @FXML
    private ComboBox drpOrder;

    @FXML
    private TextField txtAddQuantity;

    @FXML
    private TextField txtAddName;
    //----------------

    @FXML
    private TableView tblOrders;


    @FXML
    private VBox boxAddInvoice;

    @FXML
    private Button btnAddInvoiceToOrder;

    @FXML
    private Button btnAddInvoiceSubmit;

    @FXML
    private Button btnAddInvoiceCancel;

    @FXML
    private Label lblOrderID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> orderData = FXCollections.observableArrayList();
        try {
            ResultSet order_rs = Runner.sC.runQuery("SELECT IssueNumber from rsussa1db.PropertyIssues");
            while (order_rs.next()) {
                orderData.add(order_rs.getString("IssueNumber"));
            }
            drpOrder.setItems(orderData);
            btnAddInvoiceToOrder.setVisible(false);

            //onchange of drop down, update table
            populateTableOnDropDownUpdate();
            //populateTable....
            //putSelectedRowInForm();

        } catch (SQLException e) {
            System.out.println("Couldn't load Property Damages!");
        }
    }

    private void populateTableOnDropDownUpdate() {
        drpOrder.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
//                    String selectedEmployee = newValue.toString();
//                    String[] splitData = selectedEmployee.substring(1, selectedEmployee.length() - 1).split(",");
                    populateTableView();
                    btnAddInvoiceToOrder.setVisible(true);

                } catch (NullPointerException e) {
                    System.out.print("Unselected items! Non critical Exception");
                }
            }
        });
    }

    private void populateTableView() {
        tblOrders.getColumns().clear();
        try {
            ResultSet rs = Runner.sC.runQuery("" +
                    "SELECT PropertyIssues.IssueNumber, PropertyIssues.TypeOf, PropertyIssues.DateOf, PersonsInvolved.Name, PersonsInvolved.Phone \n" +
                    "FROM rsussa1db.PropertyIssues, rsussa1db.PersonsInvolved\n" +
                    "WHERE PropertyIssues.IssueNumber=PersonsInvolved.IssueNumber AND PropertyIssues.IssueNumber=" + drpOrder.getValue());

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

                tblOrders.getColumns().addAll(col);
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
            tblOrders.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddInvoiceToOrder(ActionEvent event) throws IOException, SQLException {
        boxAddInvoice.setVisible(true);
        btnAddInvoiceToOrder.setVisible(false);

//        ObservableList<String> orderData2 = FXCollections.observableArrayList();
//        //populate SKU entries from Table
//        try {
//            ResultSet order_rs = Runner.sC.runQuery("SELECT Name from rsussa1db.PersonsInvolved");
//            while (order_rs.next()) {
//                orderData2.add(order_rs.getString("Name"));
//            }
//            txtAddName.setItems(orderData2);
//
//            //onchange of drop down, update table
//            //populateTableOnDropDownUpdate();
//            //populateTable....
//            //putSelectedRowInForm();
//
//        } catch (SQLException e) {
//            System.out.println("Couldn't load Orders!");
//        }


        lblOrderID.setText(drpOrder.getValue().toString());
        drpOrder.setDisable(true);
    }

    @FXML
    private void handleAddInvoiceCancel(ActionEvent event) throws IOException, SQLException {
        boxAddInvoice.setVisible(false);
        btnAddInvoiceToOrder.setVisible(true);

        drpOrder.setDisable(false);
    }


    @FXML
    private void handleAddInvoiceCancelNew(ActionEvent event) throws IOException, SQLException {
        boxAddNewIncident.setVisible(false);
        btnAddOrder.setVisible(true);
    }

    @FXML
    private void handleAddInvoiceSubmit(ActionEvent event) throws IOException, SQLException {
        System.out.println("About To Create New Record in PropertyIssues");

        try {

            String query2 = "INSERT INTO PersonsInvolved (IssueNumber, Name, Phone) VALUES (?, ?, ?)";

            PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query2);

            pstmt.setString(1, lblOrderID.getText());
            pstmt.setString(2, txtAddName.getText());
            pstmt.setString(3, txtAddQuantity.getText());

            System.out.print(pstmt);

            int addInvoice = pstmt.executeUpdate();
            System.out.println(addInvoice);

            populateTableView();

            boxAddInvoice.setVisible(false);
            btnAddInvoiceToOrder.setVisible(true);

            drpOrder.setDisable(false);

            txtAddQuantity.setText("");
            txtAddName.setText("");
            //onchange of drop down, update table
            //populateTableOnDropDownUpdate();
            //populateTable....
            //putSelectedRowInForm();

        } catch (SQLException e) {
            System.out.println(e);
        }
//        //My String
//        String query = "INSERT INTO Vendors (Name, Phone, Email) VALUES (?, ?, ?)";
//
//        //Prevent SQL Injection
//        PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query);
//        pstmt.setString(1, txtAddName.getText());
//        pstmt.setString(2, txtAddPhone.getText());
//        pstmt.setString(3, txtAddEmail.getText());
//
//        System.out.println(pstmt.toString());
//        int rs = 0;
//        try {
//            rs = pstmt.executeUpdate();
//            if (rs == 0) { //if failed
//                Stage dialogStage = new Stage();
//                dialogStage.initModality(Modality.WINDOW_MODAL);
//                dialogStage.setScene(new Scene(VBoxBuilder.create().
//                        children(new Text("Could not create. Please try again.")).
//                        alignment(Pos.CENTER).padding(new Insets(5)).build()));
//                dialogStage.show();
//
//            } else {
//                //succeeded
//                //update Tableview
//                populateTableView();
//
//                boxAdd.setVisible(false);
//                btnAddCancel.setVisible(false);
//                btnAddAdd.setVisible(false);
//
//                btnAddVendor.setVisible(true);
//
//                //set add textfields to empty
//                txtAddName.setText("");
//                txtAddPhone.setText("");
//                txtAddEmail.setText("");
//            }
//        } catch (SQLException e) {
//            //Primary Key Exists
//            Stage dialogStage = new Stage();
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.setScene(new Scene(VBoxBuilder.create().
//                    children(new Text("Vendor Already exists. Please choose a new vendor name or continue to adding products from this vendor.")).
//                    alignment(Pos.CENTER).padding(new Insets(5)).build()));
//            dialogStage.show();
//        }
    }

    @FXML
    private void handleAddNewBtn(ActionEvent event) throws IOException, SQLException {
        boxAddNewIncident.setVisible(true);
        btnAddOrder.setVisible(false);
    }

    @FXML
    private void handleAddNewIncident(ActionEvent event) throws IOException, SQLException {
        String query2 = "INSERT INTO PropertyIssues (TypeOf, DateOf, Description) VALUES (?, ?, ?)";

        PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query2);

        pstmt.setString(1, txtAddIncType.getText());
        pstmt.setString(2, txtAddIncDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        pstmt.setString(3, txtAddIncDesc.getText());

        System.out.print(pstmt);
        //temp
        //execute financial reports really quick
        String temp_query = "INSERT INTO FinancialReports VALUES (?, '5000', '2500')";
        PreparedStatement pstmt_temp = Runner.sC.getConnection().prepareStatement(temp_query);
        pstmt_temp.setString(1, txtAddIncDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        try {
            int t_rs = pstmt_temp.executeUpdate();
            if (t_rs == 0) {
                System.out.println("Couldn't create financial Report");
            } else {
                int addInvoice = pstmt.executeUpdate();
                System.out.println(addInvoice);

                populateTableView();

                boxAddInvoice.setVisible(false);
                btnAddInvoiceToOrder.setVisible(true);

                drpOrder.setDisable(false);

                txtAddQuantity.setText("");
                txtAddName.setText("");
            }
            // end temp
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}



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
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
public class AdminOrdersController implements Initializable {


    @FXML
    private ComboBox drpOrder;

    @FXML
    private TextField txtAddQuantity;

    @FXML
    private ComboBox drpSKU;
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
            ResultSet order_rs = Runner.sC.runQuery("SELECT OrderID from rsussa1db.Orders");
            while (order_rs.next()) {
                orderData.add(order_rs.getString("OrderID"));
            }
            drpOrder.setItems(orderData);

            //onchange of drop down, update table
            populateTableOnDropDownUpdate();
            //populateTable....
            //putSelectedRowInForm();

        } catch (SQLException e) {
            System.out.println("Couldn't load Orders!");
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


    private void putSelectedRowInForm() {
        tblOrders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {


                    String selectedEmployee = newValue.toString();
                    String[] splitData = selectedEmployee.substring(1, selectedEmployee.length() - 1).split(",");

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
                    "SELECT OrderId, OrderInvoice.SKU, Products.Name, Products.VendorName,OrderInvoice.Qty \n" +
                    "FROM rsussa1db.OrderInvoice, rsussa1db.Products\n" +
                    "WHERE OrderInvoice.SKU=Products.SKU AND OrderId=" + drpOrder.getValue());

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

        ObservableList<String> orderData2 = FXCollections.observableArrayList();
        //populate SKU entries from Table
        try {
            ResultSet order_rs = Runner.sC.runQuery("SELECT Name from rsussa1db.Products");
            while (order_rs.next()) {
                orderData2.add(order_rs.getString("Name"));
            }
            drpSKU.setItems(orderData2);

            //onchange of drop down, update table
            //populateTableOnDropDownUpdate();
            //populateTable....
            //putSelectedRowInForm();

        } catch (SQLException e) {
            System.out.println("Couldn't load Orders!");
        }


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
    private void handleAddInvoiceSubmit(ActionEvent event) throws IOException, SQLException {
        System.out.println("About To Create New Record in OrderInvoice");

        try {
            String query = "SELECT SKU,Name FROM rsussa1db.Products WHERE Name = '" + drpSKU.getValue()+"'";
            System.out.println(query);
            ResultSet SKUGetter = Runner.sC.runQuery(query);
            SKUGetter.next();
            System.out.println("SKUGetter got " + SKUGetter.getString("SKU"));
            String query2 = "INSERT INTO OrderInvoice (OrderID, SKU, Qty) VALUES (?, ?, ?)";

            PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query2);
            pstmt.setString(1, lblOrderID.getText());
            pstmt.setString(2, SKUGetter.getString("SKU"));
            pstmt.setString(3, txtAddQuantity.getText());

            System.out.print(pstmt);

            int addInvoice = pstmt.executeUpdate();
            System.out.println(addInvoice);

            populateTableView();

            boxAddInvoice.setVisible(false);
            btnAddInvoiceToOrder.setVisible(true);

            drpOrder.setDisable(false);

            txtAddQuantity.setText("");
            drpSKU.getSelectionModel().clearSelection();
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

    }
}



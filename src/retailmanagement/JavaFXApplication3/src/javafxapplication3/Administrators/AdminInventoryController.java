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
public class AdminInventoryController implements Initializable {


    @FXML
    private TableView tblInventory;


    @FXML
    private TextField txtSKU;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCurrentStock;

    @FXML
    private TextField txtRetailPrice;

    @FXML
    private TextField txtVendorPrice;

    @FXML
    private TextField txtVendorName;


    @FXML
    private VBox boxAdd1;

    @FXML
    private VBox boxAdd2;


    @FXML
    private Label lblAddSKU;

    @FXML
    private Label lblAddName;

    @FXML
    private Label lblAddCurrentStock;

    @FXML
    private Label lblAddRetailPrice;

    @FXML
    private Label lblAddVendorPrice;

    @FXML
    private Label lblAddVendorName;


    @FXML
    private TextField txtAddSKU;

    @FXML
    private TextField txtAddName;

    @FXML
    private TextField txtAddCurrentStock;

    @FXML
    private TextField txtAddRetailPrice;

    @FXML
    private TextField txtAddVendorPrice;

    @FXML
    private TextField txtAddVendorName;

    @FXML
    private Button btnAddAdd;

    @FXML
    private Button btnAddCancel;

    @FXML
    private Button btnAddProduct;

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
        tblInventory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    String selectedEmployee = newValue.toString();
                    String[] splitData = selectedEmployee.substring(1, selectedEmployee.length() - 1).split(",");
                    //someone selected something

                    //first enable the textboxes
                    txtName.setDisable(false);
                    txtCurrentStock.setDisable(false);
                    txtRetailPrice.setDisable(false);
                    txtVendorPrice.setDisable(false);

                    //populate the textboxes with all values
                    txtSKU.setText(splitData[0].trim());
                    txtName.setText(splitData[1].trim());
                    txtCurrentStock.setText(splitData[2].trim());
                    txtRetailPrice.setText(splitData[3].trim());
                    txtVendorPrice.setText(splitData[4].trim());
                    txtVendorName.setText(splitData[5].trim());

                } catch (NullPointerException e) {
                    System.out.print("Unselected items! Non critical Exception");
                }
            }
        });
    }

    private void populateTableView() {
        tblInventory.getColumns().clear();
        try {
            ResultSet rs = Runner.sC.runQuery("SELECT SKU,Name,CurrentStock,RetailPrice,VendorPrice,VendorName FROM rsussa1db.Products WHERE CurrentStock>-1;");
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

                tblInventory.getColumns().addAll(col);
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
                //System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tblInventory.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveBtn(ActionEvent event) throws IOException, SQLException {
        System.out.println("About To Save record");
        // perform input validation to detect attacks

        String query = "UPDATE Products SET Name=?,CurrentStock=?,RetailPrice=?,VendorPrice=?,VendorName=? WHERE SKU=?";

        PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query);

        pstmt.setString(1, txtName.getText());
        pstmt.setString(2, txtCurrentStock.getText());
        pstmt.setString(3, txtRetailPrice.getText());
        pstmt.setString(4, txtVendorPrice.getText());
        pstmt.setString(5, txtVendorName.getText());
        pstmt.setString(6, txtSKU.getText());

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
                txtName.setDisable(true);
                txtCurrentStock.setDisable(true);
                txtRetailPrice.setDisable(true);
                txtVendorPrice.setDisable(true);


            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleDeleteBtn(ActionEvent event) throws IOException, SQLException {
        System.out.println("About To Set current stock of record to -1");
        // perform input validation to detect attacks

        String query1 = "UPDATE Products SET CurrentStock=-1 WHERE SKU=?";

        PreparedStatement pstmt1 = Runner.sC.getConnection().prepareStatement(query1);
        pstmt1.setString(1, txtSKU.getText());
        System.out.println(pstmt1.toString());
        try {
            int rs1 = pstmt1.executeUpdate();
            if (rs1 != 1) { //if failed
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
                txtName.setDisable(true);
                txtCurrentStock.setDisable(true);
                txtRetailPrice.setDisable(true);
                txtVendorPrice.setDisable(true);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleNewBtn(ActionEvent event) throws IOException, SQLException {
        boxAdd1.setVisible(true);
        boxAdd2.setVisible(true);
        btnAddProduct.setVisible(false);
    }

    @FXML
    private void handleAddNewBtn(ActionEvent event) throws IOException, SQLException {
        System.out.println("About To Create New Record");

        //First check with Vendors
        String vQuery = "SELECT Name FROM rsussa1db.Vendors WHERE Name=\"" + txtAddVendorName.getText() + "\"";

        ResultSet rs = Runner.sC.runQuery(vQuery);
        if (!rs.next()) {
            //create vendor
            System.out.println("\nNeed to create a vendor!");
            String vendorQuery = "INSERT INTO Vendors (Name, Phone, Email) VALUES (?, ?, ?)";

            PreparedStatement pstmt1 = Runner.sC.getConnection().prepareStatement(vendorQuery);
            pstmt1.setString(1, txtAddVendorName.getText());
            pstmt1.setString(2, "000-000-0000");
            pstmt1.setString(3, "no@gmail.com");

            int firstResult = pstmt1.executeUpdate();
            if (firstResult == 0) {
                System.out.print("Failed to add vendor");
            }
        }

        System.out.print("Vendor (Now) Exists!");
        String query = "INSERT INTO Products (SKU, Name, CurrentStock, RetailPrice, VendorPrice, VendorName, Category) VALUES (?, ?, ?, ?, ?, ?, ?)";

        //Prevent SQL Injection
        PreparedStatement pstmt = Runner.sC.getConnection().prepareStatement(query);
        pstmt.setString(1, txtAddSKU.getText());
        pstmt.setString(2, txtAddName.getText());
        pstmt.setString(3, txtAddCurrentStock.getText());
        pstmt.setString(4, txtAddRetailPrice.getText());
        pstmt.setString(5, txtAddVendorPrice.getText());
        pstmt.setString(6, txtAddVendorName.getText());
        pstmt.setString(7, "Grocery");

        System.out.println(pstmt.toString());

        int result = pstmt.executeUpdate();
        if (result == 0) { //if failed
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
            System.out.print("Added Product!");
        }

        boxAdd1.setVisible(false);
        boxAdd2.setVisible(false);
        btnAddProduct.setVisible(true);

        //set textfields to empty
        txtAddName.setText("");
        txtAddSKU.setText("");
        txtAddCurrentStock.setText("");
        txtAddRetailPrice.setText("");
        txtAddVendorName.setText("");
        txtAddVendorPrice.setText("");
    }

    @FXML
    private void handleCancelBtn(ActionEvent event) throws IOException, SQLException {
        boxAdd1.setVisible(false);
        boxAdd2.setVisible(false);
        btnAddProduct.setVisible(true);
    }
}



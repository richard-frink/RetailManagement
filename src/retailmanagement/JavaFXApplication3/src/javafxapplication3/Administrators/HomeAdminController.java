/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3.Administrators;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafxapplication3.JavaFXApplication3;
import javafxapplication3.SqlConnect;

/**
 * FXML Controller class for HomeAdmin
 *
 * @author Ted
 * @author sam
 */
public class HomeAdminController implements Initializable {

    @FXML
    private Button btnHome;
    @FXML
    private Button btnEmployees;
    @FXML
    private Button btnInventory;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnEvents;
    @FXML
    private Button btnReports;
    @FXML
    private Button btnVendors;
    @FXML
    private Button btnProperty;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        btnHome.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){
                System.out.println("I heard someone click the Home btn in Admin!");
                 try {
                     AnchorPane pane = (AnchorPane) FXMLLoader.load(HomeAdminController.class.getResource("adminHello.fxml"));
                 } catch (Exception e){
                     System.out.print(e);
                 }
            }
        });
    }


}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3.Administrators;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class for HomeAdmin
 *
 * @author Ted
 * @author sam
 */
public class AdminHomeController implements Initializable {

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

    @FXML
    private AnchorPane contentPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        homeButtonAction();
        employeesButtonAction();
        inventoryButtonAction();
        ordersButtonAction();
        eventsButtonAction();
        reportsButtonAction();
        vendorsButtonAction();
        propertyButtonAction();

    }

    public void homeButtonAction() {
        btnHome.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the Home btn in Admin!");
                try {
                    contentPane.getChildren().setAll((Node) FXMLLoader.load(AdminHomeController.class.getResource("adminHello.fxml")));
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });
    }

    public void employeesButtonAction() {
        btnEmployees.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the Employee btn in Admin!");
                try {
                    contentPane.getChildren().setAll((Node) FXMLLoader.load(AdminHomeController.class.getResource("adminEmployees.fxml")));
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });
    }

    public void inventoryButtonAction() {
        btnInventory.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the Inventory btn in Admin!");
                try {
                    contentPane.getChildren().setAll((Node) FXMLLoader.load(AdminHomeController.class.getResource("adminInventory.fxml")));
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });
    }
    public void ordersButtonAction() {
        btnOrders.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the orders btn in Admin!");
                try {
                    contentPane.getChildren().setAll((Node) FXMLLoader.load(AdminHomeController.class.getResource("adminOrders.fxml")));
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });

    }
    public void reportsButtonAction() {
        btnReports.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the reports btn in Admin!");
                try {
                    contentPane.getChildren().setAll((Node) FXMLLoader.load(AdminHomeController.class.getResource("adminReports.fxml")));
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });
    }
    public void eventsButtonAction() {
        btnEvents.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the events btn in Admin!");
                try {
                    contentPane.getChildren().setAll((Node) FXMLLoader.load(AdminHomeController.class.getResource("adminEvents.fxml")));
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });
    }
    public void vendorsButtonAction() {
        btnVendors.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the vendors btn in Admin!");
                try {
                    contentPane.getChildren().setAll((Node) FXMLLoader.load(AdminHomeController.class.getResource("adminVendors.fxml")));
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });
    }
    public void propertyButtonAction() {
        btnProperty.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the property btn in Admin!");
                try {
                    contentPane.getChildren().setAll((Node) FXMLLoader.load(AdminHomeController.class.getResource("adminProperty.fxml")));
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });
    }

    }


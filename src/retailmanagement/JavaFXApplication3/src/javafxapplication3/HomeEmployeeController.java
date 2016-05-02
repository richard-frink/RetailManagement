/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafxapplication3.Administrators.AdminHomeController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Ted
 */
public class HomeEmployeeController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML
    public Button btnLogout1;
    @FXML
    public Button btnLogout2;
    @FXML
    public Button btnLogout3;

    @FXML
    public Label empl_Home_name;
    @FXML
    public Label empl_Home_title;
    @FXML
    public Label empl_Home_hourly_pay;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("AT home controller");
        btnLogout1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the Logout btn!");
                try {
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("Runner.fxml"));

                    Scene scene = new Scene(root, 800, 600);

                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });

        btnLogout2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the Logout btn!");
                try {
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("Runner.fxml"));

                    Scene scene = new Scene(root, 800, 600);

                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });

        btnLogout3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the Logout btn!");
                try {
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("Runner.fxml"));

                    Scene scene = new Scene(root, 800, 600);

                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });
    }
}


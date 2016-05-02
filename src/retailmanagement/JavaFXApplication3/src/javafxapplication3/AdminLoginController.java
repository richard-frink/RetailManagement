/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ted
 */
public class AdminLoginController implements Initializable {

    @FXML
    public TextField adminUser;
    @FXML
    public TextField adminPass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToCust(ActionEvent event) throws IOException {

        System.out.println("Going to admin");
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        System.out.println("It made it here");
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void handleloginAdmin(ActionEvent event) throws IOException {

        System.out.println("Login Submitted");
        if (adminUser.getText().equals("testadmin") && adminPass.getText().equals("password")) {

            Parent root = FXMLLoader.load(getClass().getResource("Administrators/HomeAdmin.fxml"));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);

            primaryStage.setScene(scene);
            primaryStage.show();

        } else {
            adminPass.setText("Try Again!");
            adminUser.setText("Try Again!");
        }
    }

}

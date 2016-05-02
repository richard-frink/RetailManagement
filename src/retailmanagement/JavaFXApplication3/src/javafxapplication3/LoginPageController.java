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
public class LoginPageController implements Initializable {

    public static String custUserName;
    @FXML
    public TextField custUser;
    @FXML
    public TextField custPass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToAdmin(ActionEvent event) throws IOException {

        System.out.println("Going to admin");
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        System.out.println("It made it here");
        Parent root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void handleloginCust(ActionEvent event) throws IOException {


        System.out.println("Login Submitted");
        if (custUser.getText().equals("testcust") && custPass.getText().equals("password")) {

            Parent root = FXMLLoader.load(getClass().getResource("HomeCust.fxml"));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            custUserName = custUser.getText();
            primaryStage.setScene(scene);
            primaryStage.show();

        } else {
            custPass.setText("Try Again!");
            custUser.setText("Try Again!");
        }
    }

}

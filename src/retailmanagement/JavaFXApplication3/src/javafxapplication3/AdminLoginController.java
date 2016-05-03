/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
    @FXML
    public Label notice;


    ResultSet credentials;

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
        System.out.println("Mae it to login");
        String query = "SELECT * FROM rsussa1db.Employees WHERE username=\"" + adminUser.getText() + "\" AND password=\"" + adminPass.getText() + "\";";
        try {
            System.out.println(query);
            credentials = Runner.sC.runQuery(query);
            System.out.println("Login Submitted");
            if (!credentials.isBeforeFirst()) {
                System.out.println("No data");
                adminUser.setText("");
                adminPass.setText("");
                notice.setText("Incorrect credentials. Please try again!");
            } else {
                //fun stuff
                notice.setText("");
                Parent root = FXMLLoader.load(getClass().getResource("Administrators/adminMasterLayout.fxml"));
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 600);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

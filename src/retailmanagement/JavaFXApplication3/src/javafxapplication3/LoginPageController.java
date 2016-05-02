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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static javafxapplication3.Runner.sC;

/**
 * FXML Controller class
 *
 * @author Ted
 */
public class LoginPageController implements Initializable {

    @FXML
    public TextField emplUser;
    @FXML
    public TextField emplPass;
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
    private void handleloginEmpl(ActionEvent event) throws IOException {
        System.out.println("Mae it to login");
        String query = "SELECT * FROM rsussa1db.Employees WHERE username=\"" + emplUser.getText() + "\" AND password=\"" + emplPass.getText() + "\";";
        try {
            System.out.println(query);
            credentials = Runner.sC.runQuery(query);
            if (!credentials.isBeforeFirst()) {
                System.out.println("No data");
                emplUser.setText("");
                emplPass.setText("");
                notice.setText("Incorrect credentials. Please try again!");
            } else {
                //fun stuff
                System.out.println("Login Submitted");
                notice.setText("");

                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("homeEmployee.fxml"));

                Scene scene = new Scene(root, 800, 600);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

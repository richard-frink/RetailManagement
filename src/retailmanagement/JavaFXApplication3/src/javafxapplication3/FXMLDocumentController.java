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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Ted
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    private HBox hbo;

    @FXML
    private void handleGoToLogin(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        // label.setText("Hello World!");

        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        System.out.println("It made it here");
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }


}

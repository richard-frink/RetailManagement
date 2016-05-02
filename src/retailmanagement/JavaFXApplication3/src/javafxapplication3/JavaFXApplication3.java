/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Ted
 */
public class JavaFXApplication3 extends Application {
    public static SqlConnect sC;

    @Override
    public void start(Stage stage) throws Exception {
        sC = new SqlConnect();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Dash-In Main Control");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the cozmmand line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

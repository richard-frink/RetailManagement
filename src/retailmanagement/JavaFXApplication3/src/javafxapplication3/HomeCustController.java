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
import javafx.stage.Stage;
import static javafxapplication3.LoginPageController.custUserName;

/**
 * FXML Controller class
 *
 * @author Ted
 */
public class HomeCustController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    public Label custName;
    @FXML
    public Label custTitle;
    
    @FXML
    public Label custPayRate;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void custHome(ActionEvent event) throws IOException { 
         Parent root = FXMLLoader.load(getClass().getResource("HomeCust.fxml"));
                         Stage primaryStage = (Stage)  ((Node)event.getSource()).getScene().getWindow();
                         Scene scene = new Scene(root, 500,500);
                         primaryStage.setScene(scene);
                        primaryStage.show();
    }
    @FXML
    private void custTimeSheet(ActionEvent event) throws IOException { 
        System.out.println("Timesheet pressed");
         Parent root = FXMLLoader.load(getClass().getResource("TimesheetCust.fxml"));
         System.out.println("Timesheet2 pressed");
                         Stage primaryStage = (Stage)  ((Node)event.getSource()).getScene().getWindow();
                        System.out.println("Timesheet3 pressed"); 
                         Scene scene = new Scene(root, 500,500);
                         System.out.println("Timesheet4 pressed");
                         primaryStage.setScene(scene);
                          System.out.println("Before timesheet show");
                        primaryStage.show();
    }
    @FXML
    private void custPump(ActionEvent event) throws IOException { 
         Parent root = FXMLLoader.load(getClass().getResource("PumpCust.fxml"));
                         Stage primaryStage = (Stage)  ((Node)event.getSource()).getScene().getWindow();
                         Scene scene = new Scene(root, 500,500);
                         primaryStage.setScene(scene);
                        primaryStage.show();
    }
}

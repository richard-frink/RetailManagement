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
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

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

    @FXML
    private AnchorPane contentPane;
    @FXML
    private Button btnHomeCust;

    @FXML
    private TabPane custTabPane;

    @FXML
    private Tab custTabHome;

    @FXML
    private Tab custTabTimesheet;
    ResultSet rs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("AT home controller");
        empl_Home_name.setText(LoginPageController.currentUser);
        try {
            String query = "SELECT * FROM rsussa1db.Employees WHERE username=\"" + LoginPageController.currentUser + "\";";
            System.out.print(query);
            rs = Runner.sC.runQuery(query);
            while (rs.next()) {
                int ssn = rs.getInt(1);
                String dob = rs.getString(2);
                String name = rs.getString(3);
                int manager = rs.getInt(4);
                float pay = rs.getFloat(5);
                System.out.println(" " + ssn + " " + dob + " " + name + " " + manager + " " + pay);

                empl_Home_name.setText(name);

                if (manager == 0) {
                    empl_Home_title.setText("No");
                } else {
                    empl_Home_title.setText("Yes");
                }

                empl_Home_hourly_pay.setText("$" + pay);
                // ... do something with these variables ...
            }
            //System.out.println(" kjghvc "+ credentials.getString(2));
            //System.out.println("Result is "+credentials.getString(0));
            System.out.println(query);
       /* if(credentials != null){
            //credentials.first();
            //System.out.println("Result is "+credentials.getString(0));
                //empl_Home_title.setText(credentials.getString("Name"));
            }*/
        } catch (Exception e) {
            System.out.println("Query had an issue. ");
        }

//        custTabPane.getSelectionModel().selectedItemProperty().addListener(
//                new ChangeListener<Tab>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
//                        if (custTabHome.equals(t1)) System.out.println("Home tab clicked");
//                        if (custTabTimesheet.equals(t1)) System.out.println("Timesheet tab clicked");
//                        System.out.println("Tab Selection changed" + custTabPane.getTabs());
//                        System.out.println(" " + ov);
//                    }
//                }
//        );
        btnLogout1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the Logout btn 1!");
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
                    
                    /*
                     try {
                    contentPane.getChildren().setAll((Node) FXMLLoader.load(AdminHomeController.class.getResource("adminHello.fxml")));
                } catch (IOException e) {
                    System.out.print(e);
                }
                     */
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });
    }
    
    /*public void homeButtonAction() {
        btnHomeCust.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the Home btn in Cust!");
                try {
                    contentPane.getChildren().setAll((Node) FXMLLoader.load(AdminHomeController.class.getResource("custHello.fxml")));
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        });
    }*/
}


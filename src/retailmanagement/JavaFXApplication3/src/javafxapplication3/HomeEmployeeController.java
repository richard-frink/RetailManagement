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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

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
    public DatePicker datePick;
    @FXML
    public Button btnLogout1;
    @FXML
    public Button btnLogout2;
    @FXML
    public Button btnLogout3;

    @FXML
    public Button btnSubmitTime;
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

    @FXML
    private TextField hoursSpot;

    @FXML
    private TableView timeSheetTableView;
    @FXML
    private TableView pumpTableView;

    ResultSet rs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("AT home controller");
        empl_Home_name.setText(LoginPageController.currentUser);
        try {
            String query = "SELECT * FROM rsussa1db.Employees WHERE username=\"" + LoginPageController.currentUser + "\";";

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
            populatePumpTableView();
            populateTimeSheetTableView();
            //System.out.println(" kjghvc "+ credentials.getString(2));
            //System.out.println("Result is "+credentials.getString(0));
            System.out.println(query);

            //hoursSpot.setText("");
            //pumpNo.setCellValueFactory("hey");
            // TableColumn col = new TableColumn("hey");

            // pumpTableView.getColumns().addAll(col);
            /* if(credentials != null){
            //credentials.first();
            //System.out.println("Result is "+credentials.getString(0));
                //empl_Home_title.setText(credentials.getString("Name"));
            }*/
        } catch (Exception e) {
            System.out.println("Query had an issue. ");
        }
        /*
        custTabPane.getSelectionModel().selectedItemProperty().addListener(
    new ChangeListener<Tab>() {
        @Override
        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
            if(custTabHome.equals(t1)) System.out.println("Home tab clicked");
            if(custTabTimesheet.equals(t1)) System.out.println("Timesheet tab clicked");
            
            
            System.out.println("Tab Selection changed"+custTabPane.getTabs());
            System.out.println(" "+ov);
        }
    }
);*/

        btnLogout1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the Logout btn 1!");
                try {
                    LoginPageController.currentUser = null;
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
                     LoginPageController.currentUser = null;
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
                     LoginPageController.currentUser = null;
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

        btnSubmitTime.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("I heard someone click the TimeSheet submit btn!");
                boolean flag = true;
                String hours = hoursSpot.getText();
                int hourInt = 0;
                try {
                    hourInt = Integer.parseInt(hours);
                } catch (Exception e) {
                    System.out.println("Not an integer");
                    flag = false;
                }
                LocalDate localDate = datePick.getValue();
                if (localDate != null && flag) {
                    System.out.println(" Hours " + hourInt + " Date " + localDate);
                    
                    try {
                        
                        String  query = "Insert INTO rsussa1db.HoursWorked (SSN, Date, Hours) VALUES ("+getSSN()+", '"+localDate+"', "+hourInt+");";
                         System.out.println("Ran the query: "+query);
                         PreparedStatement pstmt1 = Runner.sC.getConnection().prepareStatement(query);
                         int rs1 = pstmt1.executeUpdate();
                         populateTimeSheetTableView();
                        //rs = Runner.sC.runQuery(query);
                          System.out.println("It was successful");
                          
                    } catch (Exception e) {
                        System.out.println("Query had an issue. ");
                    }
                } else {
                    System.out.println("Some type of error");
                }
                /*Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("Runner.fxml"));

                    Scene scene = new Scene(root, 800, 600);

                    primaryStage.setScene(scene);
                    primaryStage.show();*/

 /*
                     try {
                    contentPane.getChildren().setAll((Node) FXMLLoader.load(AdminHomeController.class.getResource("adminHello.fxml")));
                } catch (IOException e) {
                    System.out.print(e);
                }
                 */
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
    private void populatePumpTableView() {
        pumpTableView.getColumns().clear();
        try {
            ResultSet rs = Runner.sC.runQuery("SELECT PumpNumber,QtyAvailable,IsActive,Date, SoldThisDay FROM rsussa1db.GasPumps;");
            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                pumpTableView.getColumns().addAll(col);
            }

            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            pumpTableView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateTimeSheetTableView() {
        timeSheetTableView.getColumns().clear();
        try {
            int ssn = getSSN();
            if (ssn != 0) {
                ResultSet rs = Runner.sC.runQuery("SELECT Hours, Date FROM rsussa1db.HoursWorked where SSN = '" + ssn + "';");
                ObservableList<ObservableList> data = FXCollections.observableArrayList();
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });

                    timeSheetTableView.getColumns().addAll(col);
                }

                /**
                 * ******************************
                 * Data added to ObservableList *
             *******************************
                 */
                while (rs.next()) {
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    System.out.println("Row [1] added " + row);
                    data.add(row);

                }

                //FINALLY ADDED TO TableView
                timeSheetTableView.setItems(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getSSN() {

        try {
            String query = "SELECT SSN FROM rsussa1db.Employees where Username = \"" + LoginPageController.currentUser + "\";";

            rs = Runner.sC.runQuery(query);
            while (rs.next()) {
                int ssn = rs.getInt(1);

                System.out.println("SSN obtained is " + ssn);
                return ssn;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }
}

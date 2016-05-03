/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

/**
 * @author Ted
 */

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlConnect {

    static final String userName = "rsussa1";//put your MySQL user name tsmith60
    static final String password = "Cosc*q76p";//put your MySQL password  Mine is Cosc*2jk6

    public static Connection getConnection() {
        return connection;
    }

    private static Connection connection=null;


    /**
     * This method is a constructor to create the connection executed once at Startup and constantly reused
     *
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public SqlConnect() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String holder = "";
        Object newInstance;
        newInstance = Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/rsussa1db", userName, password);
    }

    /**
     * This method is a skeleton method for executing queries using a string
     * @param query The query to be run
     * @return a ResultSet object capable of finding values using key
     * @throws SQLException
     */
    public ResultSet runQuery(String query) throws SQLException {
        return connection.createStatement().executeQuery(query);
    }
}

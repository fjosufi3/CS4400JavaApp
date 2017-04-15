package app.java.controller;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;


/*

user: cs4400_5
pw: homg_XXx

user: cs4400_Group_5
pw: VUTRiK7i
 */
public class ConnectionConfiguration {
    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            connection = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_5",
                    "cs4400_5", "homg_XXx");

            if (connection != null) {
                System.out.println("Connection successful");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return connection;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.app.java.controller;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Josufi
 */
public class LoginController implements Initializable {
    
    @FXML
    private Button login_bt;
    @FXML
    private Button login_reg_bt;
    @FXML
    private TextField login_username;
    @FXML
    private PasswordField login_password;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == login_bt) {
            stage = (Stage) login_bt.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/src/app/java/view/welcome_scientist.fxml"));
        } else {
            stage = (Stage) login_reg_bt.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/src/app/java/view/registration.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private boolean validLogin() {
        boolean access_granted = false; 
        Connection c = null;
        Statement statement = null;
        try {
            c = DriverManager.getConnection("");
            c.setAutoCommit(false);
            
            statement = c.createStatement(); 
            
            ResultSet usersTable = statement.executeQuery("SELECT * FROM Users WHERE USERNAME= " + "'" + login_username.getText() + "'" 
            + " AND PASSWORD= " + "'" + login_password.getText() + "'");
        } catch (Exception e) {
            System.out.println("Something went wrong"); 
        }
        return access_granted;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //To change body of generated methods, choose Tools | Templates.
    }
}

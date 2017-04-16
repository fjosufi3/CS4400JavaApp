/*
 * Controller for login functionality
 */
package app.java.controller;

import app.java.model.ConnectionConfiguration;
import app.java.model.FormValidation;
import app.java.model.UserType;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label credentials;
    @FXML
    private Label username_label;
    @FXML
    private Label password_label;

    private Connection connection = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = ConnectionConfiguration.getConnection();
    }
    
    @FXML
    private void onLogin(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        boolean validUserEntry = FormValidation.textFieldNotEmpty(login_username, username_label, "Username is required");
        boolean validPassEntry = FormValidation.passwordFieldNotEmpty(login_password, password_label, "Password is required");
        boolean isValidLogin = FormValidation.isValidLogin(login_username.getText(), login_password.getText());
        String userType = FormValidation.getUserType(login_username.getText(), login_password.getText());
        if (isValidLogin) {
            if (userType.equals(UserType.Admin)) {
                root = FXMLLoader.load(getClass().getResource("/main/app/java/view/welcome_admin.fxml"));
            } else if (userType.equals(UserType.City_Scientist)) {
                root = FXMLLoader.load(getClass().getResource("/main/app/java/view/welcome_scientist.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("/main/app/java/view/welcome_official.fxml"));
            }
            stage = (Stage) login_bt.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            credentials.setText("Sorry, invalid credentials");
        }
    }

    @FXML
    private void onRegistration(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) login_reg_bt.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("main/app/java/view/registration.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

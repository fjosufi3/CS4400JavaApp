package app.java.controller;

/**
 * Controller for scientist navigation functionality
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeScientistController {

    @FXML
    private Button add_data_point;

    @FXML
    private Button add_poi;

    @FXML
    private Button logout_scientist;

    @FXML
    private void onAddData(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == add_data_point) {
            stage = (Stage) add_data_point.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/main/app/java/view/add_data_point.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            stage = (Stage) add_poi.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/main/app/java/view/add_location.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void onLogout(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) logout_scientist.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}


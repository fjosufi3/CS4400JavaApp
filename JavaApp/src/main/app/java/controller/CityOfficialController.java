package app.java.controller;

import app.java.model.ConnectionConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

/**
 * Created by mcw0805 on 4/16/17.
 */
public class CityOfficialController implements Initializable {

    @FXML
    private Button filterSearch_data_point_btn;
    @FXML
    private Button poi_report_btn;
    @FXML
    private Button logout_official_btn;

    private Connection connection = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = ConnectionConfiguration.getConnection();
    }

    @FXML
    private void onClickFilterSearch(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) filterSearch_data_point_btn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/browse_view_poi.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onClickPOIReport(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) poi_report_btn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/poi_report.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onClickLogout(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) logout_official_btn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}

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
public class AdminController implements Initializable {

    @FXML
    private Button pending_data_point_btn;
    @FXML
    private Button pending_official_acct_btn;
    @FXML
    private Button logout_admin_btn;

    private Connection connection = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = ConnectionConfiguration.getConnection();
    }

    @FXML
    private void onClickPendingData(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) pending_data_point_btn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/pending_data_points.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onClickPendingOfficial(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) pending_official_acct_btn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/pending_official_accounts.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}

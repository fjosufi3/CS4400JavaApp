package app.java.controller;

import app.java.model.FormValidation;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Add data point functionality
 */
public class AddDataPointController implements Initializable {

    @FXML
    private ComboBox poi_location;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox data_type;
    @FXML
    private TextField data_value;

    //buttons
    @FXML
    private Button add_new_loc;
    @FXML
    private Button data_cancel;
    @FXML
    private Button data_submit;

    @FXML
    private void onCancel(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) data_cancel.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/welcome_scientist.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onAddNewLocation(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) add_new_loc.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/add_location.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onSubmit(ActionEvent event) throws IOException {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date.setValue(LocalDate.now());
        ObservableList<String> types = FormValidation.getType();
        data_type.setItems(types);
    }
}

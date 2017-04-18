package app.java.controller;
import app.java.model.FormValidation;
import app.java.model.POI;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Add POI location functionality
 */

public class AddLocationController implements Initializable {

    @FXML
    private Button cancel_location;

    @FXML
    private Button submit_location;

    @FXML
    private TextField poi_location;

    @FXML
    private ComboBox city_box;

    @FXML
    private ComboBox state_box;

    @FXML
    private TextField zip_code;

    //labels
    @FXML
    private Label loc_label;
    @FXML
    private Label zip_label;
    @FXML
    private Label cs_label;

    @FXML
    private void onCancel(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) cancel_location.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/welcome_scientist.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onSubmit(ActionEvent event) throws IOException, ParseException {
        boolean isValidZip = FormValidation.isValidZipCode(zip_code.getText());
        if (!isValidZip) {
            zip_label.setText("Please enter a valid zip code");
        }
        boolean locNotEmpty = FormValidation.textFieldNotEmpty(poi_location, loc_label, "Location name is required");
        boolean zipNotEmpty = FormValidation.textFieldNotEmpty(zip_code, zip_label, "Zip code is required");
        String city = city_box.getSelectionModel().getSelectedItem().toString();
        String state = state_box.getSelectionModel().getSelectedItem().toString();
        java.util.Date df = new java.util.Date();
        //initial date irrelevant as all locations are initially unflagged
        java.sql.Date df1 = new java.sql.Date(df.getTime());
        boolean isValidCityState = FormValidation.isValidCityState(city, state, cs_label);
        if (isValidZip && locNotEmpty && zipNotEmpty && isValidCityState) {
            POI poi = new POI(poi_location.getText().toString(), city, state, zip_code.getText().toString(), 0, df);
            FormValidation.addNewLocation(poi.getLocation(), poi.getCity(), poi.getState(), poi.getZip(),
                    Boolean.valueOf(poi.getFlagString()), df1);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> cities = FormValidation.populateCityBox();
        ObservableList<String> states = FormValidation.populateStateBox();
        city_box.setItems(cities);
        city_box.getSelectionModel().selectFirst();
        state_box.setItems(states);
        state_box.getSelectionModel().selectFirst();
    }
}
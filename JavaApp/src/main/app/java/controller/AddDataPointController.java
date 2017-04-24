package app.java.controller;

import app.java.model.DataPoint;
import app.java.model.DataType;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Add data point functionality
 */
public class AddDataPointController implements Initializable {

    @FXML
    private ComboBox location_name;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTimePicker time;
    @FXML
    private ComboBox data_type;
    @FXML
    private TextField data_value;
    @FXML
    private Label value_label;

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
    private void onSubmit(ActionEvent event) throws IOException, ParseException {
        boolean validInteger = FormValidation.isValidInteger(data_value);
        String poi = location_name.getSelectionModel().getSelectedItem().toString();
        DataType type = new DataType(data_type.getSelectionModel().getSelectedItem().toString());
        Date _date = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String dt = date.getValue().toString() + " " + time.getValue().toString();
        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date _d = format.parse(dt);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(_d.getTime());
        boolean d_value = FormValidation.textFieldNotEmpty(data_value, value_label, "Required");

        if (d_value && validInteger) {
            Boolean accepted = null;
            LocalDate localDate = date.getValue();
            int value = Integer.parseInt(data_value.getText());
            DataPoint dataPoint = new DataPoint(poi, false, type, value, _date, Time.valueOf(time.getValue()));
            FormValidation.addNewDataPoint(dataPoint.getDataValue(), timestamp, dataPoint.isAccepted(),
                    dataPoint.getLocationName(), dataPoint.getDataType().toString());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date.setValue(LocalDate.now());
        time.setValue(LocalTime.now());
        ObservableList<String> types = FormValidation.getType();
        ObservableList<String> loc_names = FormValidation.populateLocationNames();
        data_type.setItems(types);
        location_name.setItems(loc_names);
        data_type.getSelectionModel().selectFirst();
        location_name.getSelectionModel().selectFirst();
    }
}

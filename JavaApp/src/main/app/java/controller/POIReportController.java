package app.java.controller;

import app.java.model.DataPoint;
import app.java.model.POI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

/**
 * POI Report Controller Class
 *
 * @author mcw0805
 */
public class POIReportController implements Initializable {

    @FXML
    private TableView poiReportView;
    @FXML
    private TableColumn<POI, String> columnPOILoc;
    @FXML
    private TableColumn<POI, String> columnCity;
    @FXML
    private TableColumn<POI, String> columnState;
    @FXML
    private TableColumn columnMoldMin;
    @FXML
    private TableColumn columnMoldAvg;
    @FXML
    private TableColumn columnAQMin;
    @FXML
    private TableColumn columnAQAvg;
    @FXML
    private TableColumn columnAQMax;
    @FXML
    private TableColumn columnNumDataPts;
    @FXML
    private TableColumn<POI, Integer> columnFlagged;
    @FXML
    private Button backBtn_poi_report;


    private ObservableList data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onClickBack(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) backBtn_poi_report.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/welcome_official.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}

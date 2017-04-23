package app.java.controller;

import app.java.model.ConnectionConfiguration;

import app.java.model.POIReport;
import app.java.model.POI;
import app.java.model.POIReport;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * POI Report Controller Class
 *
 * @author mcw0805
 */
public class POIReportController implements Initializable {

    @FXML
    private TableView<POIReport> poiReportView;
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
    private TableColumn columnMoldMax;
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


    private ObservableList<POIReport> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellTable();
        loadFromDB();
    }

    @FXML
    private void setCellTable() {
        columnPOILoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnState.setCellValueFactory(new PropertyValueFactory<>("state"));
        columnMoldMin.setCellValueFactory(new PropertyValueFactory<>("moldMin"));
        columnMoldAvg.setCellValueFactory(new PropertyValueFactory<>("moldAvg"));
        columnMoldMax.setCellValueFactory(new PropertyValueFactory<>("moldMax"));
        columnAQMin.setCellValueFactory(new PropertyValueFactory<>("aqMin"));
        columnAQAvg.setCellValueFactory(new PropertyValueFactory<>("aqAvg"));
        columnAQMax.setCellValueFactory(new PropertyValueFactory<>("aqMax"));
        columnNumDataPts.setCellFactory(new PropertyValueFactory<>("numPoints"));
        columnFlagged.setCellValueFactory(new PropertyValueFactory<>("flag"));

    }

    private void loadFromDB() {

        Connection con = ConnectionConfiguration.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM test_report");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                data.add(new POIReport(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11)));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        poiReportView.setItems(data);

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

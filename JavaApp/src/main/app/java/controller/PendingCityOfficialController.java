package app.java.controller;

import app.java.model.CityOfficial;
import app.java.model.CityState;
import app.java.model.ConnectionConfiguration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mcw0805 on 4/16/17.
 */
public class PendingCityOfficialController implements Initializable {

    @FXML
    private TableView<CityOfficial> pendingOfficialView;
    @FXML
    private TableColumn<CheckBoxTableCell, Boolean> columnSelect; //may need to change later
    @FXML
    private TableColumn<CityOfficial, String> columnUsername;
    @FXML
    private TableColumn<CityOfficial, String> columnEmail;
    @FXML
    private TableColumn<CityState, String> columnCity;
    @FXML
    private TableColumn<CityState, String> columnState;
    @FXML
    private TableColumn<CityOfficial, String> columnTitle;
    @FXML
    private Button acceptBtn_pending_officialAcct;
    @FXML
    private Button rejectBtn_pending_officialAcct;
    @FXML
    private Button backBtn_pending_officialAcct;

    private ObservableList<CityOfficial> data;
    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = ConnectionConfiguration.getConnection();
        data = FXCollections.observableArrayList();
    }

    private void loadFromDB() {
        try {
            pst = connection.prepareStatement("SELECT * FROM CITY_OFFICIAL WHERE Approved IS NULL");
            rs = pst.executeQuery();

            //pull data, adjust columns based on tables in DB
            while (rs.next()) {


            }
        } catch (SQLException ex) {
            Logger.getLogger(PendingCityOfficialController.class.getName()).log(Level.SEVERE, null, ex);
        }

        pendingOfficialView.setItems(data);
    }
}

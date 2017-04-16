package app.java.controller;

import app.java.model.ConnectionConfiguration;
import app.java.model.DataPoint;
import app.java.model.POI;
import app.java.model.DataType;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PendingDataController implements Initializable {

    @FXML
    private Text screenHeader;
    @FXML
    private TableView<DataPoint> pendingDataView;
    @FXML
    private TableColumn<CheckBoxTableCell, Boolean> columnSelect;
    @FXML
    private TableColumn<POI, String> columnPOILoc;
    @FXML
    private TableColumn<DataPoint, DataType> columnDataType;
    @FXML
    private TableColumn<DataPoint, Integer> columnDataValue;
    @FXML
    private TableColumn<DataPoint, String> columnDateAndTime;
    @FXML
    private Button acceptBtn_pending_data;
    @FXML
    private Button rejectBtn_pending_data;
    @FXML
    private Button backBtn_pending_data;

    private ObservableList<DataPoint> data;
    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = ConnectionConfiguration.getConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
        loadFromDB();

    }

    public void loadFromDB() {
        try {

            pst = connection.prepareStatement("SELECT * FROM DATA_POINT WHERE Accepted IS NULL");
            rs = pst.executeQuery();

            while (rs.next()) {
                //get string from db,whichever way
                data.add(new DataPoint(rs.getString(4),
                        rs.getBoolean(3),
                        new DataType(rs.getString(5)),
                        rs.getInt(1),
                        rs.getDate(2),
                        rs.getTime(2)));

            }


        } catch (SQLException ex) {
            Logger.getLogger(PendingDataController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Set cell value factory to tableview.
        //NB.PropertyValue Factory must be the same with the one set in model class.

        pendingDataView.setItems(data);

    }

    private void setCellTable() {
        columnSelect.setCellValueFactory(new PropertyValueFactory<>("accepted"));
        columnSelect.setCellFactory(column -> new CheckBoxTableCell());
        columnPOILoc.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        columnDataType.setCellValueFactory(new PropertyValueFactory<>("dataType"));
        columnDataValue.setCellValueFactory(new PropertyValueFactory<>("dataValue"));
        columnDateAndTime.setCellValueFactory(cellData -> cellData.getValue().getDateTime());

    }

    @FXML
    private void onClickBack(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) backBtn_pending_data.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/welcome_admin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    private void onAccept(ActionEvent event) {
        //push checked data to the DB

    }

    private void onReject(ActionEvent event) {
        //delete checked data from the DB

    }
}

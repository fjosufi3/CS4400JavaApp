package app.java.controller;

import app.java.model.ConnectionConfiguration;
import app.java.model.DataPoint;
import app.java.model.POI;
import app.java.model.DataType;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PendingDataController implements Initializable {

    @FXML
    private Text screenHeader;
    @FXML
    private TableView<DataPoint> pendingDataView;
    @FXML
    private TableColumn<DataPoint, CheckBox> columnSelect;
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
        columnSelect.setCellValueFactory(new PendingDataValFactory());
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

    @FXML
    private void onClickAccept(ActionEvent event) {
        //push checked data to the DB

        for (int i = 0; i < data.size(); i++) {
            ObservableValue<CheckBox> c = columnSelect.getCellObservableValue(data.get(i));
            CheckBox cb = c.getValue();
            boolean isSelected = cb.isSelected();
            System.out.println(isSelected);

            if (isSelected) {
                data.get(i).setAccepted(true);
                String locNamePK = pendingDataView.getItems().get(i).getLocationName();
                String dateTimePK = pendingDataView.getItems().get(i).getDateTimeString();
                //System.out.println(dateTimePK);

                try {
//                    PreparedStatement changeStatement = connection.prepareStatement("SELECT Location_Name, Date_Time " +
//                            "FROM DATA_POINT WHERE Location_Name = '"+locNamePK+"' and  Date_time = '"+dateTimePK+"'     ");

                    PreparedStatement changeStatement = connection.prepareStatement("UPDATE DATA_POINT " +
                            "SET Accepted = TRUE WHERE Location_Name = ? and  Date_Time = ?");
                    changeStatement.setString(1, locNamePK);
                    changeStatement.setString(2, dateTimePK);

                    changeStatement.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                } //end catch block

                pendingDataView.getItems().remove(data.get(i));
            }


        } //end for

    }

    @FXML
    private void onClickReject(ActionEvent event) {

        for (int i = 0; i < data.size(); i++) {
            ObservableValue<CheckBox> c = columnSelect.getCellObservableValue(data.get(i));
            CheckBox cb = c.getValue();
            boolean isSelected = cb.isSelected();
            System.out.println(isSelected);

            if (isSelected) {
                data.get(i).setAccepted(false);
                String locNamePK = pendingDataView.getItems().get(i).getLocationName();
                String dateTimePK = pendingDataView.getItems().get(i).getDateTimeString();
                //System.out.println(dateTimePK);

                try {
                    PreparedStatement changeStatement = connection.prepareStatement("DELETE FROM DATA_POINT " +
                            "WHERE Location_Name = ? and  Date_Time = ?");
                    changeStatement.setString(1, locNamePK);
                    changeStatement.setString(2, dateTimePK);

                    changeStatement.executeUpdate();


                } catch (SQLException e) {
                    e.printStackTrace();
                }

                pendingDataView.getItems().remove(data.get(i));
            }

        } //end for loop

    }

}

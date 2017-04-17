package app.java.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import app.java.model.ConnectionConfiguration;
import app.java.model.POI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Created by mcw0805 on 4/14/17.
 */
public class ViewPOIController implements Initializable {

    @FXML
    private Label pageHeader;
    @FXML
    private TableView<POI> poiTableView;
    @FXML
    private TableColumn<POI, String> columnLocation;
    @FXML
    private TableColumn<POI, String> columnCity;
    @FXML
    private TableColumn<POI, String> columnState;
    @FXML
    private TableColumn<POI, String> columnZipCode;
    @FXML
    private TableColumn<POI, Integer> columnFlag;
    @FXML
    private TableColumn<POI, Date> columnDate;
    @FXML
    private ComboBox poi_loc_box;
    @FXML
    private ComboBox city_view_poi_box;
    @FXML
    private ComboBox state_view_poi_box;
    @FXML
    private TextField zip_view_poi;
    @FXML
    private CheckBox flagged_checkBox;
    @FXML
    private DatePicker dateStart_view_poi;
    @FXML
    private DatePicker dateEnd_view_poi;
    @FXML
    private Button apply_filter_view_poi_btn;
    @FXML
    private Button reset_filter_view_poi_btn;
    @FXML
    private Button backBtn_view_poi;
    @FXML
    private Label zip_valid_label;

    private ObservableList<String> cityList = FXCollections.observableArrayList();
    private ObservableList<String> stateList = FXCollections.observableArrayList();
    private ObservableList<String> poiLocList = FXCollections.observableArrayList();

    private ObservableList<POI> data;
    private Connection con = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        con = ConnectionConfiguration.getConnection();
        setCellTable();
        loadFromDB(); //temporary
        loadDropDown();

    }

    @FXML
    private void loadFromDB() {
        data = FXCollections.observableArrayList();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM POI");

            while (rs.next()) {
                data.add(new POI(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getDate(6)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        poiTableView.setItems(null);
        poiTableView.setItems(data);
    }

    private void setCellTable() {
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnState.setCellValueFactory(new PropertyValueFactory<>("state"));
        columnZipCode.setCellValueFactory(new PropertyValueFactory<>("zip"));
        columnFlag.setCellValueFactory(new PropertyValueFactory<>("flag"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("dateFlagged"));
    }

    @FXML
    private void loadDropDown() {

        try {
            PreparedStatement pst1 = con.prepareStatement("SELECT DISTINCT City FROM CITY_STATE ORDER BY City");
            ResultSet rs1 = pst1.executeQuery();


            while(rs1.next()) {
                //data.add(new CityState(rs1.getString(1), rs1.getString(2)));

                cityList.add(rs1.getString(1));
            }

            PreparedStatement pst2 = con.prepareStatement("SELECT DISTINCT State FROM CITY_STATE ORDER BY State");
            ResultSet rs2 = pst2.executeQuery();


            while(rs2.next()) {

                stateList.add(rs2.getString(1));
            }


            PreparedStatement pst3 = con.prepareStatement("SELECT Location_Name FROM POI ORDER BY Location_Name");
            ResultSet rs3 = pst3.executeQuery();

            while(rs3.next()) {
                poiLocList.add(rs3.getString(1));
            }

            city_view_poi_box.setItems(cityList);
            state_view_poi_box.setItems(stateList);
            poi_loc_box.setItems(poiLocList);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    private void onApplyFilter() {
//        boolean zipNotEmpty = FormValidation.textFieldNotEmpty(zip_view_poi, zip_valid_label, "Reqired");
//        boolean isValidZip = FormValidation.isValidZipCode(zip_view_poi.getText());
//
//        if(!zipNotEmpty || !isValidZip)
//            return; // fix later
//
//
//    }

    @FXML
    private void onResetFilter() {
        poiTableView.getItems().removeAll(data);
    }

    @FXML
    private void onClickBack(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) backBtn_view_poi.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/welcome_official.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}

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
import app.java.model.FormValidation;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * View POI functionality
 */
public class ViewPOIController implements Initializable {

    @FXML
    private AnchorPane viewPOIpane;
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
    private Label zip_invalid_label;
    @FXML
    private Label date_invalid_label;

    private ObservableList<String> cityList = FXCollections.observableArrayList();
    private ObservableList<String> stateList = FXCollections.observableArrayList();
    private ObservableList<String> poiLocList = FXCollections.observableArrayList();

    private ObservableList<POI> data = FXCollections.observableArrayList();
    private Connection con = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        con = ConnectionConfiguration.getConnection();
        setCellTable();
        loadDropDown();

    }

    private void setCellTable() {
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnState.setCellValueFactory(new PropertyValueFactory<>("state"));
        columnZipCode.setCellValueFactory(new PropertyValueFactory<>("zip"));
        columnFlag.setCellValueFactory(new PropertyValueFactory<>("flagString"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("dateFlagged"));

        poiTableView.setRowFactory(tv -> {
            TableRow<POI> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Stage stage = (Stage) viewPOIpane.getScene().getWindow();

                    try {
                        POIDetailController detail = new POIDetailController();
                        stage.setScene(detail.getScene());
                        System.out.println("row" + row.getItem() == null);
                        //Object o = poiTableView.getSelectionModel().getSelectedItem().getLocation();

                        System.out.println(poiTableView.getSelectionModel().getSelectedItem().getLocation());

                        //detail.setLocationText(poiTableView.getSelectionModel().getSelectedItem().getLocation());
                        //detail.setLocationText(row.getItem().getLocation());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.show();
                }
            });
            return row;
        });

    }

    @FXML
    private void loadDropDown() {

        try {
            //loading cities
            PreparedStatement pstCity = con.prepareStatement("SELECT DISTINCT City FROM CITY_STATE ORDER BY City");
            ResultSet rsCity = pstCity.executeQuery();

            while (rsCity.next()) {
                cityList.add(rsCity.getString(1));
            }

            //loading states
            PreparedStatement pstState = con.prepareStatement("SELECT DISTINCT State FROM CITY_STATE ORDER BY State");
            ResultSet rsState = pstState.executeQuery();

            while (rsState.next()) {
                stateList.add(rsState.getString(1));
            }

            //loading POI locations
            PreparedStatement pstLocName = con.prepareStatement("SELECT Location_Name FROM POI ORDER BY Location_Name");
            ResultSet rsLocName = pstLocName.executeQuery();

            while (rsLocName.next()) {
                poiLocList.add(rsLocName.getString(1));
            }

            city_view_poi_box.setItems(cityList);
            state_view_poi_box.setItems(stateList);
            poi_loc_box.setItems(poiLocList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void onApplyFilter() {

        zip_invalid_label.setText("");
        date_invalid_label.setText("");

        boolean zipNotEmpty = FormValidation.textFieldNotEmpty(zip_view_poi);
        boolean isValidZip = zipNotEmpty && FormValidation.isValidZipCode(zip_view_poi.getText(), zip_invalid_label);
        boolean isSelectedLocation = !(poi_loc_box.getSelectionModel().isEmpty());
        boolean isSelectedCity = !(city_view_poi_box.getSelectionModel().isEmpty());
        boolean isSelectedState = !(state_view_poi_box.getSelectionModel().isEmpty());
        boolean isFlagged = flagged_checkBox.isSelected();
        boolean isValidStartDate = dateStart_view_poi.getValue() != null;
        boolean isValidEndDate = dateEnd_view_poi.getValue() != null;
        boolean isValidRange = isValidStartDate && isValidEndDate && dateEnd_view_poi.getValue().compareTo(dateStart_view_poi.getValue()) >= 0;

        String POILocation = null;
        String city = null;
        String state = null;
        String flag = null;
        String zipCode = null;
        String dateStart = null;
        String dateEnd = null;

        if (isValidZip) {
            zipCode = "Zip_Code = " + zip_view_poi.getText();
        }

        if (isSelectedLocation) {
            POILocation = "Location_Name = \'" + poi_loc_box.getSelectionModel().getSelectedItem().toString() + "\'";

        }

        if (isSelectedCity) {
            city = "City = \'" + city_view_poi_box.getSelectionModel().getSelectedItem().toString() + "\'";
        }

        if (isSelectedState) {
            state = "State = \'" + state_view_poi_box.getSelectionModel().getSelectedItem().toString() + "\'";
        }

        if (isFlagged) {
            flag = "Flag = TRUE";
            //flag = "TRUE";
        }

        if (isValidStartDate) {
            dateStart = "Date_Flagged >= \'" + dateStart_view_poi.getValue().toString() + "\'";
            //dateStart = dateStart_view_poi.getValue().toString();
        }

        if (isValidEndDate) {
            dateEnd = "Date_Flagged <= \'" + dateEnd_view_poi.getValue().toString() + "\'";
            //dateEnd = dateEnd_view_poi.getValue().toString();
        }

        System.out.println(POILocation);
        System.out.println(city);
        System.out.println(state);
        System.out.println("flagged? " + flag);
        System.out.println(zipCode);
        System.out.println("Date start " + dateStart);
        System.out.println("Date end " + dateEnd);

        //System.out.println(generateCondition(POILocation, city, state, flag, zipCode, dateStart, dateEnd));
        poiTableView.getItems().removeAll(data);

        if (POILocation == null && city == null && state == null && flag == null
                && zipCode == null && dateStart == null && dateEnd == null) { //checks empty fields
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("No fields are entered.");
            errorAlert.showAndWait();

        } else if ((dateStart == null && dateEnd != null)
                || (dateStart != null && dateEnd == null)) {

            //only one date entered
            date_invalid_label.setText("Enter a range of dates");

        } else if (dateStart != null && dateEnd != null && !isValidRange) {

            //start date > end date if entered
            date_invalid_label.setText("Invalid range");

        } else {

            try {
                PreparedStatement pst = con.prepareStatement("SELECT Location_Name, City, State, Zip_Code, Flag, Date_Flagged " +
                        "FROM POI " +
                        "WHERE " + generateCondition(POILocation, city, state, flag, zipCode, dateStart, dateEnd));
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    data.add(new POI(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getInt(5), rs.getDate(6)));
                }

                rs.close();
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        poiTableView.setItems(data);


    /*
     POI Relation attribute column names:
     ------------------------------------
      - Location_Name,
      - City
      - State
      - Zip_Code
      - Flag
      - Date_Flagged */

    }

    private String generateCondition(String POILoc, String city, String state, String flag,
                                     String zip, String startDate, String endDate) {

        //guaranteed at least one parameter is not null

        String whereClause = "";

        String[] paramArray = {POILoc, city, state, flag, zip, startDate, endDate};

        for (int i = 0; i < paramArray.length; i++) {
            if (paramArray[i] != null) {
                whereClause += paramArray[i] + " AND ";
            }
        }

        //removes the last "AND"
        if (whereClause.endsWith("AND ")) {
            whereClause = whereClause.substring(0, whereClause.length() - 4);
        }

        return whereClause;

    }

    @FXML
    private void onResetFilter() {

        //clears table
        poiTableView.getItems().removeAll(data);

        //clears combo box
        poi_loc_box.getSelectionModel().clearSelection();
        city_view_poi_box.getSelectionModel().clearSelection();
        state_view_poi_box.getSelectionModel().clearSelection();

        //clears TextField, label
        zip_view_poi.clear();

        //clears checkbox
        flagged_checkBox.setSelected(false);

        //clears dates
        dateStart_view_poi.getEditor().clear();
        dateStart_view_poi.setValue(null);
        dateEnd_view_poi.getEditor().clear();
        dateEnd_view_poi.setValue(null);

        //clear warnings
        zip_invalid_label.setText("");
        date_invalid_label.setText("");
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

//    public String getPOILocation() {
//        return poiTableView.getSelectionModel().getSelectedItem().getLocation();
//    }

    /*@FXML
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
    }*/



}

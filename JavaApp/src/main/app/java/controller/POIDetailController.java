package app.java.controller;

import app.java.model.ConnectionConfiguration;
import app.java.model.DataPoint;
import app.java.model.DataType;
import app.java.model.FormValidation;
import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by mcw0805 on 4/18/17.
 */
public class POIDetailController implements Initializable {


    @FXML
    private TableView poiDetailView;
    @FXML
    private TableColumn<DataType, String> columnDataType;
    @FXML
    private TableColumn<DataPoint, String> columnDataVal;
    @FXML
    private TableColumn<DataPoint, String> columnDateTime;
    @FXML
    private ComboBox dataTypeBox;
    @FXML
    private JFXCheckBox flagged_checkbox;
    @FXML
    private TextField minDataValTextField;
    @FXML
    private TextField maxDataValTextField;
    @FXML
    private DatePicker startDate_poiDetail;
    @FXML
    private DatePicker endDate_poiDetail;
    @FXML
    private Label typeRequired_label;
    @FXML
    private Label invalidDate_label;
    @FXML
    private Button applyFilter_poi_detail_btn;
    @FXML
    private Button resetFilter_poi_detail_btn;
    @FXML
    private Button backBtn_poi_detail;
    @FXML
    private Text chosen_loc = new Text("");

    private ObservableList<String> dataTypeList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDropDown();
    }

    public Scene getScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/app/java/view/poi_detail.fxml"));
        Scene scene = new Scene(root);

        return scene;
    }

    @FXML
    private void onClickBack(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) backBtn_poi_detail.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/browse_view_poi.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void setLocationText(String loc) {
        chosen_loc.setText(loc);

    }

    @FXML
    private void loadDropDown() {

        try {

            Connection con = ConnectionConfiguration.getConnection();

            //loading data type
            PreparedStatement pstDataType = con.prepareStatement("SELECT Type FROM DATA_TYPE ORDER BY Type");
            ResultSet rsDataType = pstDataType.executeQuery();

            while (rsDataType.next()) {
                dataTypeList.add(rsDataType.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dataTypeBox.setItems(dataTypeList);

    }

    @FXML
    private void onClickApplyFilter() {

        boolean minDataNotEmpty = FormValidation.textFieldNotEmpty(minDataValTextField);
        boolean maxDataNotEmpty = FormValidation.textFieldNotEmpty(maxDataValTextField);
        boolean isSelectedType = !(dataTypeBox.getSelectionModel().isEmpty());
        boolean isFlagged = flagged_checkbox.isSelected();
        boolean isValidStartDate = startDate_poiDetail.getValue() != null;
        boolean isValidEndDate = endDate_poiDetail.getValue() != null;
        boolean isValidRange = isValidStartDate && isValidEndDate && endDate_poiDetail.getValue().compareTo(startDate_poiDetail.getValue()) >= 0;

        String type = null;
        String minVal = null;
        String maxVal = null;
        String flag = null;
        String dateFlagged = null;
        String dateStart = null;
        String dateEnd = null;

        if (isSelectedType) {
            type = "Type = \'" + dataTypeBox.getSelectionModel()
                    .getSelectedItem().toString() + "\' ";
        }

        if (minDataNotEmpty) {
            minVal = "Data_Value >= " + Integer.parseInt(minDataValTextField.getText());
        }

        if (maxDataNotEmpty) {
            maxVal = "Data_Value <= " + Integer.parseInt(maxDataValTextField.getText());
        }


        if (isValidStartDate) {
            dateStart = "Date_Flagged >= \'" + startDate_poiDetail.getValue().toString() + "\'";
            //dateStart = dateStart_view_poi.getValue().toString();
        }

        if (isValidEndDate) {
            dateEnd = "Date_Flagged <= \'" + endDate_poiDetail.getValue().toString() + "\'";
            //dateEnd = endDate_poiDetail.getValue().toString();
        }

        if (isFlagged) {
            flag = "Flag = TRUE";

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFlagged = dateFormat.format(new Date());
            System.out.println("date flagged: " + dateFlagged);
        }


        if (type == null && minVal == null && maxVal == null && flag == null
                && dateStart == null && dateEnd == null) { //checks empty fields
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("No fields are entered.");
            errorAlert.showAndWait();

        } else if ((dateStart == null && dateEnd != null)
                || (dateStart != null && dateEnd == null)) {

            //only one date entered
            //date_invalid_label.setText("Enter a range of dates");
            System.out.println("enter range");

        } else if (dateStart != null && dateEnd != null && !isValidRange) {

            //start date > end date if entered
            //date_invalid_label.setText("Invalid range");
            System.out.println("invalid rage");

        } else {
            Connection con = ConnectionConfiguration.getConnection();
            try {
                PreparedStatement pst = con.prepareStatement(
                        "SELECT Type, Data_Value, DATE_TIME " +
                                "FROM DATA_POINT WHERE Location_Name = ? ");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
    private void onClickResetFilter() {

        dataTypeBox.getSelectionModel().clearSelection();

        minDataValTextField.clear();
        maxDataValTextField.clear();

        //clears dates
        startDate_poiDetail.getEditor().clear();
        startDate_poiDetail.setValue(null);
        endDate_poiDetail.getEditor().clear();
        endDate_poiDetail.setValue(null);

    }

}

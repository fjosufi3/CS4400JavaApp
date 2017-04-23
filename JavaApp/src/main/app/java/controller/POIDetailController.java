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
import javafx.scene.control.cell.PropertyValueFactory;
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

    private Stage stage = new Stage();

    private ObservableList<String> dataTypeList = FXCollections.observableArrayList();
    private ObservableList<DataPoint> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDropDown();
        setCellTable();

    }


    public void setLocationText(String loc) {
        chosen_loc.setText(loc);

    }

    public void setDetailScene(Scene scene) {
        stage.show();
        stage.setScene(scene);

    }

    private void setCellTable() {
        columnDataType.setCellValueFactory(new PropertyValueFactory<>("dataType"));
        columnDataVal.setCellValueFactory(new PropertyValueFactory<>("dataValue"));
        columnDateTime.setCellValueFactory(cellData -> cellData.getValue().getDateTime());

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
        boolean isNumericMin = minDataNotEmpty
                && FormValidation.isValidInteger(minDataValTextField);
        boolean isNumericMax = minDataNotEmpty
                && FormValidation.isValidInteger(maxDataValTextField);
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

            //type = dataTypeBox.getSelectionModel().getSelectedItem().toString();
        }

        if (isNumericMin) {
            minVal = "Data_Value >= " + Integer.parseInt(minDataValTextField.getText());
            //minVal =  minDataValTextField.getText();
        }

        if (isNumericMax) {
            maxVal = "Data_Value <= " + Integer.parseInt(maxDataValTextField.getText());
            //maxVal = maxDataValTextField.getText();
        }


        if (isValidStartDate) {
            dateStart = "POI.Date_Flagged >= \'" + startDate_poiDetail.getValue().toString() + "\'";
            //dateStart = startDate_poiDetail.getValue().toString();
        }

        if (isValidEndDate) {
            dateEnd = "POI.Date_Flagged <= \'" + endDate_poiDetail.getValue().toString() + "\'";
            //dateEnd = endDate_poiDetail.getValue().toString();
        }

        if (isFlagged) {
            flag = "Flag = TRUE";

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFlagged = dateFormat.format(new Date());
            System.out.println("date flagged: " + dateFlagged);
        }


        poiDetailView.getItems().removeAll(data);

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
            System.out.println(generateCondition(type, minVal, maxVal, dateStart, dateEnd));
            Connection con = ConnectionConfiguration.getConnection();
            try {
                PreparedStatement pst = con.prepareStatement(
                        "SELECT * " +
                                "FROM DATA_POINT, POI WHERE DATA_POINT.Location_Name = ? AND POI.Location_Name  = ? AND Accepted = TRUE AND "
                                + generateCondition(type, minVal, maxVal, dateStart, dateEnd) + " ORDER BY Type");

                pst.setString(1, chosen_loc.getText());
                pst.setString(2, chosen_loc.getText());

                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    data.add(new DataPoint(rs.getString(4),
                            rs.getBoolean(3),
                            new DataType(rs.getString(5)),
                            rs.getInt(1),
                            rs.getDate(2),
                            rs.getTime(2)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            poiDetailView.setItems(data);

        } //end else

    }

    private String generateCondition(String type, String minVal, String maxVal,
                                     String dateStart, String dateEnd) {

        String whereClause = "";

        String[] paramArray = {type, minVal, maxVal, dateStart, dateEnd};

        for (int i = 0; i < paramArray.length; i++) {
            if (paramArray[i] != null) {
                whereClause += paramArray[i] + " AND ";
            }

        }

        if (whereClause.endsWith("AND ")) {
            whereClause = whereClause.substring(0, whereClause.length() - 4);
        }

        return whereClause;

    }

    @FXML
    private void onClickResetFilter() {

        //clear table
        poiDetailView.getItems().removeAll(data);

        //clear combo box
        dataTypeBox.getSelectionModel().clearSelection();

        //data values TextField
        minDataValTextField.clear();
        maxDataValTextField.clear();

        //clears dates
        startDate_poiDetail.getEditor().clear();
        startDate_poiDetail.setValue(null);
        endDate_poiDetail.getEditor().clear();
        endDate_poiDetail.setValue(null);

    }

    @FXML
    private void onClickBack(ActionEvent event) throws IOException {
        stage.close();

    }

}

package app.java.controller;

import app.java.model.ConnectionConfiguration;
import app.java.model.DataPoint;
import app.java.model.DataType;
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
    private TextField minDataVal;
    @FXML
    private TextField maxDataVal;
    @FXML
    private DatePicker startDate_potDeatil;
    @FXML
    private DatePicker endDate_potDeatil;
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

    }

    @FXML
    private void onClickResetFilter() {

    }

}

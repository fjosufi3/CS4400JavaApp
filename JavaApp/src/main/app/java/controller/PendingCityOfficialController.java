package app.java.controller;

import app.java.model.CityOfficial;
import app.java.model.CityState;
import app.java.model.UserType;
import app.java.model.ConnectionConfiguration;
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

/**
 * Created by mcw0805 on 4/16/17.
 */
public class PendingCityOfficialController implements Initializable {

    @FXML
    private TableView<CityOfficial> pendingOfficialView;
    @FXML
    private TableColumn<CityOfficial, CheckBox> columnSelect; //may need to change later
    @FXML
    private TableColumn<CityOfficial, String> columnUsername;
    @FXML
    private TableColumn<CityOfficial, String> columnEmail;
    @FXML
    private TableColumn<CityState, String> columnCity;
    @FXML
    private TableColumn<CityState, String> columnState;
    @FXML
    private TableColumn<CityOfficial, String> columnOfficialTitle;
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
        setCellTable();
        loadFromDB();
    }

    private void loadFromDB() {
        try {

            //retrieve email and username from the USER table of the users who have "City_Official" Title
            //may be approved or not approve yet
//            PreparedStatement infoFromUser = connection.prepareStatement("SELECT Email, Username FROM USER" +
//                    "WHERE User_Type = \'City_Official\'");
//            ResultSet infoFromUserTable = infoFromUser.executeQuery();

            //PreparedStatement pendingCityTs
//                pst = connection.prepareStatement("SELECT * " +
//                    "FROM (USER as U join CITY_OFFICIAL as C on U.Username = C.Username and C.Approved IS NULL);");

            pst = connection.prepareStatement("SELECT * " +
                    "from (USER NATURAL JOIN CITY_OFFICIAL) WHERE CITY_OFFICIAL.Approved IS NULL;");
            rs = pst.executeQuery();


//            pst = connection.prepareStatement("SELECT * FROM CITY_OFFICIAL WHERE Approved IS NULL");
//            rs = pst.executeQuery();

            //pull data, adjust columns based on tables in DB 
            while (rs.next()) {
                CityState cs = new CityState(rs.getString(7), rs.getString(8));
                data.add(new CityOfficial(rs.getString(2),
                        rs.getString(1),
                        rs.getString(3),
                        UserType.City_Official,
                        rs.getString(5),
                        rs.getBoolean(6),
                        cs)
                );

            }
        } catch (SQLException ex) {
            Logger.getLogger(PendingCityOfficialController.class.getName()).log(Level.SEVERE, null, ex);
        }

        pendingOfficialView.setItems(data);
    }

    private void setCellTable() {
        columnSelect.setCellValueFactory(new PropertyValueFactory<>("approval"));
        columnSelect.setCellValueFactory(new PendingOfficialFactory());
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnOfficialTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnState.setCellValueFactory(new PropertyValueFactory<>("state"));

    }

    @FXML
    private void onClickBack(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) backBtn_pending_officialAcct.getScene().getWindow();
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
            //System.out.println(isSelected);

            if (isSelected) {
                data.get(i).setApproval(true);
                String usernamePK = pendingOfficialView.getItems().get(i).getUsername();
                //System.out.println(dateTimePK);

                try {
//                    PreparedStatement changeStatement = connection.prepareStatement("SELECT Location_Name, Date_Time " +
//                            "FROM DATA_POINT WHERE Location_Name = '"+locNamePK+"' and  Date_time = '"+dateTimePK+"'     ");

                    PreparedStatement changeStatement = connection.prepareStatement("UPDATE CITY_OFFICIAL " +
                            "SET Approved = TRUE WHERE Username = ?");
                    changeStatement.setString(1, usernamePK);

                    changeStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } //end catch block

                pendingOfficialView.getItems().remove(data.get(i));
            }


        } //end for


    }

    @FXML
    private void onClickReject(ActionEvent event) {
        //delete checked data from the DB

        for (int i = 0; i < data.size(); i++) {
            ObservableValue<CheckBox> c = columnSelect.getCellObservableValue(data.get(i));
            CheckBox cb = c.getValue();
            boolean isSelected = cb.isSelected();
            System.out.println(isSelected);

            if (isSelected) {
                data.get(i).setApproval(false);
                String usernamePK = pendingOfficialView.getItems().get(i).getUsername();

                try {
                    PreparedStatement changeStatement = connection.prepareStatement("DELETE FROM CITY_OFFICIAL " +
                            "WHERE Username = ?");
                    changeStatement.setString(1, usernamePK);

                    changeStatement.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                pendingOfficialView.getItems().remove(data.get(i));
            }

        } //end for loop

    }
}

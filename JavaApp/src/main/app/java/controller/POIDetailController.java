package app.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mcw0805 on 4/18/17.
 */
public class POIDetailController implements Initializable {


    @FXML
    private Button backBtn_poi_detail;
    @FXML
    private Text chosen_loc;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/welcome_official.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

//    public void setLocationText() {
//        //chosen_loc.setText(loc);
//        chosen_loc.setText((new ViewPOIController()).getPOILocation());
//    }
}

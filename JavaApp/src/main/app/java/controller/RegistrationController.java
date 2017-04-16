package app.java.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.java.model.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.soap.Text;

/**
 * FXML Controller class
 *
 * @author Josufi
 */
public class RegistrationController implements Initializable {

    @FXML
    private Button reg_submit;
    @FXML
    private Button reg_cancel;
    @FXML
    private TextField username_reg;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password_reg;
    @FXML
    private PasswordField confirm_password;
    ObservableList<String> user_types =
            FXCollections.observableArrayList(
                    UserType.City_Scientist.toString(),
                    UserType.City_Official.toString()
            );
    @FXML
    private ComboBox user_type_box;
    //city official information
    @FXML
    private ComboBox city_box;
    @FXML
    private ComboBox state_box;
    @FXML
    private TextField city_official_title;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == reg_cancel) {
            stage = (Stage) reg_cancel.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/main/app/java/view/login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {

        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user_type_box.setItems(user_types);
        user_type_box.getSelectionModel().selectFirst();
    }
}

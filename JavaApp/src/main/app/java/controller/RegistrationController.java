package app.java.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.java.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.xml.soap.Text;

/**
 * FXML Controller class
 *
 * @author Josufi
 */
public class RegistrationController implements Initializable {
    //buttons
    @FXML
    private Button reg_submit;
    @FXML
    private Button reg_cancel;
    @FXML
    //fields
    private TextField username_reg;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password_reg;
    @FXML
    private PasswordField confirm_password;
    @FXML
    private TextField city_official_title;
    //spinners
    @FXML
    private ComboBox user_type_box;
    //city official information
    @FXML
    private ComboBox city_box;
    @FXML
    private ComboBox state_box;
    //labels
    @FXML
    private Label username_label;
    @FXML
    private Label password_label;
    @FXML
    private Label email_label;
    @FXML
    private Label confirm_password_label;
    @FXML
    private Label title_label;
    @FXML
    private Label city_state_label;


    @FXML
    private void onCancel(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) reg_cancel.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/app/java/view/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void onSubmit(ActionEvent event) throws IOException {
        String selectedType = user_type_box.getSelectionModel().getSelectedItem().toString();
        boolean typeCO = selectedType.equals(UserType.City_Official.toString());
        boolean validUserEntry = FormValidation.textFieldNotEmpty(username_reg, username_label, "Required");
        boolean usernameAvailable = FormValidation.usernameAvailable(username_reg, username_label);
        boolean emailAvailable = FormValidation.emailAvailable(email, email_label);
        boolean isValidEmail = FormValidation.isValidEmailAddress(email, email_label);
        boolean validPassEntry = FormValidation.passwordFieldNotEmpty(password_reg, password_label, "Required");
        boolean passwordMatch = FormValidation.passwordMatch(password_reg, confirm_password, confirm_password_label);
        boolean passwordLength = FormValidation.passwordLength(password_reg, password_label);
        boolean validCity = FormValidation.isValidCityState(city_box.getSelectionModel().getSelectedItem().toString(),
                state_box.getSelectionModel().getSelectedItem().toString(), city_state_label);

        if (!typeCO && validUserEntry && usernameAvailable && emailAvailable && isValidEmail && validPassEntry
                && passwordMatch && passwordLength) {
            User newUser = new User(email.getText(), username_reg.getText(), password_reg.getText(), UserType.City_Scientist);
            FormValidation.addNewUser(newUser.getEmail(), newUser.getUsername(), newUser.getPassword(),
                    newUser.getUserType());

            Stage stage;
            Parent root;
            stage = (Stage) reg_submit.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/main/app/java/view/welcome_scientist.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (typeCO) {
            boolean validTitle = FormValidation.textFieldNotEmpty(city_official_title, title_label, "Required");
            if (validUserEntry && usernameAvailable && emailAvailable && isValidEmail && validPassEntry
                    && passwordMatch && passwordLength && validTitle && validCity) {
                CityState CO_location = new CityState(new SimpleStringProperty(city_box.getSelectionModel().getSelectedItem().toString()),
                        new SimpleStringProperty(state_box.getSelectionModel().getSelectedItem().toString()));

                User newUser = new User(email.getText(), username_reg.getText(), password_reg.getText(), UserType.City_Official);
                CityOfficial newCO = new CityOfficial(email.getText(), username_reg.getText(), password_reg.getText(),
                        UserType.City_Official, city_official_title.getText(), false, CO_location);

                FormValidation.addNewUser(newUser.getEmail(), newUser.getUsername(), newUser.getPassword(), newUser.getUserType());
                FormValidation.addNewCityOfficial(newCO.getUsername(), newCO.getTitle(), newCO.getCityState().getCity(),
                        newCO.getCityState().getState());

                Stage stage;
                Parent root;
                stage = (Stage) reg_submit.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/main/app/java/view/welcome_official.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> user_types =
                FXCollections.observableArrayList(
                        UserType.City_Scientist.toString(),
                        UserType.City_Official.toString()
                );
        ObservableList<String> cities =
                FXCollections.observableArrayList(
                        "New York", "Los Angeles", "Chicago", "Houston", "Philadelphia", "Phoenix",
                        "San Antonio", "San Diego", "Dallas", "San Jose", "Austin", "Jacksonville", "San Francisco",
                        "Indianapolis", "Columbus", "Forth Worth", "Charlotte", "Seattle", "Denver", "El Paso", "Atlanta"
                );
        ObservableList<String> states =
                FXCollections.observableArrayList(
                        "New York", "California", "Illinois", "Texas", "Pennsylvania", "Arizona", "Florida",
                        "Indiana", "Ohio", "North Carolina", "Washington", "Colorado", "Georgia"
                );
        user_type_box.setItems(user_types);
        user_type_box.getSelectionModel().selectFirst();
        city_box.setItems(cities);
        city_box.getSelectionModel().selectFirst();
        state_box.setItems(states);
        state_box.getSelectionModel().selectFirst();
    }
}

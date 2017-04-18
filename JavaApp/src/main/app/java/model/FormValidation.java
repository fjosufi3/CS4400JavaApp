package app.java.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javax.swing.*;
import javax.xml.soap.Text;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Alert;

/**
 * Used to validate fields
 */
public class FormValidation {

    public static boolean textFieldNotEmpty(TextField t) {
        boolean notEmpty = false;
        if (t.getText() != null && !t.getText().isEmpty()) {
            notEmpty = true;
        }
        return notEmpty;
    }

    public static boolean textFieldNotEmpty(TextField t, Label label, String validationText) {
        boolean notEmpty = true;
        String prompt = null;
        if (!textFieldNotEmpty(t)) {
            notEmpty = false;
            prompt = validationText;
        }
        label.setText(prompt);
        return notEmpty;
    }

    public static boolean usernameAvailable(TextField username, Label label) {
        boolean email_available = true;
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT Username FROM USER WHERE Username = ?";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);
            statement.setString(1, username.getText());

            ResultSet usersTable = statement.executeQuery();

            if (usersTable.next()) {
                email_available = false;
                label.setText("Username is already taken");
            }
            usersTable.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the database");
        }
        return email_available;
    }

    public static boolean emailAvailable(TextField email, Label label) {
        boolean email_available = true;
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT Email FROM USER WHERE Email = ?";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);
            statement.setString(1, email.getText());

            ResultSet usersTable = statement.executeQuery();

            if (usersTable.next()) {
                email_available = false;
                label.setText("Email is already taken");
            }
            usersTable.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the database");
        }
        return email_available;
    }

    public static boolean isValidEmailAddress(TextField email, Label label) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email.getText());
        if (m.matches()) {
            return true;
        } else {
            label.setText("Invalid email");
        }
        return false;
    }

    public static boolean passwordFieldNotEmpty(PasswordField p) {
        boolean notEmpty = false;
        if (p.getText() != null && !p.getText().isEmpty()) {
            notEmpty = true;
        }
        return notEmpty;
    }

    public static boolean passwordFieldNotEmpty(PasswordField p, Label label, String validationText) {
        boolean notEmpty = true;
        String prompt = null;
        if (!passwordFieldNotEmpty(p)) {
            notEmpty = false;
            prompt = validationText;
        }
        label.setText(prompt);
        return notEmpty;
    }

    public static boolean passwordMatch(PasswordField pass, PasswordField confirm_pass, Label label) {
        boolean match = true;
        if (!pass.getText().equals(confirm_pass.getText())) {
            match = false;
            label.setText("Passwords must match");
        }
        return match;
    }

    public static boolean passwordLength(PasswordField pass, Label label) {
        boolean longEnough = false;
        if (pass.getText().length() >= 4) {
            longEnough = true;
        }
        return longEnough;
    }

    public static ObservableList<String> populateCityBox() {
        ObservableList<String> citiesArr = FXCollections.observableArrayList();
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT City FROM CITY_STATE";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);

            ResultSet cities = statement.executeQuery();

            while (cities.next()) {
                citiesArr.add(cities.getString("City"));
            }
            cities.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the database");
        }
        return citiesArr;
    }

    public static ObservableList<String> populateStateBox() {
        ObservableList<String> statesArr = FXCollections.observableArrayList();
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT State FROM CITY_STATE";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);

            ResultSet states = statement.executeQuery();

            while (states.next()) {
                statesArr.add(states.getString("State"));
            }
            states.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the database");
        }
        return statesArr;
    }

    public static ObservableList<String> populateLocationNames() {
        ObservableList<String> locationsArr = FXCollections.observableArrayList();
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT Location_Name FROM POI";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);

            ResultSet poi_location = statement.executeQuery();

            while (poi_location.next()) {
                locationsArr.add(poi_location.getString("Location_Name"));
            }
            poi_location.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the database");
        }
        return locationsArr;
    }

    public static boolean isValidCityState(String city, String state, Label label) {
        boolean validCityState = false;
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM CITY_STATE";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);

            ResultSet cityStates = statement.executeQuery();

            while (cityStates.next()) {
                if (city.equals(cityStates.getString("City")) && state.equals(cityStates.getString("State"))) {
                    validCityState = true;
                    label.setText("");
                }
            }
            cityStates.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the database");
        }
        if (!validCityState) {
            label.setText("Invalid city/state");
        }
        return validCityState;
    }

    public static boolean isValidZipCode(String zip) {
        boolean validZipCode = false;
        String zipCodePattern = "\\d{5}(-\\d{4})?";

        validZipCode = zip.matches(zipCodePattern);

        return validZipCode;

    }

    public static boolean isValidInteger(TextField text) {
        boolean validInteger = true;
        try {
            Integer.parseInt(text.getText());
        } catch (NumberFormatException n) {
            validInteger = false;
        }
        return validInteger;
    }

    public static boolean isValidLogin(TextField user, PasswordField pass, Label label) {
        boolean access_granted = false;
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT Username, Password FROM USER WHERE Username = ? AND Password = ?";
        if (textFieldNotEmpty(user) && passwordFieldNotEmpty(pass)) {
            try {
                c = ConnectionConfiguration.getConnection();
                statement = c.prepareStatement(query);
                statement.setString(1, user.getText());
                statement.setString(2, pass.getText());

                ResultSet usersTable = statement.executeQuery();

                if (usersTable.next()) {
                    access_granted = true;
                } else {
                    label.setText("Sorry, invalid credentials");
                }
                usersTable.close();
                statement.close();
                c.close();
            } catch (Exception e) {
                System.out.println("Something went wrong with the database");
            }
        }
        return access_granted;
    }

    public static String getUserType(TextField user, PasswordField pass) {
        String userType = null;
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM USER WHERE Username = ? AND Password = ?";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);
            statement.setString(1, user.getText());
            statement.setString(2, pass.getText());

            ResultSet usersTable = statement.executeQuery();

            if (usersTable.next()) {
                userType = usersTable.getString("User_Type");
            }
            usersTable.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the database");
        }
        return userType;
    }

    public static void addNewUser(String email, String username, String password, UserType user_type) {
        String userType = null;
        Connection c = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO USER(Username, Password, Email, User_Type) VALUES(?, ?, ?, ?)";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, user_type.name());
            statement.executeUpdate();
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText("Success!");
            successAlert.setContentText("Your information has been saved to the database");

            successAlert.showAndWait();

            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong with the database");
        }
    }

    public static void addNewCityOfficial(String username, String title, String city, String state) {
        String userType = null;
        Connection c = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO CITY_OFFICIAL(Username, Title, City, State) VALUES(?, ?, ?, ?)";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, title);
            statement.setString(3, city);
            statement.setString(4, state);
            statement.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong with the database");
        }
    }

    public static void addNewDataPoint(int data_value, Timestamp date_time, Boolean accepted, String loc_name, String type) {
        Connection c = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO DATA_POINT(Data_Value, Date_Time, Accepted, Location_Name, Type) VALUES(?, ?, ?, ?, ?)";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);
            statement.setInt(1, data_value);
            statement.setTimestamp(2, date_time);
            statement.setBoolean(3, accepted);
            statement.setString(4, loc_name);
            statement.setString(5, type);
            statement.executeUpdate();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText("Success!");
            successAlert.setContentText("Your data point has been added");
            successAlert.showAndWait();

            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong with the database");
        }
    }

    public static void addNewLocation(String loc_name, String city, String state, String zip, Boolean flag, Date df) {
        Connection c = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO POI(Location_Name, City, State, Zip_Code, Flag, Date_Flagged) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);
            statement.setString(1, loc_name);
            statement.setString(2, city);
            statement.setString(3, state);
            statement.setString(4, zip);
            statement.setBoolean(5, flag);
            statement.setDate(6, df);
            statement.executeUpdate();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText("Success!");
            successAlert.setContentText("Your data point has been added");
            successAlert.showAndWait();

            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong with the database");
        }
    }

    public static ObservableList<String> getType() {
        ObservableList<String> types = FXCollections.observableArrayList();
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT Type FROM DATA_TYPE";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);
            ResultSet data_types = statement.executeQuery();
            while (data_types.next()) {
                types.add(data_types.getString("Type"));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong with the database");
        }
        return types;
    }
}
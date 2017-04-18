package app.java.model;

import javafx.beans.property.SimpleStringProperty;
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
        boolean valid = false;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email.getText());
        if (!m.matches()) {
            label.setText("Invalid email");
        } else {
            valid = true;
            label.setText("");
        }
        return valid;
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
        } else {
            label.setText("");
        }
        return match;
    }

    public static boolean passwordLength(PasswordField pass, Label label) {
        boolean longEnough = false;
        if (pass.getText().length() < 4) {
            longEnough = false;
            label.setText("Password is too short");
        } else {
            label.setText("");
        }
        return longEnough;
    }

    public static boolean isValidCityState(String city, String state, Label label) {
        //temporary manual check
        boolean validCity = false;

        ArrayList<CityState> city_state_arr = new ArrayList<>();

        CityState cityState = new CityState(new SimpleStringProperty(city), new SimpleStringProperty(state));

        CityState ny_ny = new CityState(new SimpleStringProperty("New York"), new SimpleStringProperty("New York"));
        CityState la_ca = new CityState(new SimpleStringProperty("Los Angeles"), new SimpleStringProperty("California"));
        CityState ch_il = new CityState(new SimpleStringProperty("Chicago"), new SimpleStringProperty("Illinois"));
        CityState ho_tx = new CityState(new SimpleStringProperty("Houston"), new SimpleStringProperty("Texas"));
        CityState ph_pa = new CityState(new SimpleStringProperty("Philadelphia"), new SimpleStringProperty("Pennsylvania"));
        CityState ph_az = new CityState(new SimpleStringProperty("Phoenix"), new SimpleStringProperty("Arizona"));
        CityState sa_tx = new CityState(new SimpleStringProperty("San Antonio"), new SimpleStringProperty("Texas"));
        CityState sd_ca = new CityState(new SimpleStringProperty("San Diego"), new SimpleStringProperty("California"));
        CityState da_tx = new CityState(new SimpleStringProperty("Dallas"), new SimpleStringProperty("Texas"));
        CityState sj_ca = new CityState(new SimpleStringProperty("San Jose"), new SimpleStringProperty("California"));
        CityState au_tx = new CityState(new SimpleStringProperty("Austin"), new SimpleStringProperty("Texas"));
        CityState jk_fl = new CityState(new SimpleStringProperty("Jacksonville"), new SimpleStringProperty("Florida"));
        CityState sf_ca = new CityState(new SimpleStringProperty("San Francisco"), new SimpleStringProperty("California"));
        CityState in_in = new CityState(new SimpleStringProperty("Indianapolis"), new SimpleStringProperty("Indiana"));
        CityState co_oh = new CityState(new SimpleStringProperty("Columbus"), new SimpleStringProperty("Ohio"));
        CityState fw_tx = new CityState(new SimpleStringProperty("Forth Worth"), new SimpleStringProperty("Texas"));
        CityState ch_nc = new CityState(new SimpleStringProperty("Charlotte"), new SimpleStringProperty("North Carolina"));
        CityState se_wa = new CityState(new SimpleStringProperty("Seattle"), new SimpleStringProperty("Washington"));
        CityState de_co = new CityState(new SimpleStringProperty("Denver"), new SimpleStringProperty("Colorado"));
        CityState ep_tx = new CityState(new SimpleStringProperty("El Paso"), new SimpleStringProperty("Texas"));
        CityState at_ga = new CityState(new SimpleStringProperty("Atlanta"), new SimpleStringProperty("Georgia"));

        city_state_arr.addAll(Arrays.asList(ny_ny, la_ca, ch_il, ho_tx, ph_pa, ph_az, sa_tx, sd_ca, da_tx, sj_ca, au_tx, jk_fl, sf_ca,
                in_in, co_oh, fw_tx, ch_nc, se_wa, de_co, ep_tx, at_ga));

        for (CityState i : city_state_arr) {
            if (i.getCity().equals(cityState.getCity()) && i.getState().equals(cityState.getState())) {
                validCity = true;
            }
        }
        return validCity;
    }

    public static boolean isValidLogin(TextField user, PasswordField pass) {
        boolean access_granted = false;
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT Username, Password FROM USER WHERE Username = ? AND Password = ?";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);
            statement.setString(1, user.getText());
            statement.setString(2, pass.getText());

            ResultSet usersTable = statement.executeQuery();

            if (usersTable.next()) {
                access_granted = true;
            }
            usersTable.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the database");
        }
        return access_granted;
    }

    public static boolean isValidZipCode(String zip) {

        boolean validZipCode = false;
        String zipCodePattern = "\\d{5}(-\\d{4})?";

        validZipCode = zip.matches(zipCodePattern);

        return validZipCode;

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
}

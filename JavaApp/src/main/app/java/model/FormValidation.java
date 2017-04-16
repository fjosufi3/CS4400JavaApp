package app.java.model;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;

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

    public static boolean textFieldNotEmpty(TextField t, Label l, String validationText) {
        boolean notEmpty = true;
        String prompt = null;
        if (!textFieldNotEmpty(t)) {
            notEmpty = false;
            prompt = validationText;
        }
        l.setText(prompt);
        return notEmpty;
    }

    public static boolean passwordFieldNotEmpty(PasswordField p) {
        boolean notEmpty = false;
        if (p.getText() != null && !p.getText().isEmpty()) {
            notEmpty = true;
        }
        return notEmpty;
    }

    public static boolean passwordFieldNotEmpty(PasswordField p, Label l, String validationText) {
        boolean notEmpty = true;
        String prompt = null;
        if (!passwordFieldNotEmpty(p)) {
            notEmpty = false;
            prompt = validationText;
        }
        l.setText(prompt);
        return notEmpty;
    }

    public static boolean isValidLogin(String user, String pass) {
        boolean access_granted = false;
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT Username, Password FROM USER WHERE Username = ? AND Password = ?";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);
            statement.setString(1, user);
            statement.setString(2, pass);

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

    public static String getUserType(String user, String pass) {
        String userType = null;
        Connection c = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM USER WHERE Username = ? AND Password = ?";
        try {
            c = ConnectionConfiguration.getConnection();
            statement = c.prepareStatement(query);
            statement.setString(1, user);
            statement.setString(2, pass);

            ResultSet usersTable = statement.executeQuery();

            if (usersTable.next()) {
                userType = usersTable.getString("User_Type");
            }
            usersTable.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            System.out.println("Something with wrong with the database");
        }
        return userType;
    }
}

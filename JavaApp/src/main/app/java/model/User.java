package app.java.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Random;


/**
 * User entity
 *
 * @author mcw0805
 */
public class User {

    private StringProperty email;
    private StringProperty username;
    private StringProperty password;
    private UserType userType;

    public User() {
        email = new SimpleStringProperty(getUsername() + "@gmail.com");
        username = new SimpleStringProperty(generateID());
        password = new SimpleStringProperty("12345");
        userType = UserType.City_Scientist;
    }

    public User(String emailAddress, String userID, String pw, UserType type) {
        email = new SimpleStringProperty(emailAddress);
        username = new SimpleStringProperty(userID);
        password = new SimpleStringProperty(pw);
        userType = type;
    }

    private String generateID() {
        Random randGen = new Random();
        String hex = Integer.toHexString(randGen.nextInt());
        return this.getClass().getName() + "-" + hex;
    }


    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "Email: " + getEmail()
                + "\nUsername: " + getUsername()
                + "\nPassword: " + getPassword();
    }
}

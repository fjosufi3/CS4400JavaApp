package app.java.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Random;

/**
 * City official
 */
public class CityOfficial {

    private StringProperty username;
    private StringProperty city;
    private StringProperty state;
    private StringProperty title;

    public CityOfficial() {
        username = new SimpleStringProperty(generateID());
        city = new SimpleStringProperty("Atlanta");
        state = new SimpleStringProperty("Georgia");
        title = new SimpleStringProperty("Official");
    }

    public CityOfficial(String userID, String ct, String st, String t) {
        username = new SimpleStringProperty(userID);
        city = new SimpleStringProperty(ct);
        state = new SimpleStringProperty(st);
        title = new SimpleStringProperty(t);
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

    public String getCity() { return city.get(); }

    public StringProperty cityProperty() { return city; }

    public void setCity(String city) { this.city.set(city); }

    public String getState() { return state.get(); }

    public StringProperty stateProperty() { return state; }

    public void setState(String state) { this.state.set(state); }

    public String getTitle() { return title.get(); }

    public StringProperty titleProperty() { return title; }

    public void setTitle(String title) { this.title.set(title); }

    @Override
    public String toString() {
        return "Username: " + getUsername()
                + "\nCity: " + getCity()
                + "\nState: " + getState()
                + "\nTitle: " + getTitle();
    }
}

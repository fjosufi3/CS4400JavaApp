package app.java.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * City-State entity
 *
 * @author mcw0805
 */
public class CityState {

    private StringProperty city;
    private StringProperty state;

    public CityState(StringProperty city, StringProperty state) {
        this.city = city;
        this.state = state;
    }


    public CityState(String city, String state) {
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
    }


    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getState() {
        return state.get();
    }

    public StringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public StringProperty getCityState() {
        return new SimpleStringProperty(getCity() + ", " + getState());
    }
}
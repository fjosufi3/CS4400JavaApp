package app.java.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;


public class POI {
    private StringProperty location;
    private StringProperty city;
    private StringProperty state;
    private StringProperty zip;
    private IntegerProperty flag;
    private Date dateFlagged;
    //private DataPoint dataPoint;

    public POI(String location, String city, String state,
               String zip, int flag, Date dateFlagged) {
        this.location = new SimpleStringProperty(location);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.zip = new SimpleStringProperty(zip);
        this.flag = new SimpleIntegerProperty(flag);
        this.dateFlagged = dateFlagged;
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
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

    public String getZip() {
        return zip.get();
    }

    public StringProperty zipProperty() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }

    public int getFlag() {
        return flag.get();
    }

    public IntegerProperty flagProperty() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag.set(flag);
    }

    public Date getDateFlagged() {
        return dateFlagged;
    }

    public void setDateFlagged(Date dateFlagged) {
        this.dateFlagged = dateFlagged;
    }
}
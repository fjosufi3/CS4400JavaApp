package app.java.model;

import javafx.beans.property.*;

import java.sql.Time;
import java.util.Date;


public class DataPoint {

    private BooleanProperty accepted;
    //private IntegerProperty accepted;
    private StringProperty locationName;
    private DataType dataType;
    private IntegerProperty dataValue;
    private Date date;
    private Time time;

    public DataPoint(String locationName, Boolean accepted, DataType dataType,
                     Integer dataValue, Date date, Time time) {
        this.locationName = new SimpleStringProperty(locationName);
        //this.accepted = new SimpleBooleanProperty(accepted);
        this.accepted = new SimpleBooleanProperty(accepted);
        this.dataType = dataType;
        this.dataValue = new SimpleIntegerProperty(dataValue);
        this.date = date;
        this.time = time;
    }

    public boolean isAccepted() {
        return accepted.get();
    }

    public BooleanProperty acceptedProperty() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted.set(accepted);
    }


//    public int getAccepted() {
//        return accepted.get();
//    }
//
//    public IntegerProperty acceptedProperty() {
//        return accepted;
//    }
//
//    public void setAccepted(int accepted) {
//        this.accepted.set(accepted);
//    }

    public String getLocationName() {
        return locationName.get();
    }

    public StringProperty locationNameProperty() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName.set(locationName);
    }


    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public int getDataValue() {
        return dataValue.get();
    }

    public IntegerProperty dataValueProperty() {
        return dataValue;
    }

    public void setDataValue(int dataValue) {
        this.dataValue.set(dataValue);
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public StringProperty getDateTime() {

        return new SimpleStringProperty(date.toString() + " " + time.toString());
    }

    public String getDateTimeString() {
        return getDateTime().get();
    }


}



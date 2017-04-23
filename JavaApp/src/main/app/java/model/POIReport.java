package app.java.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by mcw0805 on 4/23/17.
 */
public class POIReport {

    private StringProperty location;
    private StringProperty city;
    private StringProperty state;
    private IntegerProperty moldMin;
    private IntegerProperty moldAvg;
    private IntegerProperty moldMax;
    private IntegerProperty aqMin;
    private IntegerProperty aqAvg;
    private IntegerProperty aqMax;
    private IntegerProperty flag;

    private POIReport(String loc, String c, String s,
                      int mmin, int mavg, int mmax,
                      int aqmin, int aqavg, int aqmax,
                      int f) {

        location = new SimpleStringProperty(loc);
        city = new SimpleStringProperty(c);
        state = new SimpleStringProperty(s);

        moldMin = new SimpleIntegerProperty(mmin);
        moldAvg = new SimpleIntegerProperty(mavg);
        moldMax = new SimpleIntegerProperty(mmax);
        aqMin = new SimpleIntegerProperty(aqmin);
        aqAvg = new SimpleIntegerProperty(aqavg);
        aqMax = new SimpleIntegerProperty(aqmax);

        flag = new SimpleIntegerProperty(f);

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

    public int getFlag() {
        return flag.get();
    }

    public IntegerProperty flagProperty() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag.set(flag);
    }

    public int getMoldMin() {
        return moldMin.get();
    }

    public IntegerProperty moldMinProperty() {
        return moldMin;
    }

    public void setMoldMin(int moldMin) {
        this.moldMin.set(moldMin);
    }

    public int getMoldAvg() {
        return moldAvg.get();
    }

    public IntegerProperty moldAvgProperty() {
        return moldAvg;
    }

    public void setMoldAvg(int moldAvg) {
        this.moldAvg.set(moldAvg);
    }

    public int getMoldMax() {
        return moldMax.get();
    }

    public IntegerProperty moldMaxProperty() {
        return moldMax;
    }

    public void setMoldMax(int moldMax) {
        this.moldMax.set(moldMax);
    }

    public int getAqMin() {
        return aqMin.get();
    }

    public IntegerProperty aqMinProperty() {
        return aqMin;
    }

    public void setAqMin(int aqMin) {
        this.aqMin.set(aqMin);
    }

    public int getAqAvg() {
        return aqAvg.get();
    }

    public IntegerProperty aqAvgProperty() {
        return aqAvg;
    }

    public void setAqAvg(int aqAvg) {
        this.aqAvg.set(aqAvg);
    }

    public int getAqMax() {
        return aqMax.get();
    }

    public IntegerProperty aqMaxProperty() {
        return aqMax;
    }

    public void setAqMax(int aqMax) {
        this.aqMax.set(aqMax);
    }
}

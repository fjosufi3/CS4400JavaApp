package app.java.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * City Official entity
 *
 * @author mcw0805
 */
public class CityOfficial extends User {

    private StringProperty title;
    private BooleanProperty approval;
    private CityState cityState;
    private StringProperty city;
    private StringProperty state;

    public CityOfficial(String emailAddress, String userID, String pw, UserType type,
                        String title, Boolean approval, CityState cityState) {
        super(emailAddress, userID, pw, type);
        this.title = new SimpleStringProperty(title);
        this.approval = new SimpleBooleanProperty(approval);
        this.cityState = cityState;
        this.city = new SimpleStringProperty(cityState.getCity());
        this.state = new SimpleStringProperty(cityState.getState());

    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public boolean isApproval() {
        return approval.get();
    }

    public BooleanProperty approvalProperty() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval.set(approval);
    }

    public CityState getCityState() {
        return cityState;
    }

    public void setCityState(CityState cityState) {
        this.cityState = cityState;
    }

    public String getCitym() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getState() {
        return state.get();
    }

    public StringProperty stateProperty() {
        return state;
    }
}

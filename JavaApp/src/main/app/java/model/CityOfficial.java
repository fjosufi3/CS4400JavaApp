package app.java.model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by mcw0805 on 4/16/17.
 */
public class CityOfficial extends User {

    private StringProperty title;
    private BooleanProperty approval;
    private CityState cityState;

    public CityOfficial(String emailAddress, String userID, String pw, UserType type,
                        String title, Boolean approval, CityState cityState) {
        super(emailAddress, userID, pw, type);
        this.title = new SimpleStringProperty(title);
        this.approval = new SimpleBooleanProperty(approval);
        this.cityState = cityState;
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
}

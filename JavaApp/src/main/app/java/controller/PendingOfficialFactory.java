package app.java.controller;

import app.java.model.CityOfficial;
import app.java.model.DataPoint;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Created by mcw0805 on 4/16/17.
 */
public class PendingOfficialFactory implements Callback<TableColumn.CellDataFeatures<CityOfficial, CheckBox>, ObservableValue<CheckBox>> {
    @Override
    public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<CityOfficial, CheckBox> param) {
        CityOfficial cityOfficial = param.getValue();
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().setValue(cityOfficial.isApproval());
        checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
            cityOfficial.setApproval(new_val);
        });
        checkBox.setAlignment(Pos.CENTER);
        return new SimpleObjectProperty<>(checkBox);
    }

}

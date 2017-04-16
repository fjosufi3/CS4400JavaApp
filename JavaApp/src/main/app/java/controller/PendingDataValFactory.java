package app.java.controller;

import app.java.model.DataPoint;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Created by mcw0805 on 4/15/17.
 */
public class PendingDataValFactory implements Callback<TableColumn.CellDataFeatures<DataPoint, CheckBox>, ObservableValue<CheckBox>> {
    @Override
    public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<DataPoint, CheckBox> param) {
        DataPoint dataPoint = param.getValue();
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().setValue(dataPoint.isAccepted());
        checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
            dataPoint.setAccepted(new_val);
        });
        checkBox.setAlignment(Pos.CENTER);
        return new SimpleObjectProperty<>(checkBox);
    }


}

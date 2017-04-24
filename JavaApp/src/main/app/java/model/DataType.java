package app.java.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * DataType entity
 *
 * @author mcw0805
 */
public class DataType {
    private StringProperty dataType;

    public DataType(String type) {
        dataType = new SimpleStringProperty(type);

        if (!(getDataType().equalsIgnoreCase("mold")
                || getDataType().equalsIgnoreCase("air quality"))) {
            throw new IllegalArgumentException("not a valid data type");
        }

    }

    public String getDataType() {
        return dataType.get();
    }

    public StringProperty dataTypeProperty() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType.set(dataType);
    }

    @Override
    public String toString() {
        return getDataType();
    }
}

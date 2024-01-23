package org.eulu.probabilisticmodeling.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ApplicationViewModel {
    private final StringProperty upperBound;

    public ApplicationViewModel() {
        this.upperBound = new SimpleStringProperty();
    }

    public StringProperty upperBoundProperty() {
        return upperBound;
    }
}

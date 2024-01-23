package org.eulu.probabilisticmodeling.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ApplicationViewModel {
    private final StringProperty upperBound;
    private final StringProperty groupCount;

    public ApplicationViewModel() {
        this.upperBound = new SimpleStringProperty();
        this.groupCount = new SimpleStringProperty();
    }

    public StringProperty upperBoundProperty() {
        return upperBound;
    }

    public StringProperty groupCountProperty() {
        return groupCount;
    }
}

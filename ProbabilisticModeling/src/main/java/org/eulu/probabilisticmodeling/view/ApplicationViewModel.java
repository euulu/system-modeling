package org.eulu.probabilisticmodeling.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ApplicationViewModel {
    private final StringProperty upperBound;
    private final StringProperty groupCount;
    private final StringProperty generationCount;

    public ApplicationViewModel() {
        this.upperBound = new SimpleStringProperty();
        this.groupCount = new SimpleStringProperty();
        this.generationCount = new SimpleStringProperty();
    }

    public StringProperty upperBoundProperty() {
        return upperBound;
    }

    public StringProperty groupCountProperty() {
        return groupCount;
    }

    public StringProperty generationCountProperty() {
        return generationCount;
    }

    public void importFromExcel() {
        System.out.println("ApplicationViewModel::excelImport");
    }

    public void exportToExcel() {
        System.out.println("ApplicationViewModel::exportToExcel");
    }

    public void generateData() {
        System.out.println("ApplicationViewModel::generateData");
    }
}

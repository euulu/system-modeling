package org.eulu.probabilisticmodeling.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.eulu.probabilisticmodeling.model.NumbersGenerator;

public class ApplicationViewModel {
    private final NumbersGenerator numbersGenerator;
    private final StringProperty upperBound;
    private final StringProperty groupCount;
    private final StringProperty generationCount;
    private final ObservableList<String> generatedNumbers = FXCollections.observableArrayList();

    public ApplicationViewModel(NumbersGenerator numbersGenerator) {
        this.numbersGenerator = numbersGenerator;
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

    public ObservableList<String> getGeneratedNumbers() {
        return generatedNumbers;
    }

    public void importFromExcel() {
        System.out.println("ApplicationViewModel::excelImport");
    }

    public void exportToExcel() {
        System.out.println("ApplicationViewModel::exportToExcel");
    }

    public void generateData() {
        int[] numbers = numbersGenerator.generateNumbers(
                Integer.parseInt(upperBound.getValue()),
                Integer.parseInt(groupCount.getValue()),
                Integer.parseInt(generationCount.getValue())
        );

        setGeneratedNumbers(numbers);
    }

    private void setGeneratedNumbers(int[] numbers) {
        generatedNumbers.clear();
        for (int number : numbers) {
            generatedNumbers.add(String.valueOf(number));
        }
    }
}

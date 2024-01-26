package org.eulu.probabilisticmodeling.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.eulu.probabilisticmodeling.model.NumbersGenerator;

public class ApplicationViewModel {
    private final NumbersGenerator numbersGenerator;
    private final StringProperty upperBound;
    private final StringProperty groupCount;
    private final StringProperty generationCount;
    private final ObservableList<String> generatedNumbers = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Series<String, Integer>> numbersInGroupsCount = FXCollections.observableArrayList();

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

    public ObservableList<XYChart.Series<String, Integer>> getNumbersInGroupsCountProperty() {
        return numbersInGroupsCount;
    }

    public void importFromExcel() {
        System.out.println("ApplicationViewModel::excelImport");
    }

    public void exportToExcel() {
        System.out.println("ApplicationViewModel::exportToExcel");
    }

    public void generateData() {
        int upperBoundInt = Integer.parseInt(upperBound.getValue());
        int groupCountInt = Integer.parseInt(groupCount.getValue());
        int generationCountInt = Integer.parseInt(generationCount.getValue());

        int[] numbers = numbersGenerator.generateNumbers(
                upperBoundInt,
                generationCountInt
        );
        setGeneratedNumbers(numbers);

        int[] numbersInGroupsCount = numbersGenerator.countNumbersInGroups(
                numbers,
                upperBoundInt,
                groupCountInt
        );
        setNumbersInGroupsCount(numbersInGroupsCount);
    }

    private void setGeneratedNumbers(int[] numbers) {
        generatedNumbers.clear();
        for (int number : numbers) {
            generatedNumbers.add(String.valueOf(number));
        }
    }

    private void setNumbersInGroupsCount(int[] numbersCount) {
        numbersInGroupsCount.clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i = 0; i < numbersCount.length; i++) {
            XYChart.Data<String, Integer> data = new XYChart.Data<>(String.valueOf(i), numbersCount[i]);
            Label label = new Label(data.getYValue() + "");
            label.getStyleClass().add("bar-value-label");
            label.setPadding(new Insets(8, 0, 0, 0));
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    StackPane stackPane = (StackPane) newValue;
                    stackPane.setAlignment(Pos.TOP_CENTER);
                    stackPane.getChildren().add(label);
                }
            });
            series.getData().add(data);
        }
        numbersInGroupsCount.add(series);
    }
}

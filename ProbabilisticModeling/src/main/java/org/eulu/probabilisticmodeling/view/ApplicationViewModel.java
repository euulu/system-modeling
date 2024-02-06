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
    private final ObservableList<XYChart.Series<String, Integer>> groupCountStandard = FXCollections.observableArrayList();
    private final ObservableList<String> groupCountLegendStandard = FXCollections.observableArrayList();
    private final ObservableList<String> numbersStandard = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Series<String, Integer>> groupCountMidSquare = FXCollections.observableArrayList();
    private final ObservableList<String> groupCountLegendMidSquare = FXCollections.observableArrayList();
    private final ObservableList<String> numbersMidSquare = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Series<String, Integer>> groupCountLinear = FXCollections.observableArrayList();
    private final ObservableList<String> groupCountLegendLinear = FXCollections.observableArrayList();
    private final ObservableList<String> numbersLinear = FXCollections.observableArrayList();

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

    // Standard
    public ObservableList<XYChart.Series<String, Integer>> getGroupCountStandardProperty() {
        return groupCountStandard;
    }

    public ObservableList<String> getGroupCountLegendStandardProperty() {
        return groupCountLegendStandard;
    }

    public ObservableList<String> getNumbersStandard() {
        return numbersStandard;
    }

    // Middle square
    public ObservableList<XYChart.Series<String, Integer>> getGroupCountMidSquareProperty() {
        return groupCountMidSquare;
    }

    public ObservableList<String> getGroupCountLegendMidSquareProperty() {
        return groupCountLegendMidSquare;
    }

    public ObservableList<String> getNumbersMidSquare() {
        return numbersMidSquare;
    }

    // Linear congruential
    public ObservableList<XYChart.Series<String, Integer>> getGroupCountLinearProperty() {
        return groupCountLinear;
    }

    public ObservableList<String> getGroupCountLegendLinearProperty() {
        return groupCountLegendLinear;
    }

    public ObservableList<String> getNumbersLinear() {
        return numbersLinear;
    }

    public void importFromExcel() {
        System.out.println("ApplicationViewModel::excelImport");
    }

    public int exportToExcel() {
        if (numbersStandard.isEmpty() || numbersMidSquare.isEmpty() || numbersLinear.isEmpty()) {
            // TODO: Show dialog to generate data
            return -1;
        } else {
            // TODO: Export data
            return 0;
        }

    }

    public void generateData() {
        int upperBoundInt = Integer.parseInt(upperBound.getValue());
        int groupCountInt = Integer.parseInt(groupCount.getValue());
        int generationCountInt = Integer.parseInt(generationCount.getValue());

        int[] calculatedNumbersStandard = numbersGenerator.generateNumbersStandard(
                upperBoundInt,
                generationCountInt
        );
        setNumbers(calculatedNumbersStandard, numbersStandard);
        int[] numbersInGroupsCountStandard = numbersGenerator.countNumbersInGroups(
                calculatedNumbersStandard,
                upperBoundInt,
                groupCountInt
        );
        setGroupCountLegend(numbersInGroupsCountStandard, groupCountLegendStandard);
        setGroupCount(numbersInGroupsCountStandard, groupCountStandard);

        int[] calculatedNumbersMidSquare = numbersGenerator.generateNumbersMidSquare(
                upperBoundInt,
                generationCountInt
        );
        setNumbers(calculatedNumbersMidSquare, numbersMidSquare);
        int[] numbersInGroupsCountMidSquare = numbersGenerator.countNumbersInGroups(
                calculatedNumbersMidSquare,
                upperBoundInt,
                groupCountInt
        );
        setGroupCountLegend(numbersInGroupsCountMidSquare, groupCountLegendMidSquare);
        setGroupCount(numbersInGroupsCountMidSquare, groupCountMidSquare);

        int[] calculatedNumbersLinear = numbersGenerator.generateNumbersLinear(
                upperBoundInt,
                generationCountInt
        );
        setNumbers(calculatedNumbersLinear, numbersLinear);
        int[] numbersInGroupsCountLinear = numbersGenerator.countNumbersInGroups(
                calculatedNumbersLinear,
                upperBoundInt,
                groupCountInt
        );
        setGroupCountLegend(numbersInGroupsCountLinear, groupCountLegendLinear);
        setGroupCount(numbersInGroupsCountLinear, groupCountLinear);
    }

    private void setNumbers(int[] numbers, ObservableList<String> numbersList) {
        numbersList.clear();
        for (int number : numbers) {
            numbersList.add(String.valueOf(number));
        }
    }

    private void setGroupCountLegend(int[] numbersInGroupsCount, ObservableList<String> groupCountList) {
        groupCountList.clear();
        for (int number : numbersInGroupsCount) {
            groupCountList.add(String.valueOf(number));
        }
    }

    private void setGroupCount(int[] numbersCount, ObservableList<XYChart.Series<String, Integer>> groupCountList) {
        groupCountList.clear();
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
        groupCountList.add(series);
    }
}

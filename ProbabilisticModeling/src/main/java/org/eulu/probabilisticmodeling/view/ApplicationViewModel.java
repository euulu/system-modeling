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

    public void exportToExcel() {
        System.out.println("ApplicationViewModel::exportToExcel");
    }

    public void generateData() {
        int upperBoundInt = Integer.parseInt(upperBound.getValue());
        int groupCountInt = Integer.parseInt(groupCount.getValue());
        int generationCountInt = Integer.parseInt(generationCount.getValue());

        int[] numbersStandard = numbersGenerator.generateNumbersStandard(
                upperBoundInt,
                generationCountInt
        );
        setNumbersStandard(numbersStandard);
        int[] numbersInGroupsCountStandard = numbersGenerator.countNumbersInGroups(
                numbersStandard,
                upperBoundInt,
                groupCountInt
        );
        setGroupCountLegendStandard(numbersInGroupsCountStandard);
        setGroupCount(numbersInGroupsCountStandard, groupCountStandard);

        int[] numbersMidSquare = numbersGenerator.generateNumbersMidSquare(
                upperBoundInt,
                generationCountInt
        );
        setNumbersMidSquare(numbersMidSquare);
        int[] numbersInGroupsCountMidSquare = numbersGenerator.countNumbersInGroups(
                numbersMidSquare,
                upperBoundInt,
                groupCountInt
        );
        setGroupCountLegendMidSquare(numbersInGroupsCountMidSquare);
        setGroupCount(numbersInGroupsCountMidSquare, groupCountMidSquare);

        int[] numbersLinear = numbersGenerator.generateNumbersLinear(
                upperBoundInt,
                generationCountInt
        );
        setNumbersLinear(numbersLinear);
        int[] numbersInGroupsCountLinear = numbersGenerator.countNumbersInGroups(
                numbersLinear,
                upperBoundInt,
                groupCountInt
        );
        setGroupCountLegendLinear(numbersInGroupsCountLinear);
        setGroupCount(numbersInGroupsCountLinear, groupCountLinear);
    }

    private void setNumbersStandard(int[] numbers) {
        numbersStandard.clear();
        for (int number : numbers) {
            numbersStandard.add(String.valueOf(number));
        }
    }

    private void setGroupCountLegendStandard(int[] numbersInGroupsCount) {
        groupCountLegendStandard.clear();
        for (int number : numbersInGroupsCount) {
            groupCountLegendStandard.add(String.valueOf(number));
        }
    }

    private void setNumbersMidSquare(int[] numbers) {
        numbersMidSquare.clear();
        for (int number : numbers) {
            numbersMidSquare.add(String.valueOf(number));
        }
    }

    private void setGroupCountLegendMidSquare(int[] numbersInGroupsCount) {
        groupCountLegendMidSquare.clear();
        for (int number : numbersInGroupsCount) {
            groupCountLegendMidSquare.add(String.valueOf(number));
        }
    }

    private void setNumbersLinear(int[] numbers) {
        numbersLinear.clear();
        for (int number : numbers) {
            numbersLinear.add(String.valueOf(number));
        }
    }

    private void setGroupCountLegendLinear(int[] numbersInGroupsCount) {
        groupCountLegendLinear.clear();
        for (int number : numbersInGroupsCount) {
            groupCountLegendLinear.add(String.valueOf(number));
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

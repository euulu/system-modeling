package org.eulu.predatorprey;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StatisticsCharts implements Initializable {
    @FXML
    public LineChart<Integer, Integer> quantity;
    @FXML
    public LineChart<Integer, Integer> increase;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quantity.setCreateSymbols(false);
    }

    public void addDataToQuantitySeries(ArrayList<Integer> numberOfAnimalsByEpoch, String seriesName) {
        XYChart.Series<Integer, Integer> animalsSeries = new XYChart.Series<>();
        animalsSeries.setName(seriesName);

        for (int i = 0; i < numberOfAnimalsByEpoch.size(); i++) {
            animalsSeries.getData().add(new XYChart.Data<>(i, numberOfAnimalsByEpoch.get(i)));
        }

        quantity.getData().add(animalsSeries);
    }
}

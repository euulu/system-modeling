package org.eulu.probabilisticmodeling.view;

import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.GridPane;

public class ApplicationViewController {
    @FXML
    public MFXTextField tfUpperBound;
    @FXML
    public MFXTextField tfGroupCount;
    @FXML
    public MFXTextField tfGenerationCount;
    @FXML
    public GridPane gpWrapper;
    @FXML
    public MFXListView<String> lvGeneratedNumbers;
    @FXML
    public BarChart<String, Integer> bcStandardGenerator;

    private ApplicationViewModel applicationViewModel;

    public void init(ApplicationViewModel applicationViewModel) {
        this.applicationViewModel = applicationViewModel;
        tfUpperBound.textProperty().bindBidirectional(applicationViewModel.upperBoundProperty());
        tfGroupCount.textProperty().bindBidirectional(applicationViewModel.groupCountProperty());
        tfGenerationCount.textProperty().bindBidirectional(applicationViewModel.generationCountProperty());
        gpWrapper.requestFocus();
        lvGeneratedNumbers.setItems(applicationViewModel.getGeneratedNumbers());
        bcStandardGenerator.setAnimated(false);
        bcStandardGenerator.setData(applicationViewModel.getNumbersInGroupsCountProperty());
    }

    public void onBtnImport(ActionEvent event) {
        applicationViewModel.importFromExcel();
    }

    public void onBtnExport(ActionEvent event) {
        applicationViewModel.exportToExcel();
    }

    public void onBtnGenerateData(ActionEvent event) {
        applicationViewModel.generateData();
    }
}
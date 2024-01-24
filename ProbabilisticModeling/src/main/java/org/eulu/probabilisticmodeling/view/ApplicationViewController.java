package org.eulu.probabilisticmodeling.view;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    private ApplicationViewModel applicationViewModel;

    public void init(ApplicationViewModel applicationViewModel) {
        this.applicationViewModel = applicationViewModel;
        tfUpperBound.textProperty().bind(applicationViewModel.upperBoundProperty());
        tfGroupCount.textProperty().bind(applicationViewModel.groupCountProperty());
        tfGenerationCount.textProperty().bind(applicationViewModel.generationCountProperty());
        gpWrapper.requestFocus();
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
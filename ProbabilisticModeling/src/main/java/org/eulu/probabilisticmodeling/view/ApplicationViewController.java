package org.eulu.probabilisticmodeling.view;

import io.github.palexdev.materialfx.controls.MFXTextField;
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

    public void init(ApplicationViewModel applicationViewModel) {
        tfUpperBound.textProperty().bind(applicationViewModel.upperBoundProperty());
        tfGroupCount.textProperty().bind(applicationViewModel.groupCountProperty());
        tfGenerationCount.textProperty().bind(applicationViewModel.generationCountProperty());
        gpWrapper.requestFocus();
    }
}
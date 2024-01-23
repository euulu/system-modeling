package org.eulu.probabilisticmodeling.view;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class ApplicationViewController {
    @FXML
    public MFXTextField tfUpperBound;
    @FXML
    public MFXTextField tfGroupCount;
    @FXML
    public GridPane gpWrapper;

    public void init(ApplicationViewModel applicationViewModel) {
        tfUpperBound.textProperty().bind(applicationViewModel.upperBoundProperty());
        tfGroupCount.textProperty().bind(applicationViewModel.groupCountProperty());
        gpWrapper.requestFocus();
    }
}
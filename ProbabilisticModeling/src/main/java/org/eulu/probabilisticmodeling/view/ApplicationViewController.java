package org.eulu.probabilisticmodeling.view;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class ApplicationViewController {
    @FXML
    public MFXTextField tfUpperBound;

    public void init(ApplicationViewModel applicationViewModel) {
        tfUpperBound.textProperty().bind(applicationViewModel.upperBoundProperty());
    }
}
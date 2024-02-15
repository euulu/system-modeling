package org.eulu.predatorpray.view;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class MainStageController {
    @FXML
    public GridPane gpWrapper;
    @FXML
    public MFXTextField tfXSize;
    @FXML
    public MFXTextField tfYSize;
    @FXML
    public MFXTextField tfPreyCount;
    @FXML
    public MFXTextField tfPreyReproductionAge;
    @FXML
    public MFXTextField tfPreyReproductionPeriod;
    @FXML
    public MFXTextField tfPredatorCount;
    @FXML
    public MFXTextField tfPredatorReproductionAge;
    @FXML
    public MFXTextField tfPredatorReproductionPeriod;
    @FXML
    public MFXTextField tfPredatorNoFoodPeriod;

    public void init(MainStageViewModel mainStageViewModel) {
        gpWrapper.requestFocus();
    }
}
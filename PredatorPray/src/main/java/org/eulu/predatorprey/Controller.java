package org.eulu.predatorprey;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Simulation simulation;

    @FXML
    public StackPane spEcosystem;
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
    @FXML
    public Label lblTextFieldError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initValidator(tfXSize, lblTextFieldError);
        this.initValidator(tfYSize, lblTextFieldError);
        this.initValidator(tfPreyCount, lblTextFieldError);
        this.initValidator(tfPreyReproductionAge, lblTextFieldError);
        this.initValidator(tfPreyReproductionPeriod, lblTextFieldError);
        this.initValidator(tfPredatorCount, lblTextFieldError);
        this.initValidator(tfPredatorReproductionAge, lblTextFieldError);
        this.initValidator(tfPredatorReproductionPeriod, lblTextFieldError);
        this.initValidator(tfPredatorNoFoodPeriod, lblTextFieldError);
    }

    private void initValidator(MFXTextField field, Label label) {
        TextFieldValidator digitFieldValidator = new TextFieldValidator(field, label);
        digitFieldValidator.validate();
    }

    public void onBtnCreateBoard() {
        int xSize = Integer.parseInt(tfXSize.getText());
        int ySize = Integer.parseInt(tfYSize.getText());
        int preyCount = Integer.parseInt(tfPreyCount.getText());
        int preyReproductionAge = Integer.parseInt(tfPreyReproductionAge.getText());
        int preyReproductionPeriod = Integer.parseInt(tfPreyReproductionPeriod.getText());
        int predatorCount = Integer.parseInt(tfPredatorCount.getText());
        int predatorReproductionAge = Integer.parseInt(tfPredatorReproductionAge.getText());
        int predatorReproductionPeriod = Integer.parseInt(tfPredatorReproductionPeriod.getText());
        int predatorNoFoodPeriod = Integer.parseInt(tfPredatorNoFoodPeriod.getText());

        simulation = new Simulation(spEcosystem, xSize, ySize, preyCount, preyReproductionAge, preyReproductionPeriod, predatorCount, predatorReproductionAge, predatorReproductionPeriod, predatorNoFoodPeriod);
        simulation.initializeCanvas();
    }

    public void onBtnStep() {
//        simulation.runEpoch();
    }

    public void onBtnStart() {

    }
}
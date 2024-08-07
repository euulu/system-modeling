package org.eulu.predatorprey;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    public Label lblEvolutionStep;
    @FXML
    public Label lblPreyCount;
    @FXML
    public Label lblPredatorCount;
    @FXML
    public Label lblPreyBorn;
    @FXML
    public Label lblPredatorBorn;
    @FXML
    public Label lblPreyDied;
    @FXML
    public Label lblPredatorDied;

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

        this.simulation = new Simulation(spEcosystem, xSize, ySize, preyCount, preyReproductionAge, preyReproductionPeriod, predatorCount, predatorReproductionAge, predatorReproductionPeriod, predatorNoFoodPeriod);
        this.simulation.initializeCanvas();

        createBindings();
    }

    public void onBtnStep() {
        this.simulation.runEpoch();
    }

    public void onBtnStart() {
        this.simulation.start();
    }

    private void createBindings() {
        if (simulation != null) {
            lblEvolutionStep.textProperty().bind(
                    Bindings.createStringBinding(
                            () -> String.valueOf(simulation.getEpochNumber()), simulation.epochNumberProperty()
                    )
            );
            lblPreyCount.textProperty().bind(
                    Bindings.createStringBinding(
                            () -> String.valueOf(simulation.getPreys()), simulation.preysProperty()
                    )
            );
            lblPredatorCount.textProperty().bind(
                    Bindings.createStringBinding(
                            () -> String.valueOf(simulation.getPredators()), simulation.predatorsProperty()
                    )
            );
            lblPreyBorn.textProperty().bind(
                    Bindings.createStringBinding(
                            () -> String.valueOf(simulation.getPreysBorn()), simulation.preysBornProperty()
                    )
            );
            lblPredatorBorn.textProperty().bind(
                    Bindings.createStringBinding(
                            () -> String.valueOf(simulation.getPredatorsBorn()), simulation.predatorsBornProperty()
                    )
            );
            lblPreyDied.textProperty().bind(
                    Bindings.createStringBinding(
                            () -> String.valueOf(simulation.getPreysDied()), simulation.preysDiedProperty()
                    )
            );
            lblPredatorDied.textProperty().bind(
                    Bindings.createStringBinding(
                            () -> String.valueOf(simulation.getPredatorsDied()), simulation.predatorsDiedProperty()
                    )
            );
        }
    }

    public void onBtnStats() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("statistics-charts.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Результат роботи симуляці \"Хижак-Жертва\"");
            stage.setScene(scene);

            StatisticsCharts statChartsController = fxmlLoader.getController();
            statChartsController.addDataToQuantitySeries(simulation.getPreyNumberByEpoch(), "Жертви");
            statChartsController.addDataToQuantitySeries(simulation.getPredatorNumberByEpoch(), "Хижаки");
            statChartsController.addDataToIncreaseSeries(simulation.getPreyIncrease(), "Жертви");
            statChartsController.addDataToIncreaseSeries(simulation.getPredatorIncrease(), "Хижаки");

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

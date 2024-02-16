package org.eulu.predatorpray.view;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

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
    @FXML
    public GridPane gpEcosystem;
    @FXML
    public Label lblPreyCount;
    @FXML
    public Label lblPredatorCount;
    @FXML
    public Label lblEvolutionStep;
    @FXML
    public Label lblPreyBorn;
    @FXML
    public Label lblPredatorBorn;
    @FXML
    public Label lblPreyDied;
    @FXML
    public Label lblPredatorDied;
    @FXML
    public Pane pEcosystemWrap;

    public void init(MainStageViewModel mainStageViewModel) {
        gpWrapper.requestFocus();
        gpEcosystem.layoutXProperty().bind(pEcosystemWrap.widthProperty().subtract(gpEcosystem.widthProperty()).divide(2));
        gpEcosystem.layoutYProperty().bind(pEcosystemWrap.heightProperty().subtract(gpEcosystem.heightProperty()).divide(2));
    }

    public void onBtnStart() {
        int columns = Integer.parseInt(tfXSize.getText());
        int rows = Integer.parseInt(tfYSize.getText());
        double cellSize = columns > rows ? (100.0 / columns) : (100.0 / rows);
        gpEcosystem.getChildren().clear();
        gpEcosystem.getColumnConstraints().clear();
        gpEcosystem.getRowConstraints().clear();
        for (int i = 0; i < columns; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(cellSize);
            gpEcosystem.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < rows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(cellSize);
            gpEcosystem.getRowConstraints().add(rowConstraints);
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Pane cellPane = new Pane();
                cellPane.setStyle("-fx-background-color: white;");
                gpEcosystem.add(cellPane, col, row);
            }
        }
        gpEcosystem.layout();
    }
}
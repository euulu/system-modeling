package org.eulu.predatorpray.view;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.converter.IntegerStringConverter;
import org.eulu.predatorpray.data.Creature;

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

    MainStageViewModel mainStageViewModel;

    public void init(MainStageViewModel mainStageViewModel) {
        this.mainStageViewModel = mainStageViewModel;
        gpWrapper.requestFocus();

        TextFormatter<Integer> xSizeTextFormatter = new TextFormatter<>(new IntegerStringConverter(), 0);
        xSizeTextFormatter.valueProperty().bindBidirectional(mainStageViewModel.columnsProperty().asObject());
        tfXSize.setTextFormatter(xSizeTextFormatter);
        TextFormatter<Integer> ySizeTextFormatter = new TextFormatter<>(new IntegerStringConverter(), 0);
        ySizeTextFormatter.valueProperty().bindBidirectional(mainStageViewModel.rowsProperty().asObject());
        tfYSize.setTextFormatter(ySizeTextFormatter);
        TextFormatter<Integer> preyCountTextFormatter = new TextFormatter<>(new IntegerStringConverter(), 0);
        preyCountTextFormatter.valueProperty().bindBidirectional(mainStageViewModel.preyCountProperty().asObject());
        tfPreyCount.setTextFormatter(preyCountTextFormatter);
        TextFormatter<Integer> predatorCountTextFormatter = new TextFormatter<>(new IntegerStringConverter(), 0);
        predatorCountTextFormatter.valueProperty().bindBidirectional(mainStageViewModel.predatorCountProperty().asObject());
        tfPredatorCount.setTextFormatter(predatorCountTextFormatter);

        tfXSize.textProperty().set("");
        tfYSize.textProperty().set("");
        tfPreyCount.textProperty().set("");
        tfPredatorCount.textProperty().set("");
    }

    public void onBtnStart() {
        gpEcosystem.getChildren().clear();
        gpEcosystem.getColumnConstraints().clear();
        gpEcosystem.getRowConstraints().clear();

        int columns = Integer.parseInt(tfXSize.getText());
        int rows = Integer.parseInt(tfYSize.getText());
        double cellWidth = gpEcosystem.getWidth() / columns;
        double cellHeight = gpEcosystem.getHeight() / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(cellWidth);
                rectangle.setHeight(cellHeight);
                rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.BLACK);
                gpEcosystem.add(rectangle, j, i);
            }
        }

        gpEcosystem.layout();
    }
}
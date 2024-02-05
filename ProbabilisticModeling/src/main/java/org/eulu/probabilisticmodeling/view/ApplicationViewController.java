package org.eulu.probabilisticmodeling.view;

import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

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
    public BarChart<String, Integer> bcStandard;
    @FXML
    public BarChart<String, Integer> bcMidSquare;
    @FXML
    public BarChart<String, Integer> bcLinear;
    @FXML
    public MFXListView<String> lvLegendStandard;
    @FXML
    public MFXListView<String> lvLegendMidSquare;
    @FXML
    public MFXListView<String> lvLegendLinear;
    @FXML
    public MFXListView<String> lvGeneratedNumbersStandard;
    @FXML
    public MFXListView<String> lvGeneratedNumbersMidSquare;
    @FXML
    public MFXListView<String> lvGeneratedNumbersLinear;

    private ApplicationViewModel applicationViewModel;

    public void init(ApplicationViewModel applicationViewModel) {
        this.applicationViewModel = applicationViewModel;
        tfUpperBound.textProperty().bindBidirectional(applicationViewModel.upperBoundProperty());
        tfGroupCount.textProperty().bindBidirectional(applicationViewModel.groupCountProperty());
        tfGenerationCount.textProperty().bindBidirectional(applicationViewModel.generationCountProperty());
        gpWrapper.requestFocus();
        // Standard method
        bcStandard.setData(applicationViewModel.getGroupCountStandardProperty());
        lvLegendStandard.setItems(applicationViewModel.getGroupCountLegendStandardProperty());
        lvLegendStandard.setCellFactory(legendItem -> new LegendCellFactory(lvLegendStandard, legendItem));
        lvGeneratedNumbersStandard.setItems(applicationViewModel.getNumbersStandard());
        lvGeneratedNumbersStandard.setCellFactory(generatedNumber ->
                new GeneratedNumbersCellFactory(lvGeneratedNumbersStandard, generatedNumber));
        // Middle square method
        bcMidSquare.setData(applicationViewModel.getGroupCountMidSquareProperty());
        lvLegendMidSquare.setItems(applicationViewModel.getGroupCountLegendMidSquareProperty());
        lvLegendMidSquare.setCellFactory(legendItem -> new LegendCellFactory(lvLegendMidSquare, legendItem));
        lvGeneratedNumbersMidSquare.setItems(applicationViewModel.getNumbersMidSquare());
        lvGeneratedNumbersMidSquare.setCellFactory(generatedNumber ->
                new GeneratedNumbersCellFactory(lvGeneratedNumbersMidSquare, generatedNumber));
        // Linear congruential method
        bcLinear.setData(applicationViewModel.getGroupCountLinearProperty());
        lvLegendLinear.setItems(applicationViewModel.getGroupCountLegendLinearProperty());
        lvLegendLinear.setCellFactory(legendItem -> new LegendCellFactory(lvLegendLinear, legendItem));
        lvGeneratedNumbersLinear.setItems(applicationViewModel.getNumbersLinear());
        lvGeneratedNumbersLinear.setCellFactory(generatedNumber ->
                new GeneratedNumbersCellFactory(lvGeneratedNumbersLinear, generatedNumber));
    }

    public void onBtnImport() {
        applicationViewModel.importFromExcel();
    }

    public void onBtnExport() {
        applicationViewModel.exportToExcel();
    }

    public void onBtnGenerateData() {
        applicationViewModel.generateData();
    }

    private static class LegendCellFactory extends MFXListCell<String> {
        private final MFXFontIcon squareIcon;

        public LegendCellFactory(MFXListView<String> listView, String data) {
            super(listView, data);
            squareIcon = new MFXFontIcon("fas-square", 12, Color.web("8c57ff"));
            render(data);
        }

        @Override
        protected void render(String data) {
            super.render(data);
            setPrefHeight(24.0);
            if (squareIcon != null) getChildren().addFirst(squareIcon);
        }
    }

    private static class GeneratedNumbersCellFactory extends MFXListCell<String> {
        public GeneratedNumbersCellFactory(MFXListView<String> listView, String data) {
            super(listView, data);
            render(data);
        }

        @Override
        protected void render(String data) {
            super.render(data);
            setPrefHeight(24.0);
        }
    }
}
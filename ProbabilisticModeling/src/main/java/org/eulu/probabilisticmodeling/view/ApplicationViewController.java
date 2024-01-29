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
    public MFXListView<String> lvGeneratedNumbers;
    @FXML
    public BarChart<String, Integer> bcStandardGenerator;
    @FXML
    public MFXListView<String> lvLegendStandard;

    private ApplicationViewModel applicationViewModel;

    public void init(ApplicationViewModel applicationViewModel) {
        this.applicationViewModel = applicationViewModel;
        tfUpperBound.textProperty().bindBidirectional(applicationViewModel.upperBoundProperty());
        tfGroupCount.textProperty().bindBidirectional(applicationViewModel.groupCountProperty());
        tfGenerationCount.textProperty().bindBidirectional(applicationViewModel.generationCountProperty());
        gpWrapper.requestFocus();
        bcStandardGenerator.setData(applicationViewModel.getNumbersInGroupsCountProperty());
        lvLegendStandard.setItems(applicationViewModel.getNumbersInGroupsCountLegendProperty());
        lvLegendStandard.setCellFactory(legendItem -> new LegendCellFactory(lvLegendStandard, legendItem));
        lvGeneratedNumbers.setItems(applicationViewModel.getGeneratedNumbers());
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
            squareIcon = new MFXFontIcon("fas-square", 12);
            squareIcon.setColor(Color.AQUA);
            render(data);
        }

        @Override
        protected void render(String data) {
            super.render(data);
            if (squareIcon != null) getChildren().addFirst(squareIcon);
        }
    }
}
package org.eulu.probabilisticmodeling.view;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.eulu.probabilisticmodeling.view.cellfactory.GeneratedNumbersCellFactory;
import org.eulu.probabilisticmodeling.view.cellfactory.LegendCellFactory;

import java.io.IOException;
import java.util.Map;

import static org.eulu.probabilisticmodeling.util.Constants.*;

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
    private MFXGenericDialog exportDialogContent;
    private MFXStageDialog exportDialog;
    MFXFontIcon errorIcon = new MFXFontIcon("fas-circle-exclamation", 18);

    public void init(ApplicationViewModel applicationViewModel, Stage stage) {
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
        // Initialize export data dialog error
        Platform.runLater(() -> {
            this.exportDialogContent = MFXGenericDialogBuilder.build()
                    .setHeaderText(EXPORT_ERROR_TITLE)
                    .setContentText(EXPORT_ERROR_MESSAGE)
                    .setHeaderIcon(errorIcon)
                    .makeScrollable(true)
                    .setShowClose(false)
                    .setShowMinimize(false)
                    .setShowAlwaysOnTop(false)
                    .get();
            this.exportDialog = MFXGenericDialogBuilder.build(exportDialogContent)
                    .toStageDialogBuilder()
                    .initOwner(stage)
                    .initModality(Modality.APPLICATION_MODAL)
                    .setOwnerNode(gpWrapper)
                    .setScrimPriority(ScrimPriority.WINDOW)
                    .setScrimOwner(true)
                    .get();

            exportDialogContent.addActions(
                    Map.entry(new MFXButton(EXPORT_ERROR_BTN_OK), event -> exportDialog.close())
            );
            exportDialogContent.getStyleClass().add("mfx-error-dialog");
            exportDialogContent.setMaxSize(400, 200);
        });
    }

    public void onBtnImport() {
        applicationViewModel.importFromExcel();
    }

    public void onBtnExport() {
        if (!applicationViewModel.canExportToExcelProperty().get()) {
            exportDialog.showDialog();
        } else {
            try {
                applicationViewModel.exportToExcel();
            } catch (IOException e) {
                exportDialogContent.setHeaderText(EXPORT_FILE_ERROR_TITLE);
                exportDialogContent.setContentText(EXPORT_FILE_ERROR_MESSAGE);
                exportDialog.showDialog();
            }
        }
    }

    public void onBtnGenerateData() {
        applicationViewModel.generateData();
    }
}
package org.eulu.predatorprey;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Controller {
    private static final int MIN_SIZE = 572;

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
    public MFXTextField tfPredatorCount;
    @FXML
    public MFXTextField tfPredatorReproductionAge;

    public void onBtnStart() {
        Canvas canvas = new Canvas(MIN_SIZE, MIN_SIZE);
        spEcosystem.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawGrid(canvas, gc);
    }

    private void drawGrid(Canvas canvas, GraphicsContext gc) {
        int numColumns = Integer.parseInt(tfXSize.getText());
        int numRows = Integer.parseInt(tfYSize.getText());
        int preyCount = Integer.parseInt(tfPreyCount.getText());
        int predatorCount = Integer.parseInt(tfPredatorCount.getText());

        float cellSize = numColumns > numRows ? (float) MIN_SIZE / numColumns : (float) MIN_SIZE / numRows;

        if (numColumns > numRows) {
            canvas.setWidth(MIN_SIZE);
            canvas.setHeight(numRows * cellSize);
        } else {
            canvas.setWidth(numColumns * cellSize);
            canvas.setHeight(MIN_SIZE);
        }

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }

        gc.setLineWidth(0.5f);
        gc.setStroke(Color.GRAY);
        for (int i = 0; i <= numColumns; i++) {
            gc.strokeLine(i * cellSize, 0, i * cellSize, canvas.getHeight());
        }

        for (int i = 0; i <= numRows; i++) {
            gc.strokeLine(0, i * cellSize, canvas.getWidth(), i * cellSize);
        }

        fillRandomCells(AnimalType.PREY, gc, preyCount, cellSize);
        fillRandomCells(AnimalType.PREDATOR, gc, predatorCount, cellSize);
    }

    private void fillRandomCells(AnimalType animalType, GraphicsContext gc, int numCells, double cellSize) {
        int numColumns = Integer.parseInt(tfXSize.getText());
        int numRows = Integer.parseInt(tfYSize.getText());
        int preyReproductionAge = Integer.parseInt(tfPreyReproductionAge.getText());
        int predatorReproductionAge = Integer.parseInt(tfPredatorReproductionAge.getText());
        Set<String> occupiedCells = new HashSet<>();
        Random random = new Random();

        while (numCells > 0) {
            int col = random.nextInt(numColumns);
            int row = random.nextInt(numRows);
            String cellKey = col + "," + row;


            if (!occupiedCells.contains(cellKey)) {
                Animal animal;
                if (animalType == AnimalType.PREY) {
                    animal = new Prey(col, row, preyReproductionAge);
                    colorCell(gc, animal, cellSize);
                }
                if (animalType == AnimalType.PREDATOR) {
                    animal = new Predator(col, row, predatorReproductionAge);
                    colorCell(gc, animal, cellSize);
                }
                occupiedCells.add(cellKey);
                numCells--;
            }
        }
    }

    private void colorCell(GraphicsContext gc, Animal animal, double cellSize) {
        double x = animal.getX() * cellSize;
        double y = animal.getY() * cellSize;

        gc.setFill(animal.getColor());
        gc.fillRect(x, y, cellSize, cellSize);
    }
}
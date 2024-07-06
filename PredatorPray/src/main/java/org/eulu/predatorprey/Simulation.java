package org.eulu.predatorprey;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.*;

import static org.eulu.predatorprey.Constants.DIRECTIONS;
import static org.eulu.predatorprey.Constants.MIN_SIZE;

public class Simulation {
    private final StackPane parent;
    private final Canvas canvas;
    private final int xSize;
    private final int ySize;
    private final float cellSize;
    private final int preyCount;
    private final int preyReproductionAge;
    private final int predatorCount;
    private final int predatorReproductionAge;
    private Animal[][] board;

    public Simulation(StackPane parent, int xSize, int ySize, int preyCount, int preyReproductionAge, int predatorCount, int predatorReproductionAge) {
        this.canvas = new Canvas(MIN_SIZE, MIN_SIZE);
        this.parent = parent;
        this.xSize = xSize;
        this.ySize = ySize;
        this.cellSize = this.xSize > this.ySize ? (float) MIN_SIZE / this.xSize : (float) MIN_SIZE / this.ySize;
        this.preyCount = preyCount;
        this.preyReproductionAge = preyReproductionAge;
        this.predatorCount = predatorCount;
        this.predatorReproductionAge = predatorReproductionAge;
        this.board = new Animal[xSize][ySize];
    }

    public void initializeCanvas() {
        if (!this.parent.getChildren().isEmpty()) {
            this.parent.getChildren().clear();
        }
        this.parent.getChildren().add(this.canvas);

        if (this.xSize > this.ySize) {
            this.canvas.setWidth(MIN_SIZE);
            this.canvas.setHeight(this.ySize * this.cellSize);
        } else {
            this.canvas.setWidth(this.xSize * this.cellSize);
            this.canvas.setHeight(MIN_SIZE);
        }

        this.drawBoard();

        fillRandomCells(AnimalType.PREY, this.preyCount);
        fillRandomCells(AnimalType.PREDATOR, this.predatorCount);
    }

    private void drawBoard() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();

        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                switch (this.board[x][y]) {
                    case Prey prey -> {
                        if (prey.getAge() <= prey.getReproductionAge()) {
                            g.setFill(prey.getYangColor());
                        } else {
                            g.setFill(prey.getOldColor());
                        }
                    }
                    case Predator predator -> {
                        if (predator.getAge() <= predator.getReproductionAge()) {
                            g.setFill(predator.getYangColor());
                        } else {
                            g.setFill(predator.getOldColor());
                        }
                    }
                    case null, default -> g.setFill(Color.LIGHTGRAY);
                }
                g.fillRect(x * this.cellSize, y * this.cellSize, this.cellSize, this.cellSize);
            }
        }

        g.setLineWidth(0.5f);
        g.setStroke(Color.GRAY);
        for (int x = 0; x <= this.xSize; x++) {
            g.strokeLine(x * this.cellSize, 0, x * this.cellSize, this.canvas.getHeight());
        }

        for (int y = 0; y <= this.ySize; y++) {
            g.strokeLine(0, y * this.cellSize, this.canvas.getWidth(), y * this.cellSize);
        }
    }

    private void fillRandomCells(AnimalType animalType, int numCells) {
        Set<String> occupiedCells = new HashSet<>();
        Random random = new Random();

        while (numCells > 0) {
            int x = random.nextInt(this.xSize);
            int y = random.nextInt(this.ySize);
            String cellKey = x + "," + y;

            if (!occupiedCells.contains(cellKey)) {
                switch (animalType) {
                    case PREY -> {
                        Prey prey = new Prey(x, y, this.preyReproductionAge);
                        this.board[x][y] = prey;
                        colorCell(prey);
                    }
                    case PREDATOR -> {
                        Predator predator = new Predator(x, y, this.predatorReproductionAge);
                        this.board[x][y] = predator;
                        colorCell(predator);
                    }
                }
                occupiedCells.add(cellKey);
                numCells--;
            }
        }
    }

    private void colorCell(Animal animal) {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        double x = animal.getX() * this.cellSize;
        double y = animal.getY() * this.cellSize;

        g.setFill(animal.getYangColor());
        g.fillRect(x, y, this.cellSize, this.cellSize);
    }

    public void runEpoch() {
        List<Animal> animalsToMove = new ArrayList<>();
        Animal[][] newBoard = new Animal[xSize][ySize];
        Random random = new Random();

        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                if (this.board[x][y] != null) {
                    animalsToMove.add(this.board[x][y]);
                }
            }
        }

        for (Animal animal : animalsToMove) {
            animal.incrementAge();
            int x = animal.getX();
            int y = animal.getY();

            List<int[]> emptyCells = new ArrayList<>();
            for (int[] direction : DIRECTIONS) {
                int newX = (x + direction[0] + xSize) % xSize;
                int newY = (y + direction[1] + ySize) % ySize;
                if (newBoard[newX][newY] == null) {
                    emptyCells.add(new int[]{newX, newY});
                }
            }

            if (!emptyCells.isEmpty()) {
                int newPosition = random.nextInt(emptyCells.size());
                int newX = emptyCells.get(newPosition)[0];
                int newY = emptyCells.get(newPosition)[1];
                newBoard[newX][newY] = animal;
                animal.setX(newX);
                animal.setY(newY);
            } else {
                newBoard[x][y] = animal;
            }
        }

        this.board = newBoard;
        drawBoard();
    }
}

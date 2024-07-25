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
    private final int preyReproductionPeriod;
    private final int predatorCount;
    private final int predatorReproductionAge;
    private final int predatorReproductionPeriod;
    private final int predatorNoFoodPeriod;
    private Animal[][] board;
    private boolean[][] tempBoard;
    private int epochNumber;

    public Simulation(StackPane parent, int xSize, int ySize, int preyCount, int preyReproductionAge, int preyReproductionPeriod, int predatorCount, int predatorReproductionAge, int predatorReproductionPeriod, int predatorNoFoodPeriod) {
        this.canvas = new Canvas(MIN_SIZE, MIN_SIZE);
        this.parent = parent;
        this.xSize = xSize;
        this.ySize = ySize;
        this.cellSize = this.xSize > this.ySize ? (float) MIN_SIZE / this.xSize : (float) MIN_SIZE / this.ySize;
        this.preyCount = preyCount;
        this.preyReproductionAge = preyReproductionAge;
        this.preyReproductionPeriod = preyReproductionPeriod;
        this.predatorCount = predatorCount;
        this.predatorReproductionAge = predatorReproductionAge;
        this.predatorReproductionPeriod = predatorReproductionPeriod;
        this.predatorNoFoodPeriod = predatorNoFoodPeriod;
        this.board = new Animal[xSize][ySize];
        this.epochNumber = 0;
    }

    public void initializeCanvas() {
        if (!this.parent.getChildren().isEmpty()) {
            this.parent.getChildren().clear();
        }

        if (this.xSize > this.ySize) {
            this.canvas.setWidth(MIN_SIZE);
            this.canvas.setHeight(this.ySize * this.cellSize);
        } else {
            this.canvas.setWidth(this.xSize * this.cellSize);
            this.canvas.setHeight(MIN_SIZE);
        }

        this.addAnimalsOnBoardInit(Prey.class, this.preyCount);
        this.addAnimalsOnBoardInit(Predator.class, this.predatorCount);

        this.drawAnimalsOnBoard();

        this.parent.getChildren().add(this.canvas);
    }

    private void addAnimalsOnBoardInit(Class<? extends Animal> animalClass, int numCells) {
        Random random = new Random();
        Animal newAnimal = null;

        while (numCells > 0) {
            int x = random.nextInt(this.xSize);
            int y = random.nextInt(this.ySize);

            if (this.board[x][y] == null) {
                if (animalClass == Prey.class) {
                    newAnimal = new Prey(x, y, this.preyReproductionAge, this.preyReproductionPeriod);
                } else if (animalClass == Predator.class) {
                    newAnimal = new Predator(x, y, this.predatorReproductionAge, this.predatorReproductionPeriod);
                }
                this.board[x][y] = newAnimal;
                numCells--;
            }
        }
    }

    private void drawAnimalsOnBoard() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();

        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                Animal animal = this.board[x][y];
                if (animal instanceof Prey) {
                    if (animal.getAge() <= animal.getReproductionAge()) {
                        g.setFill(animal.getYangColor());
                    } else {
                        g.setFill(animal.getOldColor());
                    }
                } else if (animal instanceof Predator) {
                    if (animal.getAge() <= animal.getReproductionAge()) {
                        g.setFill(animal.getYangColor());
                    } else {
                        g.setFill(animal.getOldColor());
                    }
                } else {
                    g.setFill(Color.LIGHTGRAY);
                }
                g.fillRect(y * this.cellSize, x * this.cellSize, this.cellSize, this.cellSize);
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

    public void runEpoch() {
        this.epochNumber++;
        this.tempBoard = new boolean[this.xSize][this.ySize];
        movePreys();
        movePredators();
        this.drawAnimalsOnBoard();
    }

    private void movePreys() {
        List<Prey> preysList = getAnimalsOfType(Prey.class);
        Collections.shuffle(preysList);

        for (Prey prey : preysList) {
            moveToEmpty(prey);
        }
    }

    private void movePredators() {
        List<Predator> predatorsList = getAnimalsOfType(Predator.class);
        Collections.shuffle(predatorsList);

        for (Predator predator : predatorsList) {
            moveToEmpty(predator);
        }
    }

    private void moveToEmpty(Animal animal) {
        int oldX = animal.getX();
        int oldY = animal.getY();
        List<int[]> emptyNeighbors = getEmptyNeighbors(oldX, oldY);

        if (!emptyNeighbors.isEmpty()) {
            int[] newCoordinates = emptyNeighbors.get(new Random().nextInt(emptyNeighbors.size()));
            this.board[oldX][oldY] = null;
            animal.setX(newCoordinates[0]);
            animal.setY(newCoordinates[1]);
            this.board[newCoordinates[0]][newCoordinates[1]] = animal;
            this.tempBoard[newCoordinates[0]][newCoordinates[1]] = true;
        } else {
            this.tempBoard[oldX][oldY] = true;
        }
    }

    private <T extends Animal> List<T> getAnimalsOfType(Class<T> animalType) {
        List<T> animalsList = new ArrayList<>();

        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                if (animalType.isInstance(this.board[x][y])) {
                    animalsList.add(animalType.cast(this.board[x][y]));
                }
            }
        }

        return animalsList;
    }

    private List<int[]> getEmptyNeighbors(int x, int y) {
        List<int[]> emptyNeighbors = new ArrayList<>();

        for (int[] dir : DIRECTIONS) {
            int newX = (x + dir[0] + this.xSize) % this.xSize;
            int newY = (y + dir[1] + this.ySize) % this.ySize;
            if (this.board[newX][newY] == null && !this.tempBoard[newX][newY]) {
                emptyNeighbors.add(new int[]{newX, newY});
            }
        }

        return emptyNeighbors;
    }
}

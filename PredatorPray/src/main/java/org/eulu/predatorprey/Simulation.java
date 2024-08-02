package org.eulu.predatorprey;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    private final Animal[][] board;
    private final Timeline timeline;
    private boolean isSimulationPlaying = false;

    private final IntegerProperty epochNumber;
    private final IntegerProperty preys;
    private final IntegerProperty predators;
    private final IntegerProperty preyBorn;

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
        this.board = new Animal[ySize][xSize];

        this.epochNumber = new SimpleIntegerProperty(0);
        this.preys = new SimpleIntegerProperty(preyCount);
        this.predators = new SimpleIntegerProperty(predatorCount);
        this.preyBorn = new SimpleIntegerProperty(0);

        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), actionEvent -> this.runEpoch()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void initializeCanvas() {
        this.timeline.stop();

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
            int y = random.nextInt(this.ySize);
            int x = random.nextInt(this.xSize);

            if (this.board[y][x] == null) {
                if (animalClass == Prey.class) {
                    newAnimal = new Prey(y, x, this.preyReproductionAge, this.preyReproductionPeriod);
                } else if (animalClass == Predator.class) {
                    newAnimal = new Predator(y, x, this.predatorReproductionAge, this.predatorReproductionPeriod, this.predatorNoFoodPeriod);
                }
                this.board[y][x] = newAnimal;
                numCells--;
            }
        }
    }

    private void drawAnimalsOnBoard() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        int preysOnBoard = 0;
        int predatorsOnBoard = 0;

        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                Animal animal = this.board[y][x];
                g.setFill(Color.LIGHTGRAY);
                if (animal instanceof Prey) {
                    preysOnBoard++;
                    if (animal.getAge() < animal.getReproductionAge()) {
                        g.setFill(animal.getYangColor());
                    } else {
                        g.setFill(animal.getOldColor());
                    }
                } else if (animal instanceof Predator) {
                    predatorsOnBoard++;
                    if (animal.getAge() < animal.getReproductionAge()) {
                        g.setFill(animal.getYangColor());
                    } else {
                        g.setFill(animal.getOldColor());
                    }
                }
                g.fillRect(x * this.cellSize, y * this.cellSize, this.cellSize, this.cellSize);
            }
        }

        this.preys.set(preysOnBoard);
        this.predators.set(predatorsOnBoard);

        g.setLineWidth(0.5f);
        g.setStroke(Color.GRAY);
        for (int x = 0; x <= this.xSize; x++) {
            g.strokeLine(x * this.cellSize, 0, x * this.cellSize, this.canvas.getHeight());
        }

        for (int y = 0; y <= this.ySize; y++) {
            g.strokeLine(0, y * this.cellSize, this.canvas.getWidth(), y * this.cellSize);
        }
    }

    public void start() {
        if (isSimulationPlaying) {
            this.timeline.pause();
        } else {
            this.timeline.play();
        }
        isSimulationPlaying = !isSimulationPlaying;
    }

    public void runEpoch() {
        this.epochNumber.set(this.epochNumber.get() + 1);
        this.movePreys();
        this.movePredators();
        this.reproduce();
        this.removeDead();
        this.drawAnimalsOnBoard();
    }

    private void movePreys() {
        List<Prey> preysList = getAnimalsOfType(Prey.class);
        Collections.shuffle(preysList);

        for (Prey prey : preysList) {
            prey.incrementAge();
            this.moveToEmptyCell(prey);
        }
    }

    private void movePredators() {
        List<Predator> predatorsList = getAnimalsOfType(Predator.class);
        Collections.shuffle(predatorsList);

        for (Predator predator : predatorsList) {
            predator.incrementAge();
            this.moveToEat(predator);
        }
    }

    private void moveToEmptyCell(Animal animal) {
        int oldY = animal.getY();
        int oldX = animal.getX();
        List<int[]> emptyNeighbors = getEmptyNeighbors(oldY, oldX);

        if (!emptyNeighbors.isEmpty()) {
            int[] newCoordinates = emptyNeighbors.get(new Random().nextInt(emptyNeighbors.size()));
            this.board[oldY][oldX] = null;
            animal.setY(newCoordinates[0]);
            animal.setX(newCoordinates[1]);
            this.board[newCoordinates[0]][newCoordinates[1]] = animal;
        }
    }

    private void moveToEat(Predator predator) {
        int oldY = predator.getY();
        int oldX = predator.getX();
        List<int[]> preyNeighbors = getNeighborsToEat(oldY, oldX);

        if (!preyNeighbors.isEmpty()) {
            int[] preyCoordinates = preyNeighbors.get(new Random().nextInt(preyNeighbors.size()));
            this.board[oldY][oldX] = null;
            predator.setY(preyCoordinates[0]);
            predator.setX(preyCoordinates[1]);
            predator.eat();
            this.board[preyCoordinates[0]][preyCoordinates[1]] = predator;
        } else {
            predator.increaseHunger();
            this.moveToEmptyCell(predator);
        }
    }

    private <T extends Animal> List<T> getAnimalsOfType(Class<T> animalType) {
        List<T> animalsList = new ArrayList<>();

        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                if (animalType.isInstance(this.board[y][x])) {
                    animalsList.add(animalType.cast(this.board[y][x]));
                }
            }
        }

        return animalsList;
    }

    private List<int[]> getEmptyNeighbors(int y, int x) {
        List<int[]> emptyNeighbors = new ArrayList<>();

        for (int[] dir : DIRECTIONS) {
            int newY = (y + dir[0] + this.ySize) % this.ySize;
            int newX = (x + dir[1] + this.xSize) % this.xSize;
            if (this.board[newY][newX] == null) {
                emptyNeighbors.add(new int[]{newY, newX});
            }
        }

        return emptyNeighbors;
    }

    private List<int[]> getNeighborsToEat(int y, int x) {
        List<int[]> preyNeighbors = new ArrayList<>();

        for (int[] dir : DIRECTIONS) {
            int newY = (y + dir[0] + this.ySize) % this.ySize;
            int newX = (x + dir[1] + this.xSize) % this.xSize;
            if (this.board[newY][newX] instanceof Prey) {
                preyNeighbors.add(new int[]{newY, newX});
            }
        }

        return preyNeighbors;
    }

    private void reproduce() {
        List<Animal> newborns = getAllNewborns();

        for (Animal newborn : newborns) {
            List<int[]> emptyNeighbors = getEmptyNeighbors(newborn.getY(), newborn.getX());
            if (!emptyNeighbors.isEmpty()) {
                int[] newPos = emptyNeighbors.get(new Random().nextInt(emptyNeighbors.size()));
                int y = newPos[0];
                int x = newPos[1];
                this.board[y][x] = newborn;
                if (newborn instanceof Prey) {
                    this.preyBorn.set(this.preyBorn.get() + 1);
                }
            }
        }
    }

    private List<Animal> getAllNewborns() {
        List<Animal> newborns = new ArrayList<>();

        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                if (this.board[y][x] != null) {
                    Animal animal = this.board[y][x];
                    if (animal.canReproduce()) {
                        Animal newborn = animal.reproduce();
                        if (newborn != null) {
                            newborns.add(newborn);
                        }
                    }
                }
            }
        }

        return newborns;
    }

    private void removeDead() {
        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                if (this.board[y][x] instanceof Predator predator && predator.isDead()) {
                    this.board[y][x] = null;
                }
            }
        }
    }

    public int getEpochNumber() {
        return epochNumber.get();
    }

    public IntegerProperty epochNumberProperty() {
        return epochNumber;
    }

    public int getPreys() {
        return preys.get();
    }

    public IntegerProperty preysProperty() {
        return preys;
    }

    public int getPredators() {
        return predators.get();
    }

    public IntegerProperty predatorsProperty() {
        return predators;
    }

    public int getPreyBorn() {
        return preyBorn.get();
    }

    public IntegerProperty preyBornProperty() {
        return preyBorn;
    }
}

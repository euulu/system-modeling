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
    private final Animal[][] board;
    private boolean[][] tempBoard;
    private int epochNumber;
    private List<Predator> predatorsList;

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
        this.tempBoard = new boolean[ySize][xSize];
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
            int y = random.nextInt(this.ySize);
            int x = random.nextInt(this.xSize);

            if (this.board[y][x] == null) {
                if (animalClass == Prey.class) {
                    newAnimal = new Prey(x, y, this.preyReproductionAge, this.preyReproductionPeriod);
                } else if (animalClass == Predator.class) {
                    newAnimal = new Predator(x, y, this.predatorReproductionAge, this.predatorReproductionPeriod, this.predatorNoFoodPeriod);
                }
                this.board[y][x] = newAnimal;
                numCells--;
            }
        }
    }

    private void drawAnimalsOnBoard() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();

        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                Animal animal = this.board[y][x];
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
                g.fillRect(x * this.cellSize, y * this.cellSize, this.cellSize, this.cellSize);
                g.setFill(Color.BLACK);
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
        for (int y = 0; y < this.ySize; y++) {
            Arrays.fill(this.tempBoard[y], false);
        }

        this.epochNumber++;
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
            this.moveToEmptyCell(prey);
        }
    }

    private void movePredators() {
        this.predatorsList = getAnimalsOfType(Predator.class);
        Collections.shuffle(this.predatorsList);

        for (Predator predator : this.predatorsList) {
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
            this.tempBoard[oldY][oldX] = false;
            animal.setY(newCoordinates[0]);
            animal.setX(newCoordinates[1]);
            this.board[newCoordinates[0]][newCoordinates[1]] = animal;
            this.tempBoard[newCoordinates[0]][newCoordinates[1]] = true;
        } else {
            this.tempBoard[oldY][oldX] = true;
        }
    }

    private void moveToEat(Predator predator) {
        int oldY = predator.getY();
        int oldX = predator.getX();
        List<int[]> preyNeighbors = getNeighborsToEat(oldY, oldX);

        if (!preyNeighbors.isEmpty()) {
            int[] preyCoordinates = preyNeighbors.get(new Random().nextInt(preyNeighbors.size()));
            this.board[oldY][oldX] = null;
            this.tempBoard[oldY][oldX] = false;
            predator.setY(preyCoordinates[0]);
            predator.setX(preyCoordinates[1]);
            predator.eat();
            this.board[preyCoordinates[0]][preyCoordinates[1]] = predator;
            this.tempBoard[preyCoordinates[0]][preyCoordinates[1]] = true;
        } else {
            this.moveToEmptyCell(predator);
            predator.increaseHunger();
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
            int newY = (y + dir[1] + this.ySize) % this.ySize;
            int newX = (x + dir[0] + this.xSize) % this.xSize;
            if (this.board[newY][newX] == null && !this.tempBoard[newY][newX]) {
                emptyNeighbors.add(new int[]{newY, newX});
            }
        }

        return emptyNeighbors;
    }

    private List<int[]> getNeighborsToEat(int y, int x) {
        List<int[]> preyNeighbors = new ArrayList<>();

        for (int[] dir : DIRECTIONS) {
            int newY = (y + dir[1] + this.ySize) % this.ySize;
            int newX = (x + dir[0] + this.xSize) % this.xSize;
            if (this.board[newY][newX] instanceof Prey && this.tempBoard[newY][newX]) {
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
                this.tempBoard[y][x] = true;
            }
        }
    }

    private List<Animal> getAllNewborns() {
        List<Animal> newborns = new ArrayList<>();

        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                if (this.board[y][x] != null) {
                    Animal animal = this.board[y][x];
                    animal.incrementAge();
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
        for (Predator predator : this.predatorsList) {
            int y = predator.getY();
            int x = predator.getX();

            if (predator.isDead()) {
                this.board[y][x] = null;
                this.tempBoard[y][x] = false;
            }
        }
    }
}

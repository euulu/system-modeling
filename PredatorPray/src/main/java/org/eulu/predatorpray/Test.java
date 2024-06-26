package org.eulu.predatorpray;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int GRID_SIZE = 20; // Size of each grid cell
    private static final int NUM_PREDATORS = 10;
    private static final int NUM_PREY = 50;

    private List<Prey> preyList;
    private List<Predator> predatorList;
    private Canvas canvas;
    private Random random;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        preyList = new ArrayList<>();
        predatorList = new ArrayList<>();
        random = new Random();

        // Initialize prey and predators
        for (int i = 0; i < NUM_PREY; i++) {
            preyList.add(new Prey(random.nextInt(WIDTH), random.nextInt(HEIGHT)));
        }

        for (int i = 0; i < NUM_PREDATORS; i++) {
            predatorList.add(new Predator(random.nextInt(WIDTH), random.nextInt(HEIGHT)));
        }

        canvas = new Canvas(WIDTH, HEIGHT);
        draw();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            update();
            draw();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        primaryStage.setScene(new Scene(new StackPane(canvas)));
        primaryStage.setTitle("Predator Prey Simulation");
        primaryStage.show();
    }

    private void update() {
        // Move prey
        for (Prey prey : preyList) {
            prey.move(WIDTH, HEIGHT);

            // Check for predator collisions
            for (Predator predator : predatorList) {
                if (predator.isColliding(prey)) {
                    preyList.remove(prey);
                    break;
                }
            }
        }

        // Move predators
        for (Predator predator : predatorList) {
            predator.move(WIDTH, HEIGHT, preyList);

            // Predator reproduction logic (optional)
            // ...
        }
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Draw grid (optional)
        /*
        for (int i = 0; i <= WIDTH; i += GRID_SIZE) {
            gc.strokeLine(i, 0, i, HEIGHT);
        }
        for (int i = 0; i <= HEIGHT; i += GRID_SIZE) {
            gc.strokeLine(0, i, WIDTH, i);
        }
        */

        // Draw prey as green circles
        for (Prey prey : preyList) {
            gc.setFill(Color.GREEN);
            gc.fillOval(prey.getX() - GRID_SIZE / 2, prey.getY() - GRID_SIZE / 2, GRID_SIZE, GRID_SIZE);
        }

        // Draw predators as red circles
        for (Predator predator : predatorList) {
            gc.setFill(Color.RED);
            gc.fillOval(predator.getX() - GRID_SIZE / 2, predator.getY() - GRID_SIZE / 2, GRID_SIZE, GRID_SIZE);
        }
    }

    class Prey {
        private int x;
        private int y;

        public Prey(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void move(int width, int height) {
            int moveX = random.nextInt(3) - 1; // Random movement between -1, 0, 1
            int moveY = random.nextInt(3) - 1;
            x += moveX * GRID_SIZE;
            y += moveY * GRID_SIZE;

            // Wrap around the edges of the world
            if (x < 0) {
                x = width;
            } else if (x >= width) {
                x = 0;
            }

            if (y < 0) {
                y = height;
            } else if (y >= height) {
                y = 0;
            }
        }
    }

    class Predator {
        private int x;
        private int y;

        public Predator(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean isColliding(Prey prey) {
            double distance = Math.sqrt(Math.pow(x - prey.getX(), 2) + Math.pow(y - prey.getY(), 2));
            return distance <= GRID_SIZE; // Check if within grid size distance
        }

        public void move(int width, int height, List<Prey> preyList) {
            Prey closestPrey = null;
            double closestDistance = Double.MAX_VALUE;

            // Find closest prey
            for (Prey prey : preyList) {
                double distance = Math.sqrt(Math.pow(x - prey.getX(), 2) + Math.pow(y - prey.getY(), 2));
                if (distance < closestDistance) {
                    closestPrey = prey;
                    closestDistance = distance;
                }
            }

            // Move towards closest prey if found
            if (closestPrey != null) {
                int moveX = (int) Math.signum(closestPrey.getX() - x);
                int moveY = (int) Math.signum(closestPrey.getY() - y);

                x += moveX * GRID_SIZE;
                y += moveY * GRID_SIZE;
            } else {
                // Move randomly if no prey found
                int moveX = random.nextInt(3) - 1;
                int moveY = random.nextInt(3) - 1;

                x += moveX * GRID_SIZE;
                y += moveY * GRID_SIZE;
            }

            // Wrap around the edges of the world
            if (x < 0) {
                x = width;
            } else if (x >= width) {
                x = 0;
            }

            if (y < 0) {
                y = height;
            } else if (y >= height) {
                y = 0;
            }
        }
    }
}

package org.eulu.predatorprey;

import javafx.scene.paint.Color;

public class Predator extends Animal {
    private final int predatorNoFoodPeriod;
    private int hunger;

    public Predator(int x, int y, int reproductionAge, int reproductionPeriod, int predatorNoFoodPeriod) {
        super(x, y, reproductionAge, reproductionPeriod);
        this.predatorNoFoodPeriod = predatorNoFoodPeriod;
        this.hunger = 0;
        this.setYangColor(Color.web("#ff795b"));
        this.setOldColor(Color.web("#ff200c"));
    }

    public void eat() {
        this.hunger = 0;
    }

    public void increaseHunger() {
        this.hunger++;
    }

    public boolean isDead() {
        return this.hunger >= this.predatorNoFoodPeriod;
    }
}

package org.eulu.predatorprey;

import javafx.scene.paint.Color;

public class Predator extends Animal {
    private final int predatorNoFoodPeriod;
    private int hunger;

    public Predator(int y, int x, int reproductionAge, int reproductionPeriod, int predatorNoFoodPeriod) {
        super(y, x, reproductionAge, reproductionPeriod);
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

    @Override
    public Animal reproduce() {
        this.lastReproduction = this.age;
        return new Predator(this.getY(), this.getX(), this.reproductionAge, this.reproductionPeriod, this.predatorNoFoodPeriod);
    }
}

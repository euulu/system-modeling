package org.eulu.predatorprey;

import javafx.scene.paint.Color;

public class Prey extends Animal {

    public Prey(int y, int x, int reproductionAge, int reproductionPeriod) {
        super(y, x, reproductionAge, reproductionPeriod);
        this.setYangColor(Color.web("#60b9ff"));
        this.setOldColor(Color.web("#007add"));
    }

    @Override
    public Animal reproduce() {
        this.lastReproduction = this.age;
        return new Prey(this.getY(), this.getX(), this.reproductionAge, this.reproductionPeriod);
    }
}

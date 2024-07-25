package org.eulu.predatorprey;

import javafx.scene.paint.Color;

public class Prey extends Animal {

    public Prey(int x, int y, int reproductionAge, int reproductionPeriod) {
        super(x, y, reproductionAge, reproductionPeriod);
        this.setYangColor(Color.web("#60b9ff"));
        this.setOldColor(Color.web("#007add"));
    }

    @Override
    public Animal reproduce() {
        this.lastReproduction = this.age;
        return new Prey(this.getX(), this.getY(), this.reproductionAge, this.reproductionPeriod);
    }
}

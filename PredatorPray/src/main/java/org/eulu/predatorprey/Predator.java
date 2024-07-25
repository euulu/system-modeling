package org.eulu.predatorprey;

import javafx.scene.paint.Color;

import java.util.Random;

public class Predator extends Animal {

    public Predator(int x, int y, int reproductionAge, int reproductionPeriod) {
        super(x, y, reproductionAge, reproductionPeriod);
        this.setYangColor(Color.web("#ff795b"));
        this.setOldColor(Color.web("#ff200c"));
    }
}

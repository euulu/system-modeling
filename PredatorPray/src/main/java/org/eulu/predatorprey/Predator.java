package org.eulu.predatorprey;

import javafx.scene.paint.Color;

import java.util.Random;

public class Predator extends Animal {
    public Predator(int x, int y, int reproductionAge) {
        super(x, y, reproductionAge);
        Random random = new Random();
        this.setAge(random.nextInt(reproductionAge));
        this.setYangColor(Color.web("#ff795b"));
        this.setOldColor(Color.web("#ff200c"));
    }
}

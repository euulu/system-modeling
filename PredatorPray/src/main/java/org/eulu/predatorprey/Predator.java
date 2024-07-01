package org.eulu.predatorprey;

import javafx.scene.paint.Color;

import java.util.Random;

public class Predator extends Animal{
//    #ff200c
//    ff795b
    public Predator(int x, int y, int reproductionAge) {
        super(x, y, reproductionAge);
        Random random = new Random();
        this.setAge(random.nextInt(reproductionAge));
        this.setColor(Color.web("#ff200c"));
    }
}

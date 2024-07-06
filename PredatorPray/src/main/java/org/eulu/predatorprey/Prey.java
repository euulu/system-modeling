package org.eulu.predatorprey;

import javafx.scene.paint.Color;

public class Prey extends Animal {
    public Prey(int x, int y, int reproductionAge) {
        super(x, y, reproductionAge);
        this.setYangColor(Color.web("#60b9ff"));
        this.setOldColor(Color.web("#007add"));
    }
}

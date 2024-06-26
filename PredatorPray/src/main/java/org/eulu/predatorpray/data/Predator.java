package org.eulu.predatorpray.data;

public class Predator extends Creature {
    private final String color;

    public Predator(int xPos, int yPos) {
        super(xPos, yPos);
        color = "red";
    }

    @Override
    public String getColor() {
        return color;
    }
}

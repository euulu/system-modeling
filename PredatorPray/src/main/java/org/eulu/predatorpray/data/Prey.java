package org.eulu.predatorpray.data;

public class Prey extends Creature {
    private final String color;

    public Prey(int xPos, int yPos) {
        super(xPos, yPos);
        color = "green";
    }

    @Override
    public String getColor() {
        return color;
    }
}

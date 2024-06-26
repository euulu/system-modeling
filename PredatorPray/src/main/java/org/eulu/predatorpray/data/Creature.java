package org.eulu.predatorpray.data;

public class Creature {
    private final int xPos;
    private final int yPos;
    private int xPosOld;
    private int yPosOld;
    private final int age;

    public Creature(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.age = 0;
    }

    public void move() {}

    public void eat() {}

    public void giveBirth() {}

    public void die() {}

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getXPosOld() {
        return xPosOld;
    }

    public void setXPosOld(int xPosOld) {
        this.xPosOld = xPosOld;
    }

    public int getYPosOld() {
        return yPosOld;
    }

    public void setYPosOld(int yPosOld) {
        this.yPosOld = yPosOld;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return "white";
    }
}

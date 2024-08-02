package org.eulu.predatorprey;

import javafx.scene.paint.Color;

import java.util.Random;

public abstract class Animal {
    private int x;
    private int y;
    protected int age;
    protected int reproductionAge;
    protected int reproductionPeriod;
    protected int lastReproduction;
    private Color yangColor;
    private Color oldColor;

    public Animal(int y, int x, int reproductionAge, int reproductionPeriod) {
        Random random = new Random();
        this.y = y;
        this.x = x;
        this.reproductionAge = reproductionAge;
        this.age = random.nextInt(this.reproductionAge);
        this.reproductionPeriod = reproductionPeriod;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAge() {
        return age;
    }

    public int getReproductionAge() {
        return reproductionAge;
    }

    public void incrementAge() {
        this.age++;
    }

    public Color getYangColor() {
        return yangColor;
    }

    public void setYangColor(Color yangColor) {
        this.yangColor = yangColor;
    }

    public Color getOldColor() {
        return oldColor;
    }

    public void setOldColor(Color oldColor) {
        this.oldColor = oldColor;
    }

    public boolean canReproduce() {
        return (this.age >= this.reproductionAge) && ((this.age - this.lastReproduction) >= this.reproductionPeriod);
    }

    public abstract Animal reproduce();
}

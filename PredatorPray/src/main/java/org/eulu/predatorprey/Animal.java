package org.eulu.predatorprey;

import javafx.scene.paint.Color;

import java.util.Random;

public class Animal {
    private int x;
    private int y;
    private int age;
    private int reproductionAge;
    private int reproductionPeriod;
    private Color yangColor;
    private Color oldColor;

    public Animal(int x, int y, int reproductionAge, int reproductionPeriod) {
        Random random = new Random();
        this.x = x;
        this.y = y;
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

    public void setAge(int age) {
        this.age = age;
    }

    public int getReproductionAge() {
        return reproductionAge;
    }

    public void setReproductionAge(int reproductionAge) {
        this.reproductionAge = reproductionAge;
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

    public int getReproductionPeriod() {
        return reproductionPeriod;
    }

    public void setReproductionPeriod(int reproductionPeriod) {
        this.reproductionPeriod = reproductionPeriod;
    }
}

package org.eulu.predatorprey;

public class Constants {
    public static final int MIN_SIZE = 572;
    public static final String[] DIGITS = "0 1 2 3 4 5 6 7 8 9".split(" ");
    public static final String LENGTH_ERR_MSG = "Поля не можуть бути пустими";
    public static final String DIGIT_ERR_MSG = "Поля можуть містити тільки числа";
    public static final int[][] DIRECTIONS = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};
}

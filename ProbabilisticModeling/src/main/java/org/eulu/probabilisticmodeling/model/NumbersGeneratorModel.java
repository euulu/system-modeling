package org.eulu.probabilisticmodeling.model;

import java.util.concurrent.ThreadLocalRandom;

public class NumbersGeneratorModel implements NumbersGenerator {
    @Override
    public int[] generateNumbers(int upperBound, int groupCount, int generationCount) {
        int[] generatedNumbers = new int[generationCount];
        for (int i = 0; i < generationCount; i++) {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, upperBound + 1);
            generatedNumbers[i] = randomNumber;
        }

        return generatedNumbers;
    }
}

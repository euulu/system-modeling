package org.eulu.probabilisticmodeling.model;

import java.util.concurrent.ThreadLocalRandom;

public class NumbersGeneratorModel implements NumbersGenerator {
    @Override
    public int[] generateNumbersStandard(int upperBound, int generationCount) {
        int[] generatedNumbers = new int[generationCount];
        for (int i = 0; i < generationCount; i++) {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, upperBound);
            generatedNumbers[i] = randomNumber;
        }

        return generatedNumbers;
    }

    @Override
    public int[] generateNumbersMidSquare(int upperBound, int generationCount) {
        int[] generatedNumbers = new int[generationCount];
        int seed = 1234;
        for (int i = 0; i < generationCount; i++) {
            String squaredSeed = String.format("%08d", (seed * seed));
            int randomNumber = Integer.parseInt(squaredSeed.substring(2, 6));
            seed = randomNumber;
            generatedNumbers[i] = randomNumber % upperBound;
        }

        return generatedNumbers;
    }

    @Override
    public int[] countNumbersInGroups(int[] numbers, int upperBound, int groupCount) {
        int[] counter = new int[groupCount];
        int chunkSize = upperBound / groupCount;
        for (int i = 0; i < groupCount; i++) {
            for (int number : numbers) {
                if (i * chunkSize <= number && number < (i + 1) * chunkSize) {
                    counter[i]++;
                }
            }
        }

        return counter;
    }


}

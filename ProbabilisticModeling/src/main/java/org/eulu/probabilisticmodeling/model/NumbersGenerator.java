package org.eulu.probabilisticmodeling.model;

public interface NumbersGenerator {
    int[] generateNumbers(int upperBound, int groupCount, int generationCount);

    int[] countNumbersInGroups(int[] numbers, int upperBound, int groupCount);
}

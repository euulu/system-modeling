package org.eulu.probabilisticmodeling.model;

public interface NumbersGenerator {
    int[] generateNumbersStandard(int upperBound, int generationCount);

    int[] generateNumbersMidSquare(int upperBound, int generationCount);

    int[] generateNumbersLinear(int upperBound, int generationCount);

    int[] countNumbersInGroups(int[] numbers, int upperBound, int groupCount);
}

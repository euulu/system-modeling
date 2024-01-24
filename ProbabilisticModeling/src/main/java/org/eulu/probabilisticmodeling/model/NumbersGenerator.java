package org.eulu.probabilisticmodeling.model;

public interface NumbersGenerator {
    int[] generateNumbers(int upperBound, int groupCount, int generationCount);
}

package org.eulu.probabilisticmodeling.core;

import org.eulu.probabilisticmodeling.model.NumbersGenerator;
import org.eulu.probabilisticmodeling.model.NumbersGeneratorModel;

public class ModelFactory {
    private NumbersGenerator numbersGeneratorModel;

    public NumbersGenerator getNumbersGeneratorModel() {
        if (numbersGeneratorModel == null) {
            numbersGeneratorModel = new NumbersGeneratorModel();
        }
        return numbersGeneratorModel;
    }
}

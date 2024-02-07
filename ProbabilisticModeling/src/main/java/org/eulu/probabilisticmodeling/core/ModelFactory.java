package org.eulu.probabilisticmodeling.core;

import org.eulu.probabilisticmodeling.model.DataIOManager;
import org.eulu.probabilisticmodeling.model.DataIOManagerModel;
import org.eulu.probabilisticmodeling.model.NumbersGenerator;
import org.eulu.probabilisticmodeling.model.NumbersGeneratorModel;

public class ModelFactory {
    private NumbersGenerator numbersGeneratorModel;
    private DataIOManager dataIOManagerModel;

    public NumbersGenerator getNumbersGeneratorModel() {
        if (numbersGeneratorModel == null) {
            numbersGeneratorModel = new NumbersGeneratorModel();
        }
        return numbersGeneratorModel;
    }

    public DataIOManager getDataIOManagerModel() {
        if (dataIOManagerModel == null) {
            dataIOManagerModel = new DataIOManagerModel();
        }
        return dataIOManagerModel;
    }
}

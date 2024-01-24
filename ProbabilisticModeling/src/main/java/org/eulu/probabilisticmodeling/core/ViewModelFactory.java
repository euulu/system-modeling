package org.eulu.probabilisticmodeling.core;

import org.eulu.probabilisticmodeling.view.ApplicationViewModel;

public class ViewModelFactory {
    private final ModelFactory modelFactory;

    public ViewModelFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    public ApplicationViewModel getApplicationViewModel() {
        return new ApplicationViewModel(modelFactory.getNumbersGeneratorModel());
    }
}

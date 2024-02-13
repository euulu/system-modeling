package org.eulu.predatorpray.core;

import org.eulu.predatorpray.view.MainStageViewModel;

public class ViewModelFactory {
    private final ModelFactory modelFactory;

    public ViewModelFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    public MainStageViewModel getMainStageViewModel() {
        return new MainStageViewModel();
    }
}

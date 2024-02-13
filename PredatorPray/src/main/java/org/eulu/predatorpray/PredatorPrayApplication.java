package org.eulu.predatorpray;

import javafx.application.Application;
import javafx.stage.Stage;
import org.eulu.predatorpray.core.ModelFactory;
import org.eulu.predatorpray.core.ViewHandler;
import org.eulu.predatorpray.core.ViewModelFactory;

public class PredatorPrayApplication extends Application {
    @Override
    public void start(Stage stage) {
        ModelFactory modelFactory = new ModelFactory();
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = new ViewHandler(stage, viewModelFactory);
        viewHandler.start();
    }
}
package org.eulu.probabilisticmodeling;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eulu.probabilisticmodeling.core.ViewHandler;

import java.io.IOException;

public class ProbabilisticModelingApplication extends Application {
    @Override
    public void start(Stage stage) {
        ViewHandler viewHandler = new ViewHandler();
        viewHandler.start();
    }
}
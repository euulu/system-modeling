package org.eulu.probabilisticmodeling.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eulu.probabilisticmodeling.ProbabilisticModelingApplication;

import java.io.IOException;

public class ViewHandler {
    private final Stage stage;

    public ViewHandler(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        openApplicationView();
    }

    public void openApplicationView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    ProbabilisticModelingApplication.class.getResource("application-view.fxml")
            );
            Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
            stage.setTitle("Імовірнісне моделювання(Луцюк Є.В. - ЛР№2)");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

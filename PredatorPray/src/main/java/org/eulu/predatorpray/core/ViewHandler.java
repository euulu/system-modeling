package org.eulu.predatorpray.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eulu.predatorpray.PredatorPrayApplication;
import org.eulu.predatorpray.view.MainStageController;

import java.io.IOException;

public class ViewHandler {
    private final Stage stage;
    private final ViewModelFactory viewModelFactory;

    public ViewHandler(Stage stage, ViewModelFactory viewModelFactory) {
        this.stage = stage;
        this.viewModelFactory = viewModelFactory;
    }

    public void start() {
        openApplicationView();
    }

    public void openApplicationView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    PredatorPrayApplication.class.getResource("main-stage.fxml")
            );
            Scene scene = new Scene(fxmlLoader.load());
            MainStageController mainStageController = fxmlLoader.getController();
            mainStageController.init(viewModelFactory.getMainStageViewModel());
            stage.setTitle("Імітаційне моделювання(Луцюк Є.В. - ЛР№3)");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.eulu.probabilisticmodeling.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ApplicationViewController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
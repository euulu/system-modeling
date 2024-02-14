module org.eulu.predatorpray {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires VirtualizedFX;

    opens org.eulu.predatorpray to javafx.fxml;
    exports org.eulu.predatorpray;
    exports org.eulu.predatorpray.view;
    opens org.eulu.predatorpray.view to javafx.fxml;
}
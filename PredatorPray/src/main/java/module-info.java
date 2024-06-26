module org.eulu.predatorpray {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires VirtualizedFX;

    exports org.eulu.predatorpray;
    opens org.eulu.predatorpray to javafx.fxml;
}
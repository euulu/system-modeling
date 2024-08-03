module org.eulu.predatorpray {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires VirtualizedFX;

    exports org.eulu.predatorprey;
    opens org.eulu.predatorprey to javafx.fxml;
}
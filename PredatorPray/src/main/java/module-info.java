module org.eulu.predatorpray {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires VirtualizedFX;

    exports org.eulu.predatorpray;
    opens org.eulu.predatorpray to javafx.fxml;
    exports org.eulu.predatorpray.view;
    opens org.eulu.predatorpray.view to javafx.fxml;
    exports org.eulu.predatorpray.data;
    opens org.eulu.predatorpray.data to javafx.fxml;
}
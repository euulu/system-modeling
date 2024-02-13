module org.eulu.predatorpray {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.eulu.predatorpray to javafx.fxml;
    exports org.eulu.predatorpray;
}
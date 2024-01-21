module org.eulu.probabilisticmodeling {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires VirtualizedFX;

    opens org.eulu.probabilisticmodeling to javafx.fxml;
    exports org.eulu.probabilisticmodeling;
    exports org.eulu.probabilisticmodeling.view;
    opens org.eulu.probabilisticmodeling.view to javafx.fxml;
}
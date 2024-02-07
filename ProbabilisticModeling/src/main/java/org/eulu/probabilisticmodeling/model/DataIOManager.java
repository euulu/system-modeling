package org.eulu.probabilisticmodeling.model;

import javafx.collections.ObservableList;
import javafx.stage.Window;

import java.io.IOException;

public interface DataIOManager {
    void exportToFile(
            Window window,
            ObservableList<String> groupCountLegendStandard,
            ObservableList<String> numbersStandard,
            ObservableList<String> groupCountLegendMidSquare,
            ObservableList<String> numbersMidSquare,
            ObservableList<String> groupCountLegendLinear,
            ObservableList<String> numbersLinear
    ) throws IOException;
}

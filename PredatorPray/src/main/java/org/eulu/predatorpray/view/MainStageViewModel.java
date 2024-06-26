package org.eulu.predatorpray.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.eulu.predatorpray.data.Creature;
import org.eulu.predatorpray.data.Predator;
import org.eulu.predatorpray.data.Prey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainStageViewModel {
    private final IntegerProperty columns;
    private final IntegerProperty rows;
    private final IntegerProperty preyCount;
    private final IntegerProperty predatorCount;
    private final ObservableList<Creature> ecosystemCreatures;

    public MainStageViewModel() {
        columns = new SimpleIntegerProperty();
        rows = new SimpleIntegerProperty();
        preyCount = new SimpleIntegerProperty();
        predatorCount = new SimpleIntegerProperty();
        ecosystemCreatures = FXCollections.observableArrayList();
    }

    public int getColumns() {
        return columns.get();
    }

    public IntegerProperty columnsProperty() {
        return columns;
    }

    public int getRows() {
        return rows.get();
    }

    public IntegerProperty rowsProperty() {
        return rows;
    }

    public int getPreyCount() {
        return preyCount.get();
    }

    public IntegerProperty preyCountProperty() {
        return preyCount;
    }

    public int getPredatorCount() {
        return predatorCount.get();
    }

    public IntegerProperty predatorCountProperty() {
        return predatorCount;
    }

    public ObservableList<Creature> getEcosystemCreatures() {
        return ecosystemCreatures;
    }

    public void initEcosystemStructure() {
        for (int i = 0; i < getPreyCount() + getPreyCount(); i++) {
            ecosystemCreatures.add(null);
        }
//        for (int row = 0; row < getRows(); row++) {
//            ObservableList<Creature> rowList = FXCollections.observableArrayList();
//            for (int col = 0; col < getColumns(); col++) {
//                rowList.add(null);
//            }
//            ecosystemGridItems.add(rowList);
//        }
    }

    public void populateEcosystemWithCreatures() {
        List<int[]> emptyCells = new ArrayList<>();
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                emptyCells.add(new int[]{row, col});
            }
        }
        Collections.shuffle(emptyCells);

        for (int i = 0; i < getPreyCount(); i++) {
            if (!emptyCells.isEmpty()) {
                int[] position = emptyCells.removeFirst();
                ecosystemCreatures.add(new Prey(position[0], position[1]));
            }
        }

        for (int i = 0; i < getPredatorCount(); i++) {
            if (!emptyCells.isEmpty()) {
                int[] position = emptyCells.removeFirst();
                ecosystemCreatures.add(new Predator(position[0], position[1]));
            }
        }

    }
}

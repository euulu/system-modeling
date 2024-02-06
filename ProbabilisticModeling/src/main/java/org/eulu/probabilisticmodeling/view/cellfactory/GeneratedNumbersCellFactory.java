package org.eulu.probabilisticmodeling.view.cellfactory;

import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;

public class GeneratedNumbersCellFactory extends MFXListCell<String> {
    public GeneratedNumbersCellFactory(MFXListView<String> listView, String data) {
        super(listView, data);
        render(data);
    }

    @Override
    protected void render(String data) {
        super.render(data);
        setPrefHeight(24.0);
    }
}

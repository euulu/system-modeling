package org.eulu.probabilisticmodeling.view.cellfactory;

import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.scene.paint.Color;

public class LegendCellFactory extends MFXListCell<String> {
    private final MFXFontIcon squareIcon;

    public LegendCellFactory(MFXListView<String> listView, String data) {
        super(listView, data);
        squareIcon = new MFXFontIcon("fas-square", 12, Color.web("8c57ff"));
        render(data);
    }

    @Override
    protected void render(String data) {
        super.render(data);
        setPrefHeight(24.0);
        if (squareIcon != null) getChildren().addFirst(squareIcon);
    }
}
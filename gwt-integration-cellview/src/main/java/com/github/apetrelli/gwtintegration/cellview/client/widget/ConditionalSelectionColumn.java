package com.github.apetrelli.gwtintegration.cellview.client.widget;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.SelectionModel;

public abstract class ConditionalSelectionColumn<T> extends
        SelectionColumn<T> {

    public ConditionalSelectionColumn(Cell<Boolean> cell, SelectionModel<T> selectionModel) {
        super(cell, selectionModel);
    }

    @Override
    public void render(Context context, T object,
            SafeHtmlBuilder sb) {
        if (isRenderable(object)) {
            super.render(context, object, sb);
        }
    }

    public abstract boolean isRenderable(T object);
}

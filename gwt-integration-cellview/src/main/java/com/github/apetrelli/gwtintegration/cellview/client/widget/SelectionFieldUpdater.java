package com.github.apetrelli.gwtintegration.cellview.client.widget;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.view.client.SelectionModel;

public class SelectionFieldUpdater<T> implements FieldUpdater<T, Boolean> {

    private SelectionModel<T> selectionModel;

    public SelectionFieldUpdater(SelectionModel<T> selectionModel) {
        this.selectionModel = selectionModel;
    }

    @Override
    public void update(int index, T object, Boolean value) {
        selectionModel.setSelected(object, value);
    }

}

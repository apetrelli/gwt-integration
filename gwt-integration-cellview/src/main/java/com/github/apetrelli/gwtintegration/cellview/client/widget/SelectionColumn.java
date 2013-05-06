package com.github.apetrelli.gwtintegration.cellview.client.widget;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.SelectionModel;

public class SelectionColumn<T> extends Column<T, Boolean> {

	private SelectionModel<T> selectionModel;

	public SelectionColumn(Cell<Boolean> cell, SelectionModel<T> selectionModel) {
		super(cell);
		this.selectionModel = selectionModel;
	}

	@Override
	public Boolean getValue(T object) {
		return selectionModel.isSelected(object);
	}

}
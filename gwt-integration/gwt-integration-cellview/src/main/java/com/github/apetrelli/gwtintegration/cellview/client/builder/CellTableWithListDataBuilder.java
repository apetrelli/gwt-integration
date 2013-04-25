package com.github.apetrelli.gwtintegration.cellview.client.builder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

public class CellTableWithListDataBuilder<T> {

	private ProvidesKey<T> keyProvider;
	
	private ListDataProvider<T> dataProvider;
	
	private AbstractPager pager;
	
	private CellPreviewEvent.Handler<T> cellPreviewHandler;
	
	private RowStyles<T> rowStyles;
	
	private SelectionModel<T> selectionModel;
	
	private CellPreviewEvent.Handler<T> selectionEventManager;
	
	private List<String> columnTitles;
	
	private List<Column<T, ?>> columns;
	
	private List<Comparator<T>> comparators;
	
	private CellTable<T> dataTable;
	
	private CellTableWithListDataBuilder() {
		columnTitles = new ArrayList<String>();
		columns = new ArrayList<Column<T,?>>();
		comparators = new ArrayList<Comparator<T>>();
	}
	
	public static <T> CellTableWithListDataBuilder<T> create() {
		return new CellTableWithListDataBuilder<T>();
	}
	
	public CellTableWithListDataBuilder<T> setKeyProvider(ProvidesKey<T> keyProvider) {
		this.keyProvider = keyProvider;
		return this;
	}
	
	public CellTableWithListDataBuilder<T> setDataProvider(ListDataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
		return this;
	}
	
	public CellTableWithListDataBuilder<T> setPager(AbstractPager pager) {
		this.pager = pager;
		return this;
	}
	
	public CellTableWithListDataBuilder<T> setCellPreviewHandler(
			CellPreviewEvent.Handler<T> cellPreviewHandler) {
		this.cellPreviewHandler = cellPreviewHandler;
		return this;
	}
	
	public CellTableWithListDataBuilder<T> setRowStyles(RowStyles<T> rowStyles) {
		this.rowStyles = rowStyles;
		return this;
	}
	
	public CellTableWithListDataBuilder<T> setSelectionModel(SelectionModel<T> selectionModel) {
		this.selectionModel = selectionModel;
		return this;
	}
	
	public CellTableWithListDataBuilder<T> setSelectionEventManager(
			CellPreviewEvent.Handler<T> selectionEventManager) {
		this.selectionEventManager = selectionEventManager;
		return this;
	}
	
	public CellTableWithListDataBuilder<T> addColumn(String title, Column<T, ?> column, Comparator<T> comparator) {
		columnTitles.add(title);
		columns.add(column);
		comparators.add(comparator);
		return this;
	}
	
	public CellTableWithListDataBuilder<T> addColumn(String title, Column<T, ?> column) {
		return addColumn(title, column, null);
	}
	
	public CellTable<T> construct() {
		dataTable = new CellTable<T>(keyProvider);
		return dataTable;
	}
	
	public void connect() {
		Iterator<String> titleIt = columnTitles.iterator();
		Iterator<Column<T, ?>> columnIt = columns.iterator();
		Iterator<Comparator<T>> comparatorIt = comparators.iterator();
		ListHandler<T> handler = new ListHandler<T>(dataProvider.getList());
		while (titleIt.hasNext()) {
			String title = titleIt.next();
			Column<T, ?> column = columnIt.next();
			Comparator<T> comparator = comparatorIt.next();
			boolean sortable = comparator != null;
			column.setSortable(sortable);
			dataTable.addColumn(column, title);
			if (sortable) {
				handler.setComparator(column, comparator);
			}
		}
		dataProvider.addDataDisplay(dataTable);
		pager.setDisplay(dataTable);
		dataTable.setRowStyles(rowStyles);
		dataTable.addCellPreviewHandler(cellPreviewHandler);
		dataTable.addColumnSortHandler(handler);
		if (selectionModel != null) {
			dataTable.setSelectionModel(selectionModel, selectionEventManager);
		}
		dataProvider.flush();
	}
}

package com.github.apetrelli.gwtintegration.cellview.client.widget;

import java.util.List;
import java.util.Set;

import com.github.apetrelli.gwtintegration.cellview.client.builder.CellTableWithListDataBuilder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SetSelectionModel;

public abstract class PagedTable<T> extends Composite {

    private ListDataProvider<T> dataProvider;

    private Integer defaultSortColumnIndex;

    private CellTable<T> dataTable;

    @UiConstructor
    public PagedTable(CellTableWithListDataBuilder<T> tableBuilder) {
        dataProvider = tableBuilder.getDataProvider();
        dataTable = tableBuilder.construct();
        setDataTable(dataTable);
        initWidget();
        tableBuilder.setPager(getPager()).connect();
    }

    public void setPageSize(int pageSize) {
        getPager().setPageSize(pageSize);
    }

    protected abstract void initWidget();

    protected abstract void setDataTable(CellTable<T> dataTable);

    protected abstract SimplePager getPager();

    public void setDefaultSortColumnIndex(Integer defaultSortColumnIndex) {
        this.defaultSortColumnIndex = defaultSortColumnIndex;
    }

    public void setItems(List<T> persons) {
        List<T> list = dataProvider.getList();
        list.clear();
        if (persons != null) {
            list.addAll(persons);
        }
        SetSelectionModel<T> selectionModel = getSelectionModel();
        if (selectionModel != null) {
            selectionModel.clear();
        }
        dataProvider.flush();
        ColumnSortList sortList = dataTable.getColumnSortList();
        sortList.clear();
        if (defaultSortColumnIndex != null) {
            sortList.push(dataTable.getColumn(defaultSortColumnIndex));
        }

        getPager().setDisplay(dataTable);
        getPager().setPage(0);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private SetSelectionModel<T> getSelectionModel() {
        SelectionModel selectionModel = dataTable.getSelectionModel();
        if (selectionModel == null) {
            return null;
        }
        if (selectionModel instanceof SetSelectionModel) {
            return (SetSelectionModel) selectionModel;
        }
        throw new IllegalStateException("The selection model is not a SetSelectionModel");
    }

    public Set<T> getSelectedItems() {
        SetSelectionModel<T> selectionModel = getSelectionModel();
        return selectionModel != null ? selectionModel.getSelectedSet() : null;
    }
}

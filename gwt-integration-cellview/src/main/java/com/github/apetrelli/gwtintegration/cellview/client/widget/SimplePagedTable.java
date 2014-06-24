package com.github.apetrelli.gwtintegration.cellview.client.widget;

import com.github.apetrelli.gwtintegration.cellview.client.builder.CellTableWithListDataBuilder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Widget;

public class SimplePagedTable<T> extends PagedTable<T> {

    @UiField(provided = true) CellTable<T> dataTable;

    @UiField SimplePager pager;

    private static SimplePagedTableUiBinder uiBinder = GWT
            .create(SimplePagedTableUiBinder.class);

    @SuppressWarnings("rawtypes")
    interface SimplePagedTableUiBinder extends
            UiBinder<Widget, SimplePagedTable> {
    }

    public SimplePagedTable(CellTableWithListDataBuilder<T> builder) {
        super(builder);
    }

    @Override
    protected void initWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    protected void setDataTable(CellTable<T> dataTable) {
        this.dataTable = dataTable;
    }

    @Override
    protected SimplePager getPager() {
        return pager;
    }
}

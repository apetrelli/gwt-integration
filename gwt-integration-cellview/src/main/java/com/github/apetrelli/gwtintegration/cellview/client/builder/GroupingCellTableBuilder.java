package com.github.apetrelli.gwtintegration.cellview.client.builder;

import java.util.Collection;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.dom.builder.shared.TableSectionBuilder;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.CellTableBuilder;
import com.google.gwt.user.cellview.client.DefaultCellTableBuilder;

public class GroupingCellTableBuilder<T, I> implements CellTableBuilder<T> {

    public interface GroupRenderer<T, I> {
        I getGroupId(T obj);

        String renderGroup(T obj);

        String getClassName(T obj);
    }

    private I id = null;

    private int columnCount;

    private GroupRenderer<T, I> renderer;

    private DefaultCellTableBuilder<T> innerBuilder;

    public GroupingCellTableBuilder(
            AbstractCellTable<T> cellTable, GroupRenderer<T, I> renderer) {
        innerBuilder = new DefaultCellTableBuilder<T>(cellTable);
        columnCount = cellTable.getColumnCount();
        this.renderer = renderer;
    }

    @Override
    public void buildRow(T rowValue, int absRowIndex) {
        I newId = renderer.getGroupId(rowValue);
        if (!newId.equals(id)) {
            id = newId;
            TableRowBuilder tr = innerBuilder.startRow();
            TableCellBuilder td = tr.startTD();
            td.colSpan(columnCount).className(renderer.getClassName(rowValue))
                    .text(renderer.renderGroup(rowValue)).endTD();
            tr.endTR();
        }
        innerBuilder.buildRow(rowValue, absRowIndex);
    }

    @Override
    public TableSectionBuilder finish() {
        return innerBuilder.finish();
    }

    @Override
    public HasCell<T, ?> getColumn(Context context,
            T rowValue, Element elem) {
        return innerBuilder.getColumn(context, rowValue, elem);
    }

    @Override
    public Collection<HasCell<T, ?>> getColumns() {
        return innerBuilder.getColumns();
    }

    @Override
    public int getRowValueIndex(TableRowElement row) {
        return innerBuilder.getRowValueIndex(row);
    }

    @Override
    public int getSubrowValueIndex(TableRowElement row) {
        return innerBuilder.getSubrowValueIndex(row);
    }

    @Override
    public boolean isColumn(Element elem) {
        return innerBuilder.isColumn(elem);
    }

    @Override
    public void start(boolean isRebuildingAllRows) {
        id = null;
        innerBuilder.start(isRebuildingAllRows);
    }
}

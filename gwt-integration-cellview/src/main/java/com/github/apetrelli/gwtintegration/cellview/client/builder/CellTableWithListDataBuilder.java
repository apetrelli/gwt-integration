package com.github.apetrelli.gwtintegration.cellview.client.builder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.github.apetrelli.gwtintegration.cellview.client.widget.CheckboxHeader;
import com.github.apetrelli.gwtintegration.cellview.client.widget.SelectionColumn;
import com.github.apetrelli.gwtintegration.cellview.client.widget.SelectionFieldUpdater;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTableBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.DefaultSelectionEventManager.CheckboxEventTranslator;

public class CellTableWithListDataBuilder<T> {

    public interface CellTableBuilderFactory<T> {
        CellTableBuilder<T> create(AbstractCellTable<T> cellTable);
    }

    private ProvidesKey<T> keyProvider;

    private ListDataProvider<T> dataProvider;

    private AbstractPager pager;

    private CellPreviewEvent.Handler<T> cellPreviewHandler;

    private RowStyles<T> rowStyles;

    private SelectionModel<T> selectionModel;

    private CellPreviewEvent.Handler<T> selectionEventManager;

    private CellTableBuilderFactory<T> cellTableBuilderFactory;

    private String tableWidth;

    private boolean fixedWidth = false;

    private List<Header<?>> columnHeaders;

    private List<Column<T, ?>> columns;

    private List<Comparator<T>> comparators;

    private List<Double> columnSizes;

    private List<Unit> columnSizeUnits;

    private CellTable<T> dataTable;
    
    private String overallCellStyles;
    
    private boolean selectionColumnAdded = false;

    private CellTableWithListDataBuilder() {
        columnHeaders = new ArrayList<Header<?>>();
        columns = new ArrayList<Column<T,?>>();
        comparators = new ArrayList<Comparator<T>>();
        columnSizes = new ArrayList<Double>();
        columnSizeUnits = new ArrayList<Unit>();
    }

    public static <T> CellTableWithListDataBuilder<T> create() {
        return new CellTableWithListDataBuilder<T>();
    }

    public CellTableWithListDataBuilder<T> setKeyProvider(ProvidesKey<T> keyProvider) {
        this.keyProvider = keyProvider;
        return this;
    }

    public CellTableWithListDataBuilder<T> createDefaultDataProvider() {
        this.dataProvider = new ListDataProvider<T>();
        return this;
    }

    public CellTableWithListDataBuilder<T> setDataProvider(ListDataProvider<T> dataProvider) {
        this.dataProvider = dataProvider;
        return this;
    }

    public SelectionModel<T> getSelectionModel() {
        return selectionModel;
    }
    
    public ListDataProvider<T> getDataProvider() {
        return dataProvider;
    }
    
    public ProvidesKey<T> getKeyProvider() {
        return keyProvider;
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
    
    public CellTableWithListDataBuilder<T> setOverallCellStyles(String overallCellStyles) {
        this.overallCellStyles = overallCellStyles;
        return this;
    }

    public CellTableWithListDataBuilder<T> setSelectionModel(SelectionModel<T> selectionModel) {
        this.selectionModel = selectionModel;
        return this;
    }

    public CellTableWithListDataBuilder<T> createDefaultSelectionModel() {
        if (keyProvider == null) {
            throw new IllegalStateException("Call setKeyProvider first");
        }
        this.selectionModel = new MultiSelectionModel<T>(keyProvider);
        return this;
    }

    public CellTableWithListDataBuilder<T> setSelectionEventManager(
            CellPreviewEvent.Handler<T> selectionEventManager) {
        this.selectionEventManager = selectionEventManager;
        return this;
    }

    public CellTableWithListDataBuilder<T> setCellTableBuilderFactory(
            CellTableBuilderFactory<T> cellTableBuilderFactory) {
        this.cellTableBuilderFactory = cellTableBuilderFactory;
        return this;
    }

    public CellTableWithListDataBuilder<T> setWidth(String width, boolean fixedLayout) {
        tableWidth = width;
        fixedWidth = fixedLayout;
        return this;
    }

    public CellTableWithListDataBuilder<T> addColumn(String title,
            Column<T, ?> column, Comparator<T> comparator, Double size,
            Unit unit) {
        return addColumn(new TextHeader(title), column, comparator, size, unit);
    }

    public CellTableWithListDataBuilder<T> addColumn(Header<?> header,
            Column<T, ?> column, Comparator<T> comparator, Double size, Unit unit) {
        columnHeaders.add(header);
        columns.add(column);
        comparators.add(comparator);
        if (size != null && unit != null) {
            columnSizes.add(size);
            columnSizeUnits.add(unit);
        } else {
            columnSizes.add(null);
            columnSizeUnits.add(null);
        }
        return this;
    }

    public CellTableWithListDataBuilder<T> addColumn(String title, Column<T, ?> column, Comparator<T> comparator) {
        return addColumn(title, column, comparator, null, null);
    }

    public CellTableWithListDataBuilder<T> addColumn(String title, Column<T, ?> column) {
        return addColumn(title, column, null, null, null);
    }

    public CellTableWithListDataBuilder<T> addColumn(Header<?> header, Column<T, ?> column) {
        return addColumn(header, column, null, null, null);
    }

    public CellTableWithListDataBuilder<T> addSelectionCellboxColumn() {
        return addSelectionCellboxColumn(null, null);
    }

    public CellTableWithListDataBuilder<T> addSelectionCellboxColumn(Double size,
            Unit unit) {
        Column<T, Boolean> checkColumn = new SelectionColumn<T>(new CheckboxCell(true, true), selectionModel);
        CheckboxEventTranslator<T> translator = new DefaultSelectionEventManager.CheckboxEventTranslator<T>(
                getColumnListSize());
        addSelectionColumn(checkColumn, translator, size, unit);
        return this;
    }

    public CellTableWithListDataBuilder<T> addSelectionColumn(
            Column<T, Boolean> checkColumn,
            CheckboxEventTranslator<T> translator) {
        return addSelectionColumn(checkColumn, translator, null, null);
    }

    public CellTableWithListDataBuilder<T> addSelectionColumn(
            Column<T, Boolean> checkColumn,
            CheckboxEventTranslator<T> translator, Double size,
            Unit unit) {
        return addSelectionColumn(checkColumn, translator, size, unit,
                true);
    }

    private CellTableWithListDataBuilder<T> addSelectionColumn(
            Column<T, Boolean> checkColumn,
            CheckboxEventTranslator<T> translator, Double size, Unit unit,
            boolean addSelectAll) {
        setSelectionEventManager(DefaultSelectionEventManager
                .<T> createCustomManager(translator));
        checkColumn.setFieldUpdater(new SelectionFieldUpdater<T>(selectionModel));
        Header<?> header = addSelectAll ? new CheckboxHeader<T>(
                (MultiSelectionModel<T>) selectionModel, dataProvider)
                : new TextHeader("");
        addColumn(header, checkColumn, null, size, unit);
        selectionColumnAdded = true;
        return this;
    }

    public static <T> void addSelectionColumn(
            CellTableWithListDataBuilder<T> builder,
            Column<T, Boolean> checkColumn,
            CheckboxEventTranslator<T> translator,
            SelectionModel<T> selectionModel) {
        builder.setSelectionEventManager(DefaultSelectionEventManager
                .<T> createCustomManager(translator));
        checkColumn.setFieldUpdater(new SelectionFieldUpdater<T>(selectionModel));
        builder.addColumn("", checkColumn);
    }

    public CellTable<T> construct() {
        dataTable = new CellTable<T>(keyProvider);
        return dataTable;
    }

    public int getColumnListSize() {
        return columns.size();
    }

    public void connect() {
        if (tableWidth != null) {
            dataTable.setWidth(tableWidth, fixedWidth);
        }
        Iterator<Header<?>> headerIt = columnHeaders.iterator();
        Iterator<Column<T, ?>> columnIt = columns.iterator();
        Iterator<Comparator<T>> comparatorIt = comparators.iterator();
        Iterator<Double> sizeIt = columnSizes.iterator();
        Iterator<Unit> unitIt = columnSizeUnits.iterator();
        ListHandler<T> handler = new ListHandler<T>(dataProvider.getList());
        int i = 0;
        while (headerIt.hasNext()) {
            Header<?> header = headerIt.next();
            Column<T, ?> column = columnIt.next();
            Comparator<T> comparator = comparatorIt.next();
            Double size = sizeIt.next();
            Unit unit = unitIt.next();
            boolean sortable = comparator != null;
            column.setSortable(sortable);
            if (overallCellStyles != null && (!selectionColumnAdded || i > 0)) {
                column.setCellStyleNames(overallCellStyles);
            }
            dataTable.addColumn(column, header);
            if (sortable) {
                handler.setComparator(column, comparator);
            }
            // Setting column size has sense only on fixed layout.
            if (fixedWidth && size != null && unit != null) {
                dataTable.setColumnWidth(column, size, unit);
            }
            i++;
        }
        dataProvider.addDataDisplay(dataTable);
        pager.setDisplay(dataTable);
        dataTable.setRowStyles(rowStyles);
        dataTable.addCellPreviewHandler(cellPreviewHandler);
        dataTable.addColumnSortHandler(handler);
        if (selectionModel != null) {
            dataTable.setSelectionModel(selectionModel, selectionEventManager);
        }
        if (cellTableBuilderFactory != null) {
            dataTable.setTableBuilder(cellTableBuilderFactory.create(dataTable));
        }
        dataProvider.flush();
    }
}

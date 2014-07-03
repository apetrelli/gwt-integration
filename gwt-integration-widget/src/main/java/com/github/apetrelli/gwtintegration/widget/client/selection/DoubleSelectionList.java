package com.github.apetrelli.gwtintegration.widget.client.selection;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.apetrelli.gwtintegration.cellview.client.builder.CellTableWithListDataBuilder;
import com.github.apetrelli.gwtintegration.editor.client.ParseableValueEditor;
import com.github.apetrelli.gwtintegration.editor.client.TakesParseableValue;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

public class DoubleSelectionList<T> extends Composite implements
        TakesParseableValue<Set<T>>,
        IsEditor<LeafValueEditor<Set<T>>>{

    private static Resources DEFAULT_RESOURCES;

    @UiField(provided = true) CellTable<T> selectableTable;

    @UiField SimplePager selectablePager;

    @UiField(provided = true) CellTable<T> selectedTable;

    @UiField SimplePager selectedPager;

    @UiField Label leftTitle;

    @UiField Label rightTitle;

    private ListDataProvider<T> selectableDataProvider;

    private Integer selectableDefaultSortColumnIndex;

    private ListDataProvider<T> selectedDataProvider;

    private Integer selectedDefaultSortColumnIndex;

    private Map<Object, T> selectableItems;

    private ProvidesKey<T> keyProvider;

    private Style style;

    private Set<Integer> selectedOnSelectable;

    private Set<Integer> selectedOnSelected;

    private ParseableValueEditor<DoubleSelectionList<T>, Set<T>> editor;

    private static DoubleSelectionListUiBinder uiBinder = GWT
            .create(DoubleSelectionListUiBinder.class);

    @SuppressWarnings("rawtypes")
    interface DoubleSelectionListUiBinder extends
            UiBinder<Widget, DoubleSelectionList> {
    }

    private static Resources getDefaultResources() {
        if (DEFAULT_RESOURCES == null) {
            DEFAULT_RESOURCES = GWT.create(Resources.class);
        }
        return DEFAULT_RESOURCES;
    }

    @UiConstructor
    public DoubleSelectionList(ProvidesKey<T> keyProvider) {
        this.keyProvider = keyProvider;
        selectableDataProvider = new ListDataProvider<T>();
        selectedDataProvider = new ListDataProvider<T>();
        selectableItems = new LinkedHashMap<Object, T>();
        style = getDefaultResources().css();
        style.ensureInjected();
        selectedOnSelectable = new HashSet<Integer>();
        selectedOnSelected = new HashSet<Integer>();
    }

    public CellTableWithListDataBuilder<T> createSelectableTableBuilder() {
        CellTableWithListDataBuilder<T> builder = CellTableWithListDataBuilder.create();
        builder.setKeyProvider(keyProvider)
                .setDataProvider(selectableDataProvider)
                .setPager(selectablePager)
                .setRowStyles(new SelectionAwareRowStyles(selectedOnSelectable));
        return builder;
    }

    public CellTableWithListDataBuilder<T> createSelectedTableBuilder() {
        CellTableWithListDataBuilder<T> builder = CellTableWithListDataBuilder.create();
        builder.setKeyProvider(keyProvider)
                .setDataProvider(selectedDataProvider).setPager(selectedPager)
                .setRowStyles(new SelectionAwareRowStyles(selectedOnSelected));
        return builder;
    }

    public void setSelectableDefaultSortColumnIndex(
            Integer selectableDefaultSortColumnIndex) {
        this.selectableDefaultSortColumnIndex = selectableDefaultSortColumnIndex;
    }

    public void setSelectedDefaultSortColumnIndex(
            Integer selectedDefaultSortColumnIndex) {
        this.selectedDefaultSortColumnIndex = selectedDefaultSortColumnIndex;
    }

    public SimplePager getSelectablePager() {
        return selectablePager;
    }

    public SimplePager getSelectedPager() {
        return selectedPager;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle.setText(leftTitle);
    }

    public void setRightTitle(String rightTitle) {
        this.rightTitle.setText(rightTitle);
    }

    public void init(CellTableWithListDataBuilder<T> selectableTableBuilder,
            CellTableWithListDataBuilder<T> selectedTableBuilder) {
        selectableTable = selectableTableBuilder.construct();
        selectedTable = selectedTableBuilder.construct();
        initWidget(uiBinder.createAndBindUi(this));
        selectableTableBuilder.setCellPreviewHandler(new CellSelectionHandler(
                selectedOnSelectable, selectableTable)).setPager(selectablePager).connect();
        selectedTableBuilder.setCellPreviewHandler(new CellSelectionHandler(
                selectedOnSelected, selectedTable)).setPager(selectedPager).connect();
    }

    public void setSelectableItems(List<T> selectableItems) {
        this.selectableItems = new LinkedHashMap<Object, T>();
        for (T item : selectableItems) {
            this.selectableItems.put(keyProvider.getKey(item), item);
        }
        refreshSelectableItems();
    }

    public void clearElements() {
        selectableDataProvider.getList().clear();
        selectedDataProvider.getList().clear();
        refreshSelectableTable();
        refreshSelectedTable();
    }

    public void setSelectedItems(Collection<T> selectedItems) {
        List<T> selectableList = selectedDataProvider.getList();
        selectableList.clear();
        if (selectedItems != null) {
            for (T item: selectedItems) {
                selectableItems.remove(keyProvider.getKey(item));
            }
            selectableList.addAll(selectedItems);
        }
        refreshSelectableItems();
        ColumnSortList sortList = selectedTable.getColumnSortList();
        sortList.clear();
        if (selectedDefaultSortColumnIndex != null) {
            sortList.push(selectedTable.getColumn(selectedDefaultSortColumnIndex));
        }
        refreshSelectedTable();
    }

    @Override
    public void setValue(Set<T> value) {
        setSelectedItems(value);
    }

    @Override
    public Set<T> getValue() {
        return new LinkedHashSet<T>(selectedDataProvider.getList());
    }

    @Override
    public ParseableValueEditor<DoubleSelectionList<T>, Set<T>> asEditor() {
        if (editor == null) {
            editor = ParseableValueEditor.of(this);
        }
        return editor;
    }

    @Override
    public Set<T> getValueOrThrow() throws ParseException {
        return getValue();
    }

    @Override
    public String getUnparsedText() {
        return getValue().toString();
    }

    public interface Style extends CssResource {
        String selected();

        String DEFAULT_CSS = "it/eng/utilgwt/web/client/ui/widget/DoubleSelectionList.css";
    }

    public interface Resources extends ClientBundle {
        @Source(Style.DEFAULT_CSS)
        Style css();
    }

    private class SelectionAwareRowStyles implements RowStyles<T> {

        private Set<Integer> selectedIndexes;

        private SelectionAwareRowStyles(Set<Integer> selectedIndexes) {
            this.selectedIndexes = selectedIndexes;
        }

        @Override
        public String getStyleNames(T row, int rowIndex) {
            return selectedIndexes.contains(rowIndex) ? style.selected() : null;
        }

    }

    private class CellSelectionHandler implements CellPreviewEvent.Handler<T> {

        private Set<Integer> selections;

        private CellTable<T> table;

        private CellSelectionHandler(Set<Integer> selections, CellTable<T> table) {
            this.selections = selections;
            this.table = table;
        }

        @Override
        public void onCellPreview(CellPreviewEvent<T> event) {
            String type = event.getNativeEvent().getType();
            if (type.equals("click")) {
                Integer index = event.getIndex();
                if (selections.contains(index)) {
                    selections.remove(index);
                } else {
                    selections.add(index);
                }
                table.redrawRow(index);
            }
        }
    }

    @UiHandler("add")
    public void onAddClick(ClickEvent event) {
        List<T> selectableElements = selectableDataProvider.getList();
        List<T> toMove = new ArrayList<T>();
        for (Integer pos: selectedOnSelectable) {
            toMove.add(selectableElements.get(pos));
            selectableElements.set(pos, null);
        }
        selectedDataProvider.getList().addAll(toMove);
        List<T> compactedList = new ArrayList<T>();
        for (T element: selectableElements) {
            if (element != null) {
                compactedList.add(element);
            }
        }
        selectableElements.clear();
        selectableElements.addAll(compactedList);
        clearSelections();
        refreshSelectableTable();
        refreshSelectedTable();
    }

    @UiHandler("remove")
    public void onRemoveClick(ClickEvent event) {
        List<T> selectedElements = selectedDataProvider.getList();
        List<T> toMove = new ArrayList<T>();
        for (Integer pos: selectedOnSelected) {
            toMove.add(selectedElements.get(pos));
            selectedElements.set(pos, null);
        }
        selectableDataProvider.getList().addAll(toMove);
        List<T> compactedList = new ArrayList<T>();
        for (T element: selectedElements) {
            if (element != null) {
                compactedList.add(element);
            }
        }
        selectedElements.clear();
        selectedElements.addAll(compactedList);
        clearSelections();
        refreshSelectableTable();
        refreshSelectedTable();
    }

    @UiHandler("addAll")
    public void onAddAllClick(ClickEvent event) {
        List<T> selectableElements = selectableDataProvider.getList();
        selectedDataProvider.getList().addAll(selectableElements);
        selectableElements.clear();
        clearSelections();
        refreshSelectableTable();
        refreshSelectedTable();
    }

    @UiHandler("removeAll")
    public void onRemoveAllClick(ClickEvent event) {
        List<T> selectedElements = selectedDataProvider.getList();
        selectableDataProvider.getList().addAll(selectedElements);
        selectedElements.clear();
        clearSelections();
        refreshSelectableTable();
        refreshSelectedTable();
    }

    private void refreshSelectableItems() {
        List<T> selectableList = selectableDataProvider.getList();
        selectableList.clear();
        selectableList.addAll(this.selectableItems.values());
        ColumnSortList sortList = selectableTable.getColumnSortList();
        sortList.clear();
        if (selectableDefaultSortColumnIndex != null) {
            sortList.push(selectableTable.getColumn(selectableDefaultSortColumnIndex));
        }
        refreshSelectableTable();
    }

    private void refreshSelectedTable() {
        selectedDataProvider.flush();
        selectedPager.setDisplay(selectedTable);
        selectablePager.setPage(0);
    }

    private void refreshSelectableTable() {
        selectableDataProvider.flush();
        selectablePager.setDisplay(selectableTable);
        selectablePager.setPage(0);
    }

    private void clearSelections() {
        selectedOnSelectable.clear();
        selectedOnSelected.clear();
    }
}

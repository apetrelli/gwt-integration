package com.github.apetrelli.gwtintegration.cellview.client.widget;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

/**
 * Original source: http://pgt.de/2012/05/09/checkbox-in-cell-table-data-grid-header/
 *
 * @param <T> The type of the header.
 */
public class CheckboxHeader<T> extends Header<Boolean> {
    
    private final MultiSelectionModel<T> selectionModel;
    private final ListDataProvider<T> provider;
 
    public CheckboxHeader(MultiSelectionModel<T> selectionModel,
            ListDataProvider<T> provider) {
        super(new CheckboxCell());
        this.selectionModel = selectionModel;
        this.provider = provider;
    }
 
    @Override
    public Boolean getValue() {
        boolean allItemsSelected = selectionModel.getSelectedSet().size() == provider
                .getList().size();
        return allItemsSelected;
    }
 
    @Override
    public void onBrowserEvent(Context context, Element elem, NativeEvent event) {
        InputElement input = elem.getFirstChild().cast();
        Boolean isChecked = input.isChecked();
        for (T element : provider.getList()) {
            selectionModel.setSelected(element, isChecked);
        }
    }
}

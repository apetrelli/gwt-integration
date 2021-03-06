package com.github.apetrelli.gwtintegration.mvp.client.ui;

import java.util.List;
import java.util.Set;

import com.google.web.bindery.requestfactory.shared.BaseProxy;

public interface ListView<T extends BaseProxy, P extends MvpView.Presenter> extends MvpView<P>{

    void setItems(List<T> items);

    Set<T> getSelectedItems();
}

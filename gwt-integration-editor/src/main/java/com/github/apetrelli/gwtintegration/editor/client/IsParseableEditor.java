package com.github.apetrelli.gwtintegration.editor.client;

import com.google.gwt.user.client.ui.IsWidget;


public interface IsParseableEditor<W extends IsWidget & TakesParseableValue<T>, T> {

    ParseableValueEditor<W, T> asParseableEditor();

}

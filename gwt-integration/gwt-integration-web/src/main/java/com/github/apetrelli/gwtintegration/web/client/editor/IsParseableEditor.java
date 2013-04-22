package com.github.apetrelli.gwtintegration.web.client.editor;

import com.github.apetrelli.gwtintegration.web.client.editor.widget.ParseableValueEditor;
import com.github.apetrelli.gwtintegration.web.client.ui.TakesParseableValue;
import com.google.gwt.user.client.ui.IsWidget;


public interface IsParseableEditor<W extends IsWidget & TakesParseableValue<T>, T> {
	
	ParseableValueEditor<W, T> asParseableEditor();

}

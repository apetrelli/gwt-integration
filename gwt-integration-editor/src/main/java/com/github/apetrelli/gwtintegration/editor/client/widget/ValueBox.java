package com.github.apetrelli.gwtintegration.editor.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiConstructor;

public class ValueBox<T> extends com.google.gwt.user.client.ui.ValueBox<T>{
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@UiConstructor
	public ValueBox(Renderer renderer, Parser parser) {
		super(Document.get().createTextInputElement(), renderer, parser);
	}
}

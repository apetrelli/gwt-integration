package com.github.apetrelli.gwtintegration.editor.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;

public class ValueBox<T> extends com.google.gwt.user.client.ui.ValueBox<T>{
	
	public ValueBox(Renderer<T> renderer, Parser<T> parser) {
		super(Document.get().createTextInputElement(), renderer, parser);
	}
}

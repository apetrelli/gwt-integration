/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.apetrelli.gwtintegration.web.client.editor.widget;


import java.util.List;

import com.github.apetrelli.gwtintegration.web.client.editor.IsParseableEditor;
import com.github.apetrelli.gwtintegration.web.client.ui.TakesParseableValue;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.Widget;

/**
 * A simple decorator to display leaf widgets with an error message.
 * <p>
 * <h3>Use in UiBinder Templates</h3>
 * <p>
 * The decorator may have exactly one ValueBoxBase added though an
 * <code>&lt;e:valuebox></code> child tag.
 * <p>
 * For example:
 * 
 * <pre>
 * &#064;UiField
 * LeafValueEditorDecorator&lt;String&gt; name;
 * </pre>
 * 
 * <pre>
 * &lt;e:LeafValueEditorDecorator ui:field='name'>
 *   &lt;e:valuebox>
 *     &lt;g:TextBox />
 *   &lt;/e:valuebox>
 * &lt;/e:LeafValueEditorDecorator>
 * </pre>
 * 
 * @param <T>
 *            the type of data being edited
 */
public class LeafValueEditorDecorator<W extends IsWidget & IsParseableEditor<W, T> & TakesParseableValue<T>, T>
		extends Composite implements HasEditorErrors<T>,
		IsEditor<ParseableValueEditor<W, T>> {
	interface Binder extends UiBinder<Widget, LeafValueEditorDecorator<?, ?>> {
		Binder BINDER = GWT.create(Binder.class);
	}

	@UiField
	SimplePanel contents;

	@UiField
	DivElement errorLabel;

	private ParseableValueEditor<W, T> editor;

	/**
	 * Constructs a LeafValueEditorDecorator.
	 */
	@UiConstructor
	public LeafValueEditorDecorator() {
		initWidget(Binder.BINDER.createAndBindUi(this));
	}

	/**
	 * Constructs a LeafValueEditorDecorator using a {@link ValueBoxBase} widget
	 * and a {@link ParseableValueEditor} editor.
	 * 
	 * @param widget
	 *            the widget
	 * @param editor
	 *            the editor
	 */
	public LeafValueEditorDecorator(W widget, ParseableValueEditor<W, T> editor) {
		this();
		contents.add(widget);
		this.editor = editor;
	}

	/**
	 * Returns the associated {@link ParseableValueEditor}.
	 * 
	 * @return a {@link ParseableValueEditor} instance
	 * @see #setEditor(ParseableValueEditor)
	 */
	public ParseableValueEditor<W, T> asEditor() {
		return editor;
	}

	/**
	 * Sets the associated {@link ParseableValueEditor}.
	 * 
	 * @param editor
	 *            a {@link ParseableValueEditor} instance
	 * @see #asEditor()
	 */
	public void setEditor(ParseableValueEditor<W, T> editor) {
		this.editor = editor;
	}

	/**
	 * Set the widget that the EditorPanel will display. This method will
	 * automatically call {@link #setEditor}.
	 * 
	 * @param widget
	 *            a {@link ValueBoxBase} widget
	 */
	@UiChild(limit = 1, tagname = "valuebox")
	public void setValueBox(W widget) {
		contents.add(widget);
		setEditor(widget.asParseableEditor());
	}

	/**
	 * The default implementation will display, but not consume, received errors
	 * whose {@link EditorError#getEditor() getEditor()} method returns the
	 * Editor passed into {@link #setEditor}.
	 * 
	 * @param errors
	 *            a List of {@link EditorError} instances
	 */
	public void showErrors(List<EditorError> errors) {
		StringBuilder sb = new StringBuilder();
		for (EditorError error : errors) {
			if (error.getEditor().equals(editor)) {
				sb.append("\n").append(error.getMessage());
			}
		}

		if (sb.length() == 0) {
			errorLabel.setInnerText("");
			errorLabel.getStyle().setDisplay(Display.NONE);
			return;
		}

		errorLabel.setInnerText(sb.substring(1));
		errorLabel.getStyle().setDisplay(Display.INLINE_BLOCK);
	}
}

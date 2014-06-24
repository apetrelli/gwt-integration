package com.github.apetrelli.gwtintegration.editor.client.widget;


import com.github.apetrelli.gwtintegration.editor.client.IsParseableEditor;
import com.github.apetrelli.gwtintegration.editor.client.ParseableValueEditor;
import com.github.apetrelli.gwtintegration.editor.client.TakesParseableValue;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.view.client.ProvidesKey;

public class ValueListBox<T> extends
        com.google.gwt.user.client.ui.ValueListBox<T> implements
        TakesParseableValue<T>, IsParseableEditor<ValueListBox<T>, T> {

    private ParseableValueEditor<ValueListBox<T>, T> editor;

    private Renderer<T> renderer;

    /**
     * @param renderer
     * @param keyProvider
     */
    public ValueListBox(Renderer<T> renderer, ProvidesKey<T> keyProvider) {
        super(renderer, keyProvider);
        this.renderer = renderer;
    }

    /**
     * @param renderer
     */
    public ValueListBox(Renderer<T> renderer) {
        super(renderer);
        this.renderer = renderer;
    }

    @Override
    public ParseableValueEditor<ValueListBox<T>, T> asParseableEditor() {
        if (editor == null) {
            editor = ParseableValueEditor.of(this);
        }
        return editor;
    }

    @Override
    public T getValueOrThrow() {
        return getValue();
    }

    @Override
    public String getUnparsedText() {
        return renderer.render(getValue());
    }

}

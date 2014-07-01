package com.github.apetrelli.gwtintegration.editor.client.widget;

import java.text.ParseException;

import com.github.apetrelli.gwtintegration.editor.client.IsParseableEditor;
import com.github.apetrelli.gwtintegration.editor.client.ParseableValueEditor;
import com.github.apetrelli.gwtintegration.editor.client.TakesParseableValue;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.ValueBoxBase;

public class ValueSuggestBox extends SuggestBox implements
        TakesParseableValue<String>, IsParseableEditor<ValueSuggestBox, String> {

    private ParseableValueEditor<ValueSuggestBox, String> editor;

    public ValueSuggestBox() {
    }

    public ValueSuggestBox(SuggestOracle oracle, ValueBoxBase<String> box,
            SuggestionDisplay suggestDisplay) {
        super(oracle, box, suggestDisplay);
    }

    public ValueSuggestBox(SuggestOracle oracle, ValueBoxBase<String> box) {
        super(oracle, box);
    }

    public ValueSuggestBox(SuggestOracle oracle) {
        super(oracle);
    }

    @Override
    public ParseableValueEditor<ValueSuggestBox, String> asEditor() {
        if (editor == null) {
            editor = ParseableValueEditor.of(this);
        }
        return editor;
    }

    @Override
    public ParseableValueEditor<ValueSuggestBox, String> asParseableEditor() {
        return asEditor();
    }

    @Override
    public String getValueOrThrow() throws ParseException {
        return getValue();
    }

    @Override
    public String getUnparsedText() {
        return getValue();
    }

}

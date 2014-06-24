package com.github.apetrelli.gwtintegration.editor.client.widget;


import java.math.BigDecimal;

import com.github.apetrelli.gwtintegration.editor.client.text.BigDecimalParser;
import com.github.apetrelli.gwtintegration.editor.client.text.BigDecimalRenderer;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ValueBox;

public class BigDecimalBox extends ValueBox<BigDecimal>{
    public BigDecimalBox() {
        super(Document.get().createTextInputElement(), BigDecimalRenderer.instance(),
            BigDecimalParser.instance());
      }
}

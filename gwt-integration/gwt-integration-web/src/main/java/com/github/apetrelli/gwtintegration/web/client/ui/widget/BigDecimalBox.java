package com.github.apetrelli.gwtintegration.web.client.ui.widget;


import java.math.BigDecimal;

import com.github.apetrelli.gwtintegration.web.client.text.BigDecimalParser;
import com.github.apetrelli.gwtintegration.web.client.text.BigDecimalRenderer;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ValueBox;

public class BigDecimalBox extends ValueBox<BigDecimal>{
	public BigDecimalBox() {
	    super(Document.get().createTextInputElement(), BigDecimalRenderer.instance(),
	        BigDecimalParser.instance());
	  }
}

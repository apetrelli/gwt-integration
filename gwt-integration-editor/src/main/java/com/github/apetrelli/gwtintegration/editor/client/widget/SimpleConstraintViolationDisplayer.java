package com.github.apetrelli.gwtintegration.editor.client.widget;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.github.apetrelli.gwtintegration.editor.client.editor.ConstraintViolationDisplayer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SimpleConstraintViolationDisplayer extends Composite implements ConstraintViolationDisplayer {

    private static SimpleConstraintViolationDisplayerUiBinder uiBinder = GWT
            .create(SimpleConstraintViolationDisplayerUiBinder.class);

    interface SimpleConstraintViolationDisplayerUiBinder extends
            UiBinder<Widget, SimpleConstraintViolationDisplayer> {
    }

    @UiField FlowPanel container;

    public SimpleConstraintViolationDisplayer() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void reset() {
        container.clear();
    }

    @Override
    public void setConstraintViolations(Set<ConstraintViolation<?>> violations) {
        container.clear();
        for (ConstraintViolation<?> violation : violations) {
            container.add(new Label(violation.getMessage()));
        }
    }
}

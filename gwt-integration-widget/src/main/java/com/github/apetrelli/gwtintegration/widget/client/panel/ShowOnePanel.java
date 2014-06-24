package com.github.apetrelli.gwtintegration.widget.client.panel;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;

public class ShowOnePanel extends FlowPanel {

    public void show(int index) {
        WidgetCollection widgets = getChildren();
        int i = 0;
        for (Widget widget: widgets) {
            widget.setVisible(i == index);
            i++;
        }
    }
}

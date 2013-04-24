package com.github.apetrelli.gwtintegration.mvp.client.ui;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public interface HasBody extends IsWidget {

	AcceptsOneWidget getBody();
}

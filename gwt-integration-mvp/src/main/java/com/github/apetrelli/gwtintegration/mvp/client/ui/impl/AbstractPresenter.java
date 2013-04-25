package com.github.apetrelli.gwtintegration.mvp.client.ui.impl;


import com.github.apetrelli.gwtintegration.mvp.client.ui.MvpView;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;

public abstract class AbstractPresenter extends AbstractActivity implements MvpView.Presenter {

	protected PlaceController controller;
	
	/**
	 * @param controller
	 */
	public AbstractPresenter(PlaceController controller) {
		this.controller = controller;
	}



	@Override
	public void goTo(Place place) {
		controller.goTo(place);
	}
}

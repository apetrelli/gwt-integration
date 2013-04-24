package com.github.apetrelli.gwtintegration.mvp.client.ui.impl;


import com.github.apetrelli.gwtintegration.mvp.client.ui.MvpView;
import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractMvpView<T extends MvpView.Presenter> extends Composite implements MvpView<T>{

	protected T presenter;
	
	@Override
	public void setPresenter(T presenter) {
		this.presenter = presenter;
	}
}

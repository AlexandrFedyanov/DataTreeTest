package com.test.afedyanov.datatree.presenter.presenterloader;

import android.content.Context;
import android.support.v4.content.Loader;

import com.test.afedyanov.datatree.presenter.PresenterFactory;
import com.test.afedyanov.datatree.presenter.presenterinterface.IMainPresenter;

public class MainPresenterLoader extends Loader<IMainPresenter> {

    private PresenterFactory<IMainPresenter> presenterFactory;
    private IMainPresenter presenter;

    public MainPresenterLoader(Context context, PresenterFactory<IMainPresenter> presenterFactory) {
        super(context);
        this.presenterFactory = presenterFactory;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (presenter != null) {
            deliverResult(presenter);
        } else
            forceLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        presenter = presenterFactory.createPresenter();
        deliverResult(presenter);
    }

    @Override
    protected void onReset() {
        super.onReset();
        if (presenter != null)
            presenter.destroy();
        presenter = null;
    }
}

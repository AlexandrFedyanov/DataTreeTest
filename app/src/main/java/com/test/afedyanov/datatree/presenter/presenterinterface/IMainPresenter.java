package com.test.afedyanov.datatree.presenter.presenterinterface;

import com.test.afedyanov.datatree.view.viewinterface.IMainView;

public interface IMainPresenter {
    void attachView(IMainView view);

    void detachView();

    void destroy();

    void loadFromDataBase(int selectedId);

    void reset();

    void remove(int selectedId);

    void add(int selectedId, String value);

    void edit(int selectedId, String newValue);

    void apply();
}

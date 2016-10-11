package com.test.afedyanov.datatree.presenter;

public class BasePresenter<T> {
    protected T view;

    public void attachView(T view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }

    public void destroy() {

    }
}

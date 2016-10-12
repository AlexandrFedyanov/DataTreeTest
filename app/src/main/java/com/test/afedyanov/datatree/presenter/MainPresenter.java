package com.test.afedyanov.datatree.presenter;

import com.test.afedyanov.datatree.R;
import com.test.afedyanov.datatree.model.DataBase;
import com.test.afedyanov.datatree.model.LocalCache;
import com.test.afedyanov.datatree.model.Node;
import com.test.afedyanov.datatree.presenter.presenterinterface.IMainPresenter;
import com.test.afedyanov.datatree.view.viewinterface.IMainView;

public class MainPresenter extends BasePresenter<IMainView> implements IMainPresenter {

    private DataBase dataBase;
    private LocalCache localCache;

    public MainPresenter() {
        dataBase = new DataBase();
        localCache = new LocalCache();
    }

    @Override
    public void attachView(IMainView view) {
        super.attachView(view);
        view.setDataBaseData(dataBase);
        view.setLocalCacheData(localCache);
    }

    @Override
    public void destroy() {
        super.destroy();
        dataBase = null;
        localCache = null;
    }

    @Override
    public void loadFromDataBase(int selectedId) {
        if (selectedId == -1) {
            view.showMessage(R.string.no_selection_error);
        } else {
            localCache.addNode(dataBase.loadNodeById(selectedId));
            view.notifyCacheDataChanged();
        }
    }

    @Override
    public void reset() {
        localCache.clear();
        dataBase.reset();
        view.notifyCacheDataChanged();
        view.notifyDataBaseDataChanged();
    }

    @Override
    public void remove(int selectedId) {
        localCache.setDeleted(localCache.getElementById(selectedId));
        view.notifyCacheDataChanged();
    }

    @Override
    public void onAddClick(int selectedId) {
        view.showCreateDialog(selectedId, "New node");
    }

    @Override
    public void onEditClick(int selectedId) {
        Node currentNode = localCache.getElementById(selectedId);
        view.showEditDialog(selectedId, currentNode.getValue());
    }

    @Override
    public void add(int selectedId, String value) {
        localCache.createNode(selectedId, value);
        view.notifyCacheDataChanged();
    }

    @Override
    public void edit(int selectedId, String newValue) {
        localCache.editNode(selectedId, newValue);
        view.notifyCacheDataChanged();
    }

    @Override
    public void apply() {
        dataBase.applyChanges(localCache.getNodes());
        localCache.clear();
        view.notifyCacheDataChanged();
        view.notifyDataBaseDataChanged();
    }
}

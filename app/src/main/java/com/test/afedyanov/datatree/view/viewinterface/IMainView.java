package com.test.afedyanov.datatree.view.viewinterface;

import com.test.afedyanov.datatree.model.ITreeDataSet;

public interface IMainView {
    void notifyCacheDataChanged();

    void notifyDataBaseDataChanged();

    void setDataBaseData(ITreeDataSet dataBaseData);

    void setLocalCacheData(ITreeDataSet localCacheData);

    void showEditDialog(int selectedId, String value);

    void showCreateDialog(int selectedId, String value);

    void showMessage(int messageRes);
}

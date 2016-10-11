package com.test.afedyanov.datatree.view.viewinterface;

import com.test.afedyanov.datatree.model.ITreeDataSet;
import com.test.afedyanov.datatree.model.Node;

import java.util.List;

public interface ITreeView {

    void setData(ITreeDataSet dataSet);

    int getCurrentSelection();

    void notifyDataSetChanged();

}

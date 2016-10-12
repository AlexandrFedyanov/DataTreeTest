package com.test.afedyanov.datatree.model;

import android.support.annotation.Nullable;

public interface ITreeDataSet {
    @Nullable
    Node getElementById(Integer id);
    Node getElementForPosition(int position);
    int getElementsCount();
    int getElementDepthOnTree(Node node);
}

package com.test.afedyanov.datatree.model;

import java.util.List;

public interface ITreeDataSet {
    Node getElementById(Integer id);
    Node getElementForPosition(int position);
    int getElementsCount();
    int getElementDepthOnTree(Node node);
}

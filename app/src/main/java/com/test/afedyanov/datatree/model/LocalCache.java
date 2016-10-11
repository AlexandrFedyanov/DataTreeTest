package com.test.afedyanov.datatree.model;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class LocalCache implements ITreeDataSet {

    private SparseArray<Node> nodes = new SparseArray<>();

    public List<Node> getNodes() {
        List<Node> copiedNodes = new ArrayList<>();
        for(int i = 0; i < nodes.size(); i++) {
            int key = nodes.keyAt(i);
            copiedNodes.add(nodes.get(key));
        }
        return copiedNodes;
    }

    public void clear() {
        nodes.clear();
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    @Override
    public Node getElementById(Integer id) {
        if (id == null)
            return null;
        return nodes.get(id);
    }

    @Override
    public Node getElementForPosition(int position) {
        return nodes.get(nodes.keyAt(position));
    }

    @Override
    public int getElementsCount() {
        return nodes.size();
    }

    @Override
    public int getElementDepthOnTree(Node node) {
        Node root = getElementById(node.getRootId());
        int depth = 0;
        while (root != null) {
            depth++;
            root = getElementById(root.getRootId());
        }
        return depth;
    }
}

package com.test.afedyanov.datatree.model;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr on 11.10.2016.
 */

public class BaseTreeSet implements ITreeDataSet {

    protected SparseArray<Node> nodes = new SparseArray<>();
    private int[] orderedIds;

    @Override
    public Node getElementById(Integer id) {
        if (id == null)
            return null;
        return nodes.get(id);
    }

    @Override
    public Node getElementForPosition(int position) {
        int orderedPosition =  orderedIds != null && orderedIds.length > position ? orderedIds[position] : position;
        return nodes.get(orderedPosition);
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

    protected void orderElements() {
        orderedIds = new int[nodes.size()];
        List<Node> roots = findRoots();
        int position = 0;
        for (Node root: roots) {
            position = addElementToOrder(root, position);
        }
    }

    private int addElementToOrder(Node node, int currentPosition) {
        orderedIds[currentPosition++] = node.getId();
        for (int childId : node.getNodesIds()) {
            Node child = getElementById(childId);
            if (child != null)
                currentPosition = addElementToOrder(child, currentPosition);
        }
        return currentPosition;
    }

    private List<Node> findRoots() {
        List<Node> roots = new ArrayList<>();
        for(int i = 0; i < nodes.size(); i++) {
            int key = nodes.keyAt(i);
            Node node = getElementById(key);
            Node root = getElementById(node.getRootId());
            if (root == null)
                roots.add(node);
        }
        return roots;
    }

    protected void removeBranch(Node root) {
        for (int childId : root.getNodesIds()) {
            Node child = getElementById(childId);
            if (child != null) {
                child.setValid(false);
                removeBranch(child);
            }
        }
    }

}

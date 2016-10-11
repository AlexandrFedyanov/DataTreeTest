package com.test.afedyanov.datatree.model;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alexandr on 10.10.2016.
 */
public class DataBase implements ITreeDataSet {

    private SparseArray<Node> datas = new SparseArray<>();

    private final String baseNodeName = "Node ";
    private int createdNodesCounter = 0;
    private int[] childPseudorandomCount = {4, 2, 3, 1, 2, 0};

    public DataBase() {
        fillData();
    }

    public void reset() {
        datas.clear();
        createdNodesCounter = 0;
        fillData();
    }

    public void applyChanges(List<Node> newNodes) {
        for (Node node : newNodes) {
            if (node.getId() < 0) {// new item
                int newId = getElementForPosition(datas.size() - 1).getId() + 1;
                Node root = getElementById(node.getId());
                if (root != null) {
                    root.getNodesIds().remove(node.getId());
                    root.addChildren(newId);
                }
                node.setId(newId);
            }
            addData(node);
            if (!node.isValid()) {
                removeBranch(node);
            }
        }
    }

    private void removeBranch(Node root) {
        for (int childId : root.getNodesIds()) {
            Node child = getElementById(childId);
            child.setValid(false);
            removeBranch(child);
        }
    }

    private void addData(Node node) {
        datas.put(node.getId(), node);
    }

    public Node loadNodeById(int id) {
        return Node.copy(getElementById(id));
    }
    @Override
    public Node getElementById(Integer id) {
        if (id == null)
            return null;
        return datas.get(id);
    }

    @Override
    public Node getElementForPosition(int position) {
        return datas.get(datas.keyAt(position));
    }


    @Override
    public int getElementsCount() {
        return datas.size();
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

    private void fillData() {
        Node treeRoot = new Node();
        treeRoot.setId(createdNodesCounter);
        treeRoot.setValue(baseNodeName + ++createdNodesCounter);
        addData(treeRoot);
        addChilds(treeRoot, getChildCount(createdNodesCounter), 0, 5);
    }

    private void addChilds(Node root, int childCount, int currentDepth, int maxDepth) {
        if (currentDepth == maxDepth && childCount < 2)
            childCount = 2;
        for (int i = 0; i < childCount; i++) {
            Node child = new Node();
            child.setId(createdNodesCounter);
            child.setValue(baseNodeName + ++createdNodesCounter);
            child.setRootId(root.getId());
            root.addChildren(child.getId());
            addData(child);
            if (currentDepth != maxDepth) {
                addChilds(child, getChildCount(createdNodesCounter), currentDepth + 1, maxDepth);
            }
        }
    }

    private int getChildCount(int currentNode) {
        return childPseudorandomCount[currentNode - childPseudorandomCount.length * (currentNode/childPseudorandomCount.length)];
    }
}

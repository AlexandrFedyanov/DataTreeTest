package com.test.afedyanov.datatree.model;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class LocalCache implements ITreeDataSet {

    private SparseArray<Node> nodes = new SparseArray<>();
    private List<Node> roots = new ArrayList<>();
    private int createdNodesCounter = 0;

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
        createdNodesCounter = 0;
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
        if (checkIsDeleted(node))
            node.setValid(false);
        findRoots();
    }

    private void findRoots() {
        roots.clear();
        for(int i = 0; i < nodes.size(); i++) {
            int key = nodes.keyAt(i);
            Node node = getElementById(key);
            Node root = getElementById(node.getRootId());
            if (root == null)
                roots.add(node);
        }
    }

    public void createNode(int rootId, String value) {
        Node node = new Node();
        node.setId(node.getId() - createdNodesCounter++);
        node.setValue(value);
        node.setRootId(rootId);
        Node root = getElementById(rootId);
        if (root != null)
            root.addChildren(node.getId());
        addNode(node);
    }

    public void editNode(int nodeId, String value) {
        getElementById(nodeId).setValue(value);
    }

    @Override
    public Node getElementById(Integer id) {
        if (id == null)
            return null;
        return nodes.get(id);
    }

    @Override
    public Node getElementForPosition(int position) {
        int itemsCount = 0;
        for (Node root: roots) {
            if (position == itemsCount)
                return root;
            itemsCount++;
            while (root.getNodesIds().size() != 0) {
                for (int childId : root.getNodesIds()) {
                    Node child = getElementById(childId);
                    if (child != null) {
                        root = child;
                        itemsCount++;
                        if (position == itemsCount)
                            return root;
                    }
                }
            }

        }
        return nodes.get(nodes.keyAt(position));
    }

    @Override
    public int getElementsCount() {
        return nodes.size();
    }

    public void setDeleted(Node node) {
        node.setValid(false);
        removeBranch(node);
    }

    private void removeBranch(Node root) {
        for (int childId : root.getNodesIds()) {
            Node child = getElementById(childId);
            if (child != null) {
                child.setValid(false);
                removeBranch(child);
            }
        }
    }

    private boolean checkIsDeleted(Node node) {
        Node root = getElementById(node.getRootId());
        return root != null && !root.isValid();
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

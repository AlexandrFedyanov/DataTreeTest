package com.test.afedyanov.datatree.model;

import android.util.SparseIntArray;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alexandr on 10.10.2016.
 */
public class DataBase extends BaseTreeSet {

    private final String baseNodeName = "Node ";
    private int createdNodesCounter = 0;
    private int[] childPseudorandomCount = {4, 2, 3, 1, 2, 0};

    public DataBase() {
        fillData();
    }

    public void reset() {
        nodes.clear();
        createdNodesCounter = 0;
        fillData();
    }

    /**
     * @param newNodes nodes to save, for new created node id must be negative!!!
     * @return id of elements witch was created as {@link SparseIntArray} with key - new element
     * sent id value, value - new generated id value
     */
    public SparseIntArray applyChanges(List<Node> newNodes) {
        Collections.sort(newNodes, new Comparator<Node>() {
            @Override
            public int compare(Node left, Node right) {
                return left.getId() > right.getId() ?  - 1 : left.getId() == right.getId() ?  0 : 1; // apply changes to old nodes first
            }
        });
        SparseIntArray generatedIds = new SparseIntArray();
        for (Node node : newNodes) {
            if (node.getId() < 0) {// new item
                int newId = ++createdNodesCounter;
                generatedIds.put(node.getId(), newId);
                if (node.getRootId() != null && node.getRootId() < 0) {
                    node.setRootId(generatedIds.get(node.getRootId(), node.getRootId()));
                }
                if (node.getRootId() >= 0) {
                    Node root = getElementById(node.getRootId());
                    if (root != null) {
                        root.removeChildren(node.getId());
                        root.addChildren(newId);
                        if (!root.isValid())
                            node.setValid(false);
                    }
                }
                node.setId(newId);
            }
            addData(node);
            if (!node.isValid()) {
                removeBranch(node);
            }
        }
        orderElements();
        return generatedIds;
    }

    private void addData(Node node) {
        nodes.put(node.getId(), node);
    }

    public Node loadNodeById(int id) {
        return Node.copy(getElementById(id));
    }

    private void fillData() {
        Node treeRoot = new Node();
        treeRoot.setId(createdNodesCounter);
        treeRoot.setValue(baseNodeName + ++createdNodesCounter);
        addData(treeRoot);
        addChilds(treeRoot, getChildCount(createdNodesCounter), 0, 5);
        orderElements();
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

package com.test.afedyanov.datatree.model;

import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.ArrayList;
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
     * @param updatedNodes nodes to save, for new created node id must be negative!!!
     * @return List of updated nodes
     */
    public List<Node> applyChanges(List<Node> updatedNodes, int[] cachedNodesId) {
        SparseIntArray newIds = new SparseIntArray();
        List<Integer> updatedElementsIds = new ArrayList<>();
        for (Node node : updatedNodes) {
            int originalId = node.getId();
            if (node.getId() < 0)
                node.setId(generateItemId());
            newIds.put(originalId, node.getId());
        }
        for (Node node : updatedNodes) {
            for (int childId : node.getNodesIds()) {
                if (childId < 0) {
                    node.getNodesIds().set(node.getNodesIds().indexOf(childId), newIds.get(childId));
                }
            }
            if (node.getRootId() != null && node.getRootId() < 0) {
                node.setRootId(newIds.get(node.getRootId()));
            }
            Node root = getElementById(node.getRootId());
            if (root != null && !root.isValid()) {
                node.setValid(false);
            }
            node = addData(node);
            updatedElementsIds.add(node.getId());
            if (!node.isValid()) {
                Integer[] updatedChildIds = removeBranch(node);
                for (Integer updatedChildId : updatedChildIds) {
                    for (int cachedNodeId : cachedNodesId) {
                        if (cachedNodeId < 0)
                            cachedNodeId = newIds.get(cachedNodeId);
                        if (cachedNodeId == updatedChildId) {
                            updatedElementsIds.add(updatedChildId);
                            break;
                        }
                    }
                }
            }
        }
        orderElements();
        return getUpdatedElements(updatedElementsIds);
    }

    private List<Node> getUpdatedElements(List<Integer> updatedNodesIds) {
        List<Node> nodes = new ArrayList<>();
        for (Integer updatedNodeId : updatedNodesIds) {
            nodes.add(Node.copy(getElementById(updatedNodeId)));
        }
        return nodes;
    }

    private Node addData(Node node) {
        Node originalNode = getElementById(node.getId());
        if (originalNode == null) {
            nodes.put(node.getId(), node);
            return node;
        } else {
            originalNode.update(node);
        }
        return originalNode;
    }

    public Node loadNodeById(int id) {
        return Node.copy(getElementById(id));
    }

    private void fillData() {
        Node treeRoot = new Node();
        treeRoot.setId(createdNodesCounter);
        treeRoot.setValue(baseNodeName + generateItemId());
        addData(treeRoot);
        addChilds(treeRoot, getChildCount(treeRoot.getId()), 0, 5);
        orderElements();
    }

    private void addChilds(Node root, int childCount, int currentDepth, int maxDepth) {
        if (currentDepth == maxDepth && childCount < 2)
            childCount = 2;
        for (int i = 0; i < childCount; i++) {
            Node child = new Node();
            child.setId(createdNodesCounter);
            child.setValue(baseNodeName + generateItemId());
            child.setRootId(root.getId());
            root.addChildren(child.getId());
            addData(child);
            if (currentDepth != maxDepth) {
                addChilds(child, getChildCount(child.getId()), currentDepth + 1, maxDepth);
            }
        }
    }

    private int getChildCount(int currentNode) {
        return childPseudorandomCount[currentNode - childPseudorandomCount.length * (currentNode/childPseudorandomCount.length)];
    }

    private int generateItemId() {
        return ++createdNodesCounter;
    }

}

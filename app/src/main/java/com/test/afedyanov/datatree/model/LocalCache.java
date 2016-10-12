package com.test.afedyanov.datatree.model;

import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.List;

public class LocalCache extends BaseTreeSet {

    private int createdNodesCounter = 0;

    public List<Node> getNodes() {
        List<Node> copiedNodes = new ArrayList<>();
        for(int i = 0; i < nodes.size(); i++) {
            int key = nodes.keyAt(i);
            copiedNodes.add(Node.copy(nodes.get(key)));
        }
        return copiedNodes;
    }

    public void clear() {
        nodes.clear();
        createdNodesCounter = 0;
    }

    public void addNode(Node node) {
        Node sameNode = getElementById(node.getId());
        if (sameNode != null)
            mergeNode(node, sameNode);
        nodes.put(node.getId(), node);
        if (checkIsDeleted(node))
            node.setValid(false);
        orderElements();
    }

    private void mergeNode(Node newNode, Node oldNode) {
        for (int oldChildId : oldNode.getNodesIds()) {
            boolean hasSameChild = false;
            for (int newChildId: newNode.getNodesIds()) {
                if (newChildId == oldChildId) {
                    hasSameChild = true;
                    break;
                }
            }
            if (!hasSameChild)
                newNode.addChildren(oldChildId);
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

    public void setCreatedElementsIds(SparseIntArray generatedIds) {
        for (int i = 0; i < generatedIds.size(); i++) {
            int localId = generatedIds.keyAt(i);
            Node node = getElementById(localId);
            if (node != null) {
                node.setId(generatedIds.get(localId));
                nodes.remove(localId);
                nodes.put(node.getId(), node);
                for (int childId: node.getNodesIds()) {
                    Node child = getElementById(childId);
                    if (child != null)
                        child.setRootId(node.getId());
                }
                Node root = getElementById(node.getRootId());
                if (root != null) {
                    root.removeChildren(localId);
                    root.addChildren(node.getId());
                }
            }
        }
        orderElements();
    }

    public void editNode(int nodeId, String value) {
        getElementById(nodeId).setValue(value);
    }

    public void setDeleted(Node node) {
        node.setValid(false);
        removeBranch(node);
    }

    private boolean checkIsDeleted(Node node) {
        Node root = getElementById(node.getRootId());
        return root != null && !root.isValid();
    }
}

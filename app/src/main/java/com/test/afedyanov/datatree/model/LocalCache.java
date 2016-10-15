package com.test.afedyanov.datatree.model;

import java.util.ArrayList;
import java.util.List;

public class LocalCache extends BaseTreeSet {

    private int createdNodesCounter = 0;
    private List<Integer> changedNodesId = new ArrayList<>();

    public List<Node> getChangedNodes() {
        List<Node> copiedNodes = new ArrayList<>();
        for(int i : changedNodesId) {
            Node cachedNode = nodes.get(i);
            Node copiedNode = Node.copy(cachedNode);
            copiedNode.addChildren(cachedNode.getNodesIds());
            copiedNodes.add(copiedNode);
        }
        return copiedNodes;
    }

    public int[] getCachedNodesIds() {
        int[] ids = new int[nodes.size()];
        for (int i = 0; i < nodes.size(); i++)
            ids[i] = nodes.keyAt(i);
        return ids;
    }

    public void clear() {
        nodes.clear();
        createdNodesCounter = 0;
        changedNodesId.clear();
    }

    public void addNode(Node node) {
        node.addChildren(findChilds(node));
        Node root = getElementById(node.getRootId());
        if (root != null) {
            root.addChildren(node.getId());
            setNodeHasChanges(root.getId());
        }
        nodes.put(node.getId(), node);
        if (checkIsDeleted(node)) {
            node.setValid(false);
            Integer removedElementsIds[] = removeBranch(node);
            for (Integer id : removedElementsIds)
                setNodeHasChanges(id);
        }
        orderElements();
    }

    public void createNode(int rootId, String value) {
        Node node = new Node();
        node.setId(node.getId() - createdNodesCounter++);
        node.setValue(value);
        node.setRootId(rootId);
        addNode(node);
        setNodeHasChanges(node.getId());
    }

    public void updateSavedElements(List<Node> updatedNodes) {
        for(int i : changedNodesId) {
            nodes.remove(i);
        }
        changedNodesId.clear();
        for (Node node : updatedNodes) {
            addNode(node);
        }
    }

    public void editNode(int nodeId, String value) {
        getElementById(nodeId).setValue(value);
        setNodeHasChanges(nodeId);
    }

    public void setDeleted(Node node) {
        node.setValid(false);
        setNodeHasChanges(node.getId());
        removeBranch(node);
    }

    private boolean checkIsDeleted(Node node) {
        Node root = getElementById(node.getRootId());
        return root != null && !root.isValid();
    }

    private List<Integer> findChilds(Node root) {
        List<Integer> childs = new ArrayList<>();
        for(int i = 0; i < nodes.size(); i++) {
            int key = nodes.keyAt(i);
            Node child = getElementById(key);
            if (child != null) {
                if (child.getRootId() != null && child.getRootId() == root.getId()) {
                    childs.add(key);
                }
            }
        }
        return childs;
    }

    private void setNodeHasChanges(int nodeId) {
        boolean alreadyChanged = false;
        for (int id : changedNodesId) {
            if (id == nodeId)
                alreadyChanged = true;
        }
        if (!alreadyChanged)
            changedNodesId.add(nodeId);
    }
}

package com.test.afedyanov.datatree.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alexandr on 10.10.2016.
 */
public class Node {
    private int id = -1;
    private String value;
    private Integer rootId;
    private List<Integer> nodesIds = new ArrayList<>();
    private boolean isValid = true;

    public static Node copy(Node node) {
        Node copiedNode = new Node();
        copiedNode.setId(node.getId());
        copiedNode.setRootId(node.getRootId());
        copiedNode.setValue(node.getValue());
        copiedNode.setValid(node.isValid());
        return copiedNode;
    }

    public void update(Node newNode) {
        setValid(newNode.isValid());
        setRootId(newNode.getRootId());
        setId(newNode.getId());
        setValue(newNode.getValue());
        for (int child: newNode.getNodesIds()) {
            addChildren(child);
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public List<Integer> getNodesIds() {
        return nodesIds;
    }

    public void addChildren(int nodeId) {
        if (!hasSameChild(nodeId))
            nodesIds.add(nodeId);
    }

    public void addChildren(List<Integer> childs) {
        nodesIds.addAll(childs);
    }

    public void removeChildren(int nodeId) {
        for (Integer id: nodesIds) {
            if (id == nodeId) {
                nodesIds.remove(id);
                break;
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private boolean hasSameChild(int childId) {
        for (int oldChildId : getNodesIds()) {
            if (childId == oldChildId) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            return ((Node) o).getId() == getId();
        }
        return super.equals(o);
    }
}

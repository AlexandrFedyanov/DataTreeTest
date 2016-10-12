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
        copiedNode.getNodesIds().addAll(node.getNodesIds());
        copiedNode.setValid(node.isValid());
        return copiedNode;
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
        nodesIds.add(nodeId);
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            return ((Node) o).getId() == getId();
        }
        return super.equals(o);
    }
}

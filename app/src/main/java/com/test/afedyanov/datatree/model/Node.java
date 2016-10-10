package com.test.afedyanov.datatree.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr on 10.10.2016.
 */
public class Node {
    private int id;
    private String value;
    private Node root;
    private List<Node> nodes = new ArrayList<>();
    private boolean isValid = true;

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

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node)
            return ((Node) o).getId() == getId();
        return super.equals(o);
    }
}

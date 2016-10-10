package com.test.afedyanov.datatree.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr on 10.10.2016.
 */
public class DataBase {

    private List<Node> datas = new ArrayList<>();

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

    private void fillData() {
        Node treeRoot = new Node();
        treeRoot.setValue(baseNodeName + ++createdNodesCounter);
        datas.add(treeRoot);
        addChilds(treeRoot, getChildCount(createdNodesCounter), 0, 4);
        for (Node node : datas) {
            Node root = node.getRoot();
            String space = "";
            while (root != null) {
                space+=" ";
                root = root.getRoot();
            }
            Log.d("node", space + node.getValue());
        }
    }

    private void addChilds(Node root, int childCount, int currentDepth, int maxDepth) {
        if (currentDepth == maxDepth && childCount < 2)
            childCount = 2;
        for (int i = 0; i < childCount; i++) {
            Node child = new Node();
            child.setValue(baseNodeName + ++createdNodesCounter);
            child.setRoot(root);
            root.getNodes().add(child);
            datas.add(child);
            if (currentDepth != maxDepth) {
                addChilds(child, getChildCount(createdNodesCounter), currentDepth + 1, maxDepth);
            }
        }
    }

    private int getChildCount(int currentNode) {
        return childPseudorandomCount[currentNode - childPseudorandomCount.length * (currentNode/childPseudorandomCount.length)];
    }
}

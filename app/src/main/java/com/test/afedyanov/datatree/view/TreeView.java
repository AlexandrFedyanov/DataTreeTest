package com.test.afedyanov.datatree.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.test.afedyanov.datatree.R;
import com.test.afedyanov.datatree.model.ITreeDataSet;
import com.test.afedyanov.datatree.model.Node;
import com.test.afedyanov.datatree.view.adapter.TreeAdapter;
import com.test.afedyanov.datatree.view.viewinterface.ITreeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TreeView extends FrameLayout implements ITreeView {

    @Bind(R.id.recycleView) RecyclerView nodesRecyclerView;
    private TreeAdapter treeAdapter;

    public TreeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.view_tree_layout, this, true);
        ButterKnife.bind(this, this);
        treeAdapter = new TreeAdapter();
        nodesRecyclerView.setAdapter(treeAdapter);
    }

    @Override
    public void setData(ITreeDataSet dataSet) {
        treeAdapter.setData(dataSet);
    }

    @Override
    public int getCurrentSelection() {
        return treeAdapter.getCurrentSelectedId();
    }

    @Override
    public void notifyDataSetChanged() {
        treeAdapter.notifyDataSetChanged();
    }
}

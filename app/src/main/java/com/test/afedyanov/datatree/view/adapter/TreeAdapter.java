package com.test.afedyanov.datatree.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.afedyanov.datatree.R;
import com.test.afedyanov.datatree.model.ITreeDataSet;
import com.test.afedyanov.datatree.model.Node;

public class TreeAdapter extends RecyclerView.Adapter<NodeViewHolder> {

    private ITreeDataSet dataSet;
    private int currentSelectedItemPosition;

    public void setData(ITreeDataSet dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    public int getCurrentSelectedId() {
        return dataSet.getElementForPosition(currentSelectedItemPosition).getId();
    }

    private void setCurrentSelectedItem(int position) {
        int oldSelectedPosition = currentSelectedItemPosition;
        currentSelectedItemPosition = position;
        notifyItemChanged(oldSelectedPosition);
        notifyItemChanged(currentSelectedItemPosition);
    }

    @Override
    public NodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NodeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_node, parent, false));
    }

    @Override
    public void onBindViewHolder(final NodeViewHolder holder, int position) {
        Node node = dataSet.getElementForPosition(position);
        holder.nodeValueTextView.setText(node.getValue());
        ViewGroup.LayoutParams layoutParams = holder.depthSpace.getLayoutParams();
        layoutParams.width = dataSet.getElementDepthOnTree(node) * holder.depthSpace.getContext().getResources().getDimensionPixelSize(R.dimen.node_depth_space);
        holder.depthSpace.setLayoutParams(layoutParams);
        holder.rootView.setSelected(node.isValid() && position == currentSelectedItemPosition);
        holder.rootView.setEnabled(node.isValid());
        holder.nodeValueTextView.setEnabled(node.isValid());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentSelectedItem(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.getElementsCount();
    }
}

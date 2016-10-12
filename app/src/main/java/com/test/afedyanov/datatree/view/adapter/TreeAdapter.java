package com.test.afedyanov.datatree.view.adapter;

import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.afedyanov.datatree.R;
import com.test.afedyanov.datatree.model.ITreeDataSet;
import com.test.afedyanov.datatree.model.Node;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.NodeViewHolder> {

    private ITreeDataSet dataSet;
    private int currentSelectedItemPosition;

    public void setData(ITreeDataSet dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    public int getCurrentSelectedId() {
        Node selectedElement = dataSet.getElementForPosition(currentSelectedItemPosition);
        if (selectedElement != null && selectedElement.isValid())
            return dataSet.getElementForPosition(currentSelectedItemPosition).getId();
        return -1;
    }

    @Override
    public NodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NodeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_node, parent, false));
    }

    @Override
    public void onBindViewHolder(NodeViewHolder holder, int position) {
        Node node = dataSet.getElementForPosition(position);
        holder.nodeValueTextView.setText(node.getValue());
        ViewGroup.LayoutParams layoutParams = holder.depthSpace.getLayoutParams();
        layoutParams.width = dataSet.getElementDepthOnTree(node) * holder.depthSpace.getContext().getResources().getDimensionPixelSize(R.dimen.node_depth_space);
        holder.depthSpace.setLayoutParams(layoutParams);
        holder.rootView.setSelected(node.isValid() && position == currentSelectedItemPosition);
        holder.rootView.setEnabled(node.isValid());
        holder.nodeValueTextView.setEnabled(node.isValid());
    }

    @Override
    public int getItemCount() {
        if (dataSet == null)
            return 0;
        return dataSet.getElementsCount();
    }

    @Override
    public long getItemId(int position) {
        return dataSet.getElementForPosition(position).getId();
    }

    public class NodeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rootView) View rootView;
        @Bind(R.id.depthSpace)
        Space depthSpace;
        @Bind(R.id.nodeValue)
        TextView nodeValueTextView;

        NodeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int oldSelectedPosition = currentSelectedItemPosition;
                    currentSelectedItemPosition = getLayoutPosition();
                    notifyItemChanged(oldSelectedPosition);
                    rootView.setSelected(true);
                }
            });
        }
    }
}

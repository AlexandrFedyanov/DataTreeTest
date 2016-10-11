package com.test.afedyanov.datatree.view.adapter;

import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.test.afedyanov.datatree.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NodeViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.rootView) View rootView;
    @Bind(R.id.depthSpace) Space depthSpace;
    @Bind(R.id.nodeValue) TextView nodeValueTextView;

    public NodeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

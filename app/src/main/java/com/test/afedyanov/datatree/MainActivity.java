package com.test.afedyanov.datatree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.test.afedyanov.datatree.model.DataBase;
import com.test.afedyanov.datatree.model.LocalCache;
import com.test.afedyanov.datatree.view.TreeView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.dataTreeView) TreeView dataTreeView;
    @Bind(R.id.cacheTreeView) TreeView cacheTreeView;

    @Bind(R.id.loadSelectionButton) Button loadSelectedButton;
    @Bind(R.id.resetButton) Button resetButton;

    @Bind(R.id.addButton) ImageButton addButton;
    @Bind(R.id.removeButton) ImageButton removeButton;
    @Bind(R.id.editButton) ImageButton editButton;
    @Bind(R.id.applyButton) Button applyButton;

    private DataBase dataBase;
    private LocalCache localCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dataBase = new DataBase();
        localCache = new LocalCache();
        dataTreeView.setData(dataBase);
        cacheTreeView.setData(localCache);
        setupControls();
    }

    private void setupControls() {
        setupDataBaseControls();
        setupCacheControls();
    }

    private void setupDataBaseControls() {
        loadSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localCache.addNode(dataBase.loadNodeById(dataTreeView.getCurrentSelection()));
                cacheTreeView.notifyDataSetChanged();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localCache.clear();
                dataBase.reset();
                cacheTreeView.notifyDataSetChanged();
                dataTreeView.notifyDataSetChanged();
            }
        });
    }

    private void setupCacheControls() {

    }
}

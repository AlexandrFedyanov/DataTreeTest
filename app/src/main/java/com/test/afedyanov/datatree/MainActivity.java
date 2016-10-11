package com.test.afedyanov.datatree;

import android.content.DialogInterface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.test.afedyanov.datatree.model.ITreeDataSet;
import com.test.afedyanov.datatree.presenter.MainPresenter;
import com.test.afedyanov.datatree.presenter.presenterinterface.IMainPresenter;
import com.test.afedyanov.datatree.view.TreeView;
import com.test.afedyanov.datatree.view.viewinterface.IMainView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView {

    @Bind(R.id.mainRootView) CoordinatorLayout mainRootView;
    @Bind(R.id.dataTreeView) TreeView dataTreeView;
    @Bind(R.id.cacheTreeView) TreeView cacheTreeView;

    @Bind(R.id.loadSelectionButton) Button loadSelectedButton;
    @Bind(R.id.resetButton) Button resetButton;

    @Bind(R.id.addButton) ImageButton addButton;
    @Bind(R.id.removeButton) ImageButton removeButton;
    @Bind(R.id.editButton) ImageButton editButton;
    @Bind(R.id.applyButton) Button applyButton;

    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter();
        setupControls();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    private void setupControls() {
        setupDataBaseControls();
        setupCacheControls();
    }

    private void setupDataBaseControls() {
        loadSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadFromDataBase(dataTreeView.getCurrentSelection());

            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.reset();
            }
        });
    }

    private void setupCacheControls() {
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.remove(cacheTreeView.getCurrentSelection());
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog(cacheTreeView.getCurrentSelection());
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateDialog(cacheTreeView.getCurrentSelection());
            }
        });
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.apply();
            }
        });
    }

    @Override
    public void showMessage(int messageResId) {
        Snackbar.make(mainRootView, getString(messageResId), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void notifyCacheDataChanged() {
        cacheTreeView.notifyDataSetChanged();
    }

    @Override
    public void notifyDataBaseDataChanged() {
        dataTreeView.notifyDataSetChanged();
    }

    @Override
    public void setDataBaseData(ITreeDataSet dataBaseData) {
        dataTreeView.setData(dataBaseData);
    }

    @Override
    public void setLocalCacheData(ITreeDataSet localCacheData) {
        cacheTreeView.setData(localCacheData);
    }

    private void showEditDialog(final int currentSelection) {
        final EditText input = createInputLayout();
        showInputDialog(input, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = input.getText().toString();
                presenter.edit(currentSelection, value);
            }
        });
    }

    private void showCreateDialog(final int currentSelection) {
        final EditText input = createInputLayout();
        showInputDialog(input, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = input.getText().toString();
                presenter.add(currentSelection, value);
            }
        });
    }

    private void showInputDialog(View input, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setView(input)
                .setTitle(R.string.input_message)
                .setPositiveButton(R.string.save, listener)
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private EditText createInputLayout() {
        final EditText input = new EditText(this);
        input.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(layoutParams);
        return input;
    }
}

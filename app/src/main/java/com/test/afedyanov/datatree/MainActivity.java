package com.test.afedyanov.datatree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.test.afedyanov.datatree.model.DataBase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DataBase dataBase = new DataBase();
        findViewById(R.id.getDataButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBase.reset();
            }
        });
    }
}

package com.bluestrom.gao.explorersouhupics.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.bluestrom.gao.explorersouhupics.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mPicsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPicsList = (RecyclerView) findViewById(R.id.pics_list);
    }

}

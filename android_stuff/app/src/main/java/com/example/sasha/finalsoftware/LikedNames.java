package com.example.sasha.finalsoftware;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.cs4531.finalsoftware.R;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


public class LikedNames extends AppCompatActivity {

    private Button unLike;
    private RecyclerView mRecycleView;
    //private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        unLike = (Button)findViewById(R.id.unLikeButton);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_names);

        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(LikedNames.this);
        //mAdapter = new ExampleAdapter(exampleList);

        mRecycleView.setLayoutManager(mLayoutManager);
        //mRecycleView.setAdapter(mAdapter);

    }
}

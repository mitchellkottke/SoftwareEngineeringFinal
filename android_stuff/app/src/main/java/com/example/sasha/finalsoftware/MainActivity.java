package com.example.sasha.finalsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs4531.finalsoftware.R;

public class MainActivity extends AppCompatActivity {
@Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
}
    public void switchToNamesListed(View myView) {
        Intent myIntent = new Intent(this, NamesListedActivity.class);
        startActivity(myIntent);
    }
}

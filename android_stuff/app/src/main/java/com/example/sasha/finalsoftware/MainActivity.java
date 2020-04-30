package com.example.sasha.finalsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.cs4531.finalsoftware.R;

public class MainActivity extends AppCompatActivity {

    private Button namesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namesButton = (Button)findViewById(R.id.namesButton);
//        namesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, NamesListedActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void switchToNamesListed(View myView) {
        Intent myIntent = new Intent(MainActivity.this, NamesListedActivity.class);
        startActivity(myIntent);
    }
}
